(ns notebox.scenes.all-notes.views
  (:require [cljfx.css :as css]
            [notebox.fragments.sidemenu.views :refer [sidemenu]]
            [notebox.fragments.notes-list.views :refer [notes-list]]
            [notebox.fragments.note.views :refer [note]]
            [notebox.scenes.shared.styles :as s]
            [notebox.scenes.all-notes.styles :refer [style]]))

(defn all-notes [& _args]
  {:fx/type :scene
   :stylesheets [(::css/url style)]
   :root {:fx/type :h-box
          :children [{:fx/type :v-box
                      :style-class "sidemenu"
                      :pref-width (::s/menu-width s/style)
                      :children [{:fx/type sidemenu}]}
                     {:fx/type :v-box
                      :style-class "notelist"
                      :pref-width (::s/notelist-width s/style)
                      :children [{:fx/type notes-list}]}
                     {:fx/type :v-box
                      :style-class "note"
                      :h-box/hgrow :always
                      :children [{:fx/type note}]}]}})