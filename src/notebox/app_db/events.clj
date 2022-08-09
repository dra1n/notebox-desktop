(ns notebox.app-db.events
  (:require [cljfx.api :as fx]
            [clojure.string :as str]
            [clojure.pprint]
            [notebox.app-db.db :refer [*state event-handler]]
            [notebox.app-db.queries :as queries]
            [notebox.app-db.effects :as effects]))


(defmethod event-handler ::initialize [_]
  {:dispatch-n [{:event/type ::start-auth-flow}
                {:event/type ::set-scene :data :all-notes}]})


;; Auth events

(defmethod event-handler ::start-auth-flow [_]
  {:start-auth-flow {:auth-finished ::set-auth-finished
                     :auth-login-required ::switch-to-login-pane}})

(defmethod event-handler ::set-auth-finished [event]
  (let [{:keys [access-token context]} event]
    {:context (fx/sub-ctx context queries/assoc-access-token access-token)
     :dispatch-n [{:event/type ::set-scene :data :all-notes}
                  {:event/type ::fetch-notes-info}]}))

(defmethod event-handler ::switch-to-login-pane [event]
  (let [{:keys [authorize-url context]} event]
    {:context (fx/sub-ctx context queries/assoc-authorize-url authorize-url)
     :dispatch {:event/type ::set-scene :data :login}}))

(defmethod event-handler ::set-auth-code [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-auth-code data)}))

(defmethod event-handler ::apply-auth-code [event]
  (let [{:keys [context]} event
        auth-code (fx/sub-ctx context queries/auth-code)]
    (cond (not (str/blank? auth-code))
          {:apply-auth-code {:auth-code auth-code
                             :auth-finished ::set-auth-finished}})))


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


;; Main dispatch function

(def dispatch-event
  (-> event-handler
      (fx/wrap-co-effects {:context (fx/make-deref-co-effect *state)})
      (fx/wrap-effects {:context (fx/make-reset-effect *state)
                        :dispatch fx/dispatch-effect
                        :dispatch-n effects/dispatch-n
                        :start-auth-flow effects/start-auth-flow
                        :apply-auth-code effects/apply-auth-code
                        :fetch-notes-info effects/fetch-notes-info
                        :fetch-book effects/fetch-book})))


;; Playground

(comment (dispatch-event {:event/type ::set-notes-info :data nil}))
(comment (dispatch-event {:event/type ::set-scene :data :all_notes}))
(comment (dispatch-event {:event/type ::fetch-notes-info}))
(comment (dispatch-event {:event/type ::fetch-book :data "tolstann"}))
(comment (clojure.pprint/pprint @*state))