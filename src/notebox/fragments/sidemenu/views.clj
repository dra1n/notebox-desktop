(ns notebox.fragments.sidemenu.views
  (:require [notebox.app-db.events :as events]))

(defn sidemenu [& _args]
  {:fx/type :v-box
   :children [{:fx/type :label
               :text "side menu"}
              {:fx/type :button
               :text "refresh"
               :on-action (fn [_]
                            (events/dispatch-event
                             {:event/type ::events/fetch-notes-info}))}]})