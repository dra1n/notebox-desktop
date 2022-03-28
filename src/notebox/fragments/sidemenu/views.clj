(ns notebox.fragments.sidemenu.views
  (:require [notebox.app-db.events :as events]))

(defn sidemenu [& _args]
  {:fx/type :v-box
   :children [{:fx/type :label
               :text "side menu"}
              {:fx/type :button
               :text "refresh"
               :on-action {:event/type ::events/fetch-notes-info}}
              {:fx/type :button
               :text "switch scene"
               :on-action {:event/type ::events/set-scene :data :login}}]})