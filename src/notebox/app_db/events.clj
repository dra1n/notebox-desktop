(ns notebox.app-db.events
  (:require [cljfx.api :as fx]
            [notebox.app-db.db :refer [*state event-handler]]
            [notebox.app-db.queries :as queries]
            [notebox.app-db.co-effects :as co-effects]
            [notebox.app-db.effects :as effects]))


;; Notes events

(defmethod event-handler ::set-notes-info [event]
  (let [{:keys [data state]} event]
    {:state (queries/assoc-notes-info state data)}))

(def dispatch-event
  (-> event-handler
      (fx/wrap-co-effects {:state co-effects/state-co-effect})
      (fx/wrap-effects {:state effects/state-effect})))

(comment (dispatch-event {:event/type ::set-notes-info :data "hey"}))

(comment @*state)