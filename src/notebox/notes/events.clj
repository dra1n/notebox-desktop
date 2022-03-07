(ns notebox.notes.events
  (:require [cljfx.api :as fx]
            [notebox.notes.db :refer [*state event-handler]]
            [notebox.notes.queries :as queries]
            [notebox.notes.co-effects :as co-effects]
            [notebox.notes.effects :as effects]))

(defmethod event-handler ::set-notes-info [event]
  (let [{:keys [data state]} event]
    {:state (queries/assoc-notes-info state data)}))

(def notes-event
  (-> event-handler
      (fx/wrap-co-effects {:state co-effects/state-co-effect})
      (fx/wrap-effects {:state effects/state-effect})))

(notes-event {:event/type ::set-notes-info :data "hey"})

@*state