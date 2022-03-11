(ns notebox.scenes.all-notes.styles
  (:require [cljfx.css :as css]
            [notebox.scenes.shared.styles :as s]))

(def style
  (css/register
   ::style
   (merge
    s/style
    {".notes-count" {:-fx-padding [(::s/spacer-md s/style) (::s/spacer-lg s/style)]}

     ".sidemenu" {:-fx-background-color (::s/bg-dark s/style)}

     ".notelist" {:-fx-background-color (::s/bg-lighter s/style)}

     ".note" {:-fx-background-color (::s/white s/style)}})))

