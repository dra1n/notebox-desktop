(ns notebox.scenes.login.views
  (:require [cljfx.api :as fx]
            [notebox.app-db.events :as events]))

(defn login [{:keys [fx/context]}]
  {:fx/type :scene
   :root {:fx/type :v-box
          :children [{:fx/type :label
                      :text "login screen"}
                     {:fx/type :button
                      :text "switch scene"
                      :on-action {:event/type ::events/set-scene :data :all-notes}}]}})