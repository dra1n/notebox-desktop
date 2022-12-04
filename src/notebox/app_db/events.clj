(ns notebox.app-db.events
  (:require [clojure.set]
            [cljfx.api :as fx]
            [clojure.string :as str]
            [clojure.pprint]
            [notebox.app-db.db :refer [*state event-handler]]
            [notebox.app-db.queries :as queries]
            [notebox.app-db.effects :as effects])
  (:import [javafx.scene.control DialogEvent Dialog ButtonBar$ButtonData ButtonType]))


(defmethod event-handler ::initialize [_]
  {:dispatch-n [{:event/type ::start-auth-flow}
                {:event/type ::set-scene :data :splash}]})


;; Auth events

(defmethod event-handler ::start-auth-flow [_]
  {:start-auth-flow {:auth-finished ::set-auth-finished
                     :auth-login-required ::switch-to-login-pane}})

(defmethod event-handler ::set-auth-finished [event]
  (let [{:keys [access-token context]} event]
    {:context (-> context
                  (fx/sub-ctx queries/assoc-access-token access-token)
                  (fx/sub-ctx queries/assoc-pkce-web-auth nil))
     :dispatch-n [{:event/type ::set-scene :data :all-notes}
                  {:event/type ::fetch-notes-info}
                  {:event/type ::fetch-account-info}]}))

(defmethod event-handler ::switch-to-login-pane [event]
  (let [{:keys [authorize-url pkce-web-auth context]} event]
    {:context (-> context
                  (fx/sub-ctx queries/assoc-authorize-url authorize-url)
                  (fx/sub-ctx queries/assoc-pkce-web-auth pkce-web-auth))
     :dispatch {:event/type ::set-scene :data :login}}))

(defmethod event-handler ::set-auth-code [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-auth-code data)}))

(defmethod event-handler ::apply-auth-code [event]
  (let [{:keys [context]} event
        auth-code (fx/sub-ctx context queries/auth-code)
        pkce-web-auth (fx/sub-ctx context queries/pkce-web-auth)]
    (cond (not (str/blank? auth-code))
          {:dispatch {:event/type ::set-scene :data :splash}
           :apply-auth-code {:auth-code auth-code
                             :pkce-web-auth pkce-web-auth
                             :auth-finished ::set-auth-finished}})))

(defmethod event-handler ::logout [event]
  (let [{:keys [context]} event]
    {:context (-> context
                  (fx/sub-ctx queries/reset-auth)
                  (fx/sub-ctx queries/reset-notes)
                  (fx/sub-ctx queries/reset-account))
     :logout {:logout-finished ::initialize}}))


;; Notes events

(defmethod event-handler ::start-sync [event]
  (let [{:keys [source context]} event]
    {:context (fx/sub-ctx context queries/assoc-syncing source true)}))

(defmethod event-handler ::stop-sync [event]
  (let [{:keys [source context]} event]
    {:context (fx/sub-ctx context queries/assoc-syncing source false)}))


(defmethod event-handler ::set-visible-book [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-visible-book data)}))

(defmethod event-handler ::remove-visible-book [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/remove-visible-book data)}))


(defmethod event-handler ::set-search-input-value [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-search-input-value data)}))


(defmethod event-handler ::set-hovered-book [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-hovered-book data)}))

(defmethod event-handler ::set-last-active-book [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-last-active-book data)}))


(defmethod event-handler ::set-notes-info [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-notes-info data)
     :dispatch {:event/type ::stop-sync :source ::fetch-notes-info}}))

(defmethod event-handler ::set-notes-info-error [event]
  (let [{:keys [error]} event]
    (println error)
    {:dispatch {:event/type ::stop-sync :source ::fetch-notes-info}}))

(defmethod event-handler ::fetch-notes-info [event]
  (let [{:keys [context]} event
        access-token (fx/sub-ctx context queries/access-token)]
    {:dispatch {:event/type ::start-sync :source ::fetch-notes-info}
     :fetch-notes-info {:token access-token
                        :dispatch-success ::set-notes-info
                        :dispatch-error ::set-notes-info-error}}))

(defmethod event-handler ::set-last-active-note [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-last-active-note data)}))


(defmethod event-handler ::set-book [event]
  (let [{:keys [book data context]} event]
    {:context (fx/sub-ctx context queries/assoc-book book data)
     :dispatch {:event/type ::stop-sync :source [::fetch-book book]}}))

(defmethod event-handler ::set-book-error [event]
  (let [{:keys [error book]} event]
    (println error)
    {:dispatch {:event/type ::stop-sync :source [::fetch-book book]}}))

(defmethod event-handler ::fetch-book [event]
  (let [{:keys [data context]} event
        access-token (fx/sub-ctx context queries/access-token)]
    {:dispatch {:event/type ::start-sync :source [::fetch-book data]}
     :fetch-book {:token access-token
                  :book data
                  :dispatch-success ::set-book
                  :dispatch-error ::set-book-error}}))


;; Account events

(defmethod event-handler ::set-account-info [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-account-info data)
     :dispatch {:event/type ::stop-sync :source ::fetch-account-info}}))

(defmethod event-handler ::set-account-info-error [event]
  (let [{:keys [error]} event]
    (println error)
    {:dispatch {:event/type ::stop-sync :source ::fetch-account-info}}))

(defmethod event-handler ::fetch-account-info [event]
  (let [{:keys [context]} event
        access-token (fx/sub-ctx context queries/access-token)]
    {:dispatch {:event/type ::start-sync :source ::fetch-account-info}
     :fetch-account-info {:token access-token
                          :dispatch-success ::set-account-info
                          :dispatch-error ::set-account-info-error}}))


;; Search events

(defmethod event-handler ::start-search [event]
  (let [{:keys [context data]} event
        books-for-search (-> context
                             (fx/sub-ctx queries/notes)
                             (keys))]
    {:context (fx/sub-ctx context queries/assoc-search-started data)
     :dispatch {:event/type ::fetch-and-search :data data}
     :dispatch-n (map (fn [v] {:event/type ::search-in-book :data v})
                      books-for-search)}))

(defmethod event-handler ::finish-search [event]
  (let [{:keys [context]} event]
    {:context (queries/assoc-search-finished context)}))

(defmethod event-handler ::search-in-book [event]
  (let [{:keys [context data]} event
        search-value (fx/sub-ctx context queries/search-value)
        notes (fx/sub-ctx context queries/notes)]
    {:search-in-book {:notes (get notes data)
                      :value search-value
                      :book data
                      :dispatch-success ::add-search-result}}))

(defmethod event-handler ::add-search-result [event]
  (let [{:keys [context book data]} event]
    {:context (fx/sub-ctx context queries/assoc-search-results book data)}))

(defmethod event-handler ::fetch-and-search [event]
  (let [{:keys [context]} event
        access-token (fx/sub-ctx context queries/access-token)
        all-books (->> (fx/sub-ctx context queries/notes-info)
                       (map :slug))
        fetched-books (-> context
                          (fx/sub-ctx queries/notes)
                          (keys))
        books-for-later-search (vec (clojure.set/difference (set all-books) (set fetched-books)))]
    {:fetch-books
     {:token access-token
      :books books-for-later-search
      :dispatch-success ::book-ready-for-search
      :dispatch-error ::set-book-error}
     :dispatch-n (mapv (fn [v] {:event/type ::start-sync :source [::fetch-book v]})
                       books-for-later-search)}))

(defmethod event-handler ::book-ready-for-search [event]
  (let [{:keys [context book data]} event]
    {:context (fx/sub-ctx context queries/assoc-book book data)
     :dispatch-n [{:event/type ::stop-sync :source [::fetch-book book]}
                  {:event/type ::search-in-book :data book}]}))


;; Subscene events

(defmethod event-handler ::set-subscene [event]
  (let [{:keys [name data context]} event]
    {:context (fx/sub-ctx context queries/assoc-subscene name data)}))


;; Scene events

(defmethod event-handler ::set-scene [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-scene data)}))


;; Style events

(defmethod event-handler ::set-styles [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-styles data)}))


;; UI events

(defmethod event-handler ::toggle-sidemenu-collapsed [event]
  (let [{:keys [context]} event
        sidemenu-collapsed? (fx/sub-ctx context queries/sidemenu-collapsed?)]
    {:context (fx/sub-ctx context queries/assoc-sidemenu-collapsed (not sidemenu-collapsed?))}))

(defmethod event-handler ::show-confirmation
  [{:keys [context confirmation-id]}]
  {:context (fx/sub-ctx context queries/assoc-show-confirmation confirmation-id true)})

(defmethod event-handler ::on-confirmation-dialog-hidden
  [{:keys [context ^DialogEvent fx/event confirmation-id on-confirmed]}]
  (condp = (.getButtonData ^ButtonType (.getResult ^Dialog (.getSource event)))
    ButtonBar$ButtonData/CANCEL_CLOSE
    {:context (fx/sub-ctx context queries/assoc-show-confirmation confirmation-id false)}

    ButtonBar$ButtonData/OK_DONE
    {:context (fx/sub-ctx context queries/assoc-show-confirmation confirmation-id false)
     :dispatch on-confirmed}))


;; Main dispatch function

(def dispatch-event
  (-> event-handler
      (fx/wrap-co-effects {:context (fx/make-deref-co-effect *state)})
      (fx/wrap-effects {:context (fx/make-reset-effect *state)
                        :dispatch fx/dispatch-effect
                        :dispatch-n effects/dispatch-n
                        :start-auth-flow effects/start-auth-flow
                        :apply-auth-code effects/apply-auth-code
                        :logout effects/logout
                        :fetch-notes-info effects/fetch-notes-info
                        :fetch-book effects/fetch-book
                        :fetch-books effects/fetch-books
                        :fetch-account-info effects/fetch-account-info
                        :search-in-book effects/search-in-book})))


;; Playground

(comment (dispatch-event {:event/type ::set-notes-info :data nil}))
(comment (dispatch-event {:event/type ::set-scene :data :all_notes}))
(comment (dispatch-event {:event/type ::fetch-notes-info}))
(comment (dispatch-event {:event/type ::fetch-book :data "tolstann"}))
(comment (dispatch-event {:event/type ::start-search :data "лень"}))
(comment (dispatch-event {:event/type ::finish-search}))
(comment
  (defmethod event-handler ::reset-books [event]
    (let [{:keys [context]} event]
      {:context (fx/sub-ctx context queries/reset-books)}))

  (dispatch-event {:event/type ::reset-books}))
(comment (clojure.pprint/pprint @*state))