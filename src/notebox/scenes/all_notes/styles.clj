(ns notebox.scenes.all-notes.styles
  (:require [cljfx.css :as css]
            [notebox.common.styles :as s]))

(def style
  (css/register
   ::style
   {"*" {:-fx-font-family (::s/font-family-base s/style)}

    ".sidemenu" {:-fx-background-color (::s/bg-dark s/style)}

    ".notelist" {:-fx-font-size (::s/font-size-sm s/style)
                 :-fx-background-color (::s/bg-lighter s/style)
                 :-fx-border-style "hidden solid hidden hidden"
                 :-fx-border-width 1
                 :-fx-border-color (::s/bg-light s/style)}

    ".empty-note-description" {:-fx-padding [0 0 0 (::s/spacer-xl s/style)]}

    ".empty-note-title" {:-fx-font-size (::s/font-size-lg s/style)
                         :-fx-font-weight 500
                         :-fx-padding [0 0 (::s/spacer-md s/style)]}

    ".empty-note-disclaimer" {:-fx-font-size (::s/font-size-md s/style)}

    ".note" {:-fx-background-color (::s/white s/style)}}))

