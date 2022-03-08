(ns notebox.app-db.events
  (:require [cljfx.api :as fx]
            [notebox.app-db.db :refer [*state event-handler]]
            [notebox.app-db.queries :as queries]
            [notebox.app-db.effects :as effects]))


;; Notes events

(defmethod event-handler ::set-notes-info [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-notes-info data)}))


;; Screen events

(defmethod event-handler ::set-screen [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-screen data)}))


;; Main dispatch function

(def dispatch-event
  (-> event-handler
      (fx/wrap-co-effects {:context (fx/make-deref-co-effect *state)})
      (fx/wrap-effects {:context (fx/make-reset-effect *state)
                        :dispatch fx/dispatch-effect
                        :fetch-notes-info effects/fetch-notes-info})))


;; Playground

(comment (dispatch-event {:event/type ::set-notes-info :data "hey"}))
(comment (dispatch-event {:event/type ::set-screen :data :all_notes}))
(comment @*state)