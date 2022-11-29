(ns notebox.core
  (:gen-class)
  (:require [cljfx.api :as fx]
            [utils.core :refer [expand-home]]
            [notebox.app-db.db :refer [*state state-path]]
            [notebox.app-db.events :as events :refer [dispatch-event]]
            [notebox.app-db.queries :as queries]
            [notebox.scenes.core :as scenes])
  (:import [javafx.application Platform]
           [de.codecentric.centerdevice.javafxsvg SvgImageLoaderFactory]
           [de.codecentric.centerdevice.javafxsvg.dimension PrimitiveDimensionProvider]))

(SvgImageLoaderFactory/install (PrimitiveDimensionProvider.))

(defn root [{:keys [fx/context]}]
  {:fx/type :stage
   :on-close-request (fn [_]
                       (let [state (fx/sub-val @*state identity)]
                         (spit (expand-home state-path) (pr-str state))
                         (System/exit 0)))
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
  (dispatch-event {:event/type ::events/initialize})
  (dispatch-event {:event/type ::events/set-styles :data scenes/styles})
  (fx/mount-renderer *state renderer))


;; Playground

(comment
  ;; to iterate during development on style, add a watch to var that updates style in app
  ;; state...
  (add-watch
   #'scenes/styles
   :refresh-app
   (fn [_ _ _ _] (dispatch-event {:event/type ::events/set-styles :data scenes/styles})))
  ;; ... and remove it when you are done
  (remove-watch #'scenes/styles :refresh-app))

(comment (dispatch-event {:event/type ::events/initialize}))
(comment (dispatch-event {:event/type ::events/set-styles :data scenes/styles}))
(comment (fx/mount-renderer *state renderer))
(comment scenes/styles)
