(ns notebox.app-db.events
  (:require [cljfx.api :as fx]
            [clojure.pprint]
            [notebox.app-db.db :refer [*state event-handler]]
            [notebox.app-db.queries :as queries]
            [notebox.app-db.effects :as effects]))

(def access-token "sl.BMz3FKdLf05Wv_UxfCBNDvfE8I42lwbrU3CwKrWQa3WkVQ1xjo1RvtWuSoMMMMnQII3PaUZmeKBwRWN8mzFlLGsdUCff1Q7Fa4A-gxsiU1qrPVdOK8TLOFzRDXh9-0tXZi7_81Do6J0m")


;; Auth events

(defmethod event-handler ::check-auth [event]
  (println event))


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

(defmethod event-handler ::fetch-notes-info [_]
  {:dispatch {:event/type ::start-sync :source ::fetch-notes-info}
   :fetch-notes-info {:token access-token
                      :dispatch-success ::set-notes-info
                      :dispatch-error ::set-notes-info-error}})

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
  (let [{:keys [data]} event]
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
                        :fetch-notes-info effects/fetch-notes-info
                        :fetch-book effects/fetch-book})))


;; Playground

(comment (dispatch-event {:event/type ::set-notes-info :data nil}))
(comment (dispatch-event {:event/type ::set-scene :data :all_notes}))
(comment (dispatch-event {:event/type ::fetch-notes-info}))
(comment (dispatch-event {:event/type ::fetch-book :data "tolstann"}))
(comment (clojure.pprint/pprint @*state))