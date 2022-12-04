(ns notebox.scenes.all-notes.styles
  (:require [cljfx.css :as css]
            [notebox.common.styles :as s]))

(def style
  (css/register
   ::style
   {".sidemenu" {:-fx-background-color (::s/bg-dark s/style)}

    ".notelist" {:-fx-font-size (::s/font-size-sm s/style)
                 :-fx-background-color (::s/bg-lighter s/style)
                 :-fx-border-style "hidden solid hidden hidden"
                 :-fx-border-width 1
                 :-fx-border-color (::s/bg-light s/style)}

    ".empty-note-add-link" {:-fx-font-size (::s/font-size-md s/style)
                            :-fx-pref-height "24px"
                            :-fx-underline true
                            :-fx-padding [0 (::s/spacer-xs s/style) 0 0]
                            :-fx-text-fill (::s/cyan s/style)}

    ".empty-note-description" {:-fx-padding [0 0 0 (::s/spacer-xl s/style)]}

    ".empty-note-title" {:-fx-font-size (::s/font-size-lg s/style)
                         :-fx-font-weight 500
                         :-fx-padding [0 0 (::s/spacer-md s/style)]}

    ".empty-note-disclaimer" {:-fx-font-size (::s/font-size-md s/style)}

    ".note" {:-fx-background-color (::s/white s/style)}}))

