(ns notebox.core
  (:gen-class)
  (:require [cljfx.api :as fx]
            [notebox.app-db.db :refer [*state]]
            [notebox.app-db.events :as events :refer [dispatch-event]]
            [notebox.app-db.queries :as queries]
            [notebox.scenes.core :as scenes])
  (:import [javafx.application Platform]))

(defn root [{:keys [fx/context]}]
  {:fx/type :stage
   :showing true
   :width 1024
   :height 600
   :min-width 1024
   :min-height 600
   :title "Notebox"
   :scene {:fx/type (get scenes/scenes (fx/sub-ctx context queries/scene))}})

(def renderer
  (fx/create-renderer
   :middleware (comp
                fx/wrap-context-desc
                (fx/wrap-map-desc (fn [_] {:fx/type root})))
   :opts {:fx.opt/type->lifecycle #(or (fx/keyword->lifecycle %)
                                       (fx/fn->lifecycle-with-context %))
          :fx.opt/map-event-handler dispatch-event}))

(defn -main [& _args]
  (Platform/setImplicitExit true)
  (dispatch-event {:event/type ::events/set-scene :data ::scenes/all-notes})
  (fx/mount-renderer *state renderer))


;; Playground

(comment (dispatch-event {:event/type ::events/set-scene :data ::scenes/all-notes}))
(comment (fx/mount-renderer *state renderer))
