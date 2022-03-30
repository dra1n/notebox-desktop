(ns notebox.app-db.events
  (:require [cljfx.api :as fx]
            [notebox.app-db.db :refer [*state event-handler]]
            [notebox.app-db.queries :as queries]
            [notebox.app-db.effects :as effects]))

(def access-token "sl.BEzEOWBO0dUyUvu-6ZzPvDKnUYBhP4ykkJ6eQ6n0fn6n5ah2BZw9F1-l8GOxloFCBgGvKI_kHDGg1VCjV_zxhNEky6sbpiPnt9ZIpojJ7xm32O7I6S8u1JBIOO3mnR5Yrrc7vlKfCqIy")


;; Notes events

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

(defmethod event-handler ::set-error [event]
  (let [{:keys [error]} event]
    (println error)
    {:dispatch {:event/type ::stop-sync :source ::fetch-notes-info}}))

(defmethod event-handler ::start-sync [event]
  (let [{:keys [source context]} event]
    {:context (fx/sub-ctx context queries/assoc-syncing source true)}))

(defmethod event-handler ::stop-sync [event]
  (let [{:keys [source context]} event]
    {:context (fx/sub-ctx context queries/assoc-syncing source false)}))

(defmethod event-handler ::fetch-notes-info [_]
  {:dispatch {:event/type ::start-sync :source ::fetch-notes-info}
   :fetch-notes-info {:token access-token
                      :dispatch-success ::set-notes-info
                      :dispatch-error ::set-error}})


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
                        :fetch-notes-info effects/fetch-notes-info})))


;; Playground

(comment (dispatch-event {:event/type ::set-notes-info :data nil}))
(comment (dispatch-event {:event/type ::set-screen :data :all_notes}))
(comment @*state)