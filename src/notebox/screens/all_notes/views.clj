(ns notebox.screens.all-notes.views
  (:require [notebox.fragments.sidemenu.views :refer [sidemenu]]
            [notebox.fragments.notes-list.views :refer [notes-list]]
            [notebox.fragments.note.views :refer [note]]))

(defn all-notes [& _args]
  {:fx/type :h-box
   :children [{:fx/type :v-box
               :style {:-fx-background-color "#2c292b"}
               :pref-width 300
               :children [{:fx/type sidemenu}]}
              {:fx/type :v-box
               :style {:-fx-background-color "#f6f6f6"}
               :pref-width 300
               :children [{:fx/type notes-list}]}
              {:fx/type :v-box
               :style {:-fx-background-color "#fff"}
               :h-box/hgrow :always
               :children [{:fx/type note}]}]})