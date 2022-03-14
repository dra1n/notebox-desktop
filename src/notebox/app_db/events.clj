(ns notebox.app-db.events
  (:require [cljfx.api :as fx]
            [notebox.app-db.db :refer [*state event-handler]]
            [notebox.app-db.queries :as queries]
            [notebox.app-db.effects :as effects]))

(def access-token "sl.BDsLNHbbyyrcyPygR5V_fhQ2ZKNOVN_8sJH2uFPXUAcA-7RDIpAgzRQKAahmdoQyFWva8bDTHbYpXFKPWPuJjmO9LhD1Mlncb0iFUB91RUJdr9kaZoZJdDpR6ziQAfReNsCrEhKbdfZC")


;; Notes events

(defmethod event-handler ::set-notes-info [event]
  (let [{:keys [data context]} event]
    {:context (fx/sub-ctx context queries/assoc-notes-info data)}))

(defmethod event-handler ::set-error [event]
  (let [{:keys [error]} event]
    (println error)
    {}))

(defmethod event-handler ::fetch-notes-info [_]
  {:fetch-notes-info {:token access-token
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