(ns notebox.scenes.login.views
  (:require [notebox.fragments.auth.views :as auth]
            [notebox.app-db.events :as events]))

(defn login [_]
  {:fx/type :scene
   :root {:fx/type :v-box
          :children [{:fx/type auth/auth}
                     {:fx/type :button
                      :text "switch scene"
                      :on-action {:event/type ::events/set-scene :data :all-notes}}]}})