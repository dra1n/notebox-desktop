(ns notebox.core
  (:gen-class)
  (:require [cljfx.api :as fx]
            [notebox.app-db.db :refer [*state event-handler]]
            [notebox.screens.all-notes.views :refer [all-notes]])
  (:import [javafx.application Platform]))

(defn root [& _args]
  {:fx/type :stage
   :showing true
   :width 1024
   :height 600
   :min-width 1024
   :min-height 600
   :title "Notebox"
   :scene {:fx/type :scene
           :stylesheets #{"styles.css"}
           :root {:fx/type all-notes}}})

(def renderer
  (fx/create-renderer
   :middleware (comp
                fx/wrap-context-desc
                (fx/wrap-map-desc (fn [_] {:fx/type root})))
   :opts {:fx.opt/type->lifecycle #(or (fx/keyword->lifecycle %)
                                       (fx/fn->lifecycle-with-context %))
          :fx.opt/map-event-handler event-handler}))


(fx/mount-renderer *state renderer)

(defn -main [& _args]
  (Platform/setImplicitExit true)
  (renderer {:fx/type root}))

(comment (renderer {:fx/type root}))
