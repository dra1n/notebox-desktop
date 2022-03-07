(ns notebox.core
  (:gen-class)
  (:require [cljfx.api :as fx]
            [notebox.notes.db :refer [*state event-handler]])
  (:import [javafx.application Platform]))

(defn hello-world [& args]
  {:fx/type :label
   :text "Hello, World!"})

(defn root [& args]
  {:fx/type :stage
   :showing true
   :width 1024
   :height 600
   :min-width 1024
   :min-height 600
   :title "Notebox"
   :scene {:fx/type :scene
           :root {:fx/type :h-box
                  :children [{:fx/type :v-box
                              :style {:-fx-background-color "#2c292b"}
                              :pref-width 300
                              :children [{:fx/type hello-world}]}
                             {:fx/type :v-box
                              :style {:-fx-background-color "#f6f6f6"}
                              :pref-width 300
                              :children [{:fx/type hello-world}]}
                             {:fx/type :v-box
                              :style {:-fx-background-color "#fff"}
                              :h-box/hgrow :always
                              :children [{:fx/type hello-world}]}]}}})

(def renderer
  (fx/create-renderer
   :middleware (fx/wrap-map-desc (fn [state]
                                   {:fx/type root
                                    :state state}))
   :opts {:fx.opt/map-event-handler event-handler}))

(fx/mount-renderer *state renderer)

(defn -main [& args]
  (Platform/setImplicitExit true)
  (renderer {:fx/type root}))

(comment (renderer {:fx/type root}))
