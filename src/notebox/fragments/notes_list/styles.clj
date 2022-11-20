(ns notebox.fragments.notes-list.styles
  (:require [cljfx.css :as css]
            [notebox.common.styles :as s]))

(def style
  (css/register
   ::style
   {".notes-search" {:-fx-padding [(::s/spacer-md s/style) (::s/spacer-lg s/style) 0]}
    ".notes-count" {:-fx-padding [(::s/spacer-sm s/style) (::s/spacer-lg s/style) (::s/spacer-md s/style)]
                    :-fx-border-style "hidden hidden solid hidden"
                    :-fx-border-width 1
                    :-fx-border-color (::s/bg-light s/style)
                    :-fx-font-size (::s/font-size-xs s/style)
                    :-fx-text-fill (::s/text-grey-dark s/style)
                    :-fx-pref-width (::s/notelist-width s/style)}

    ".notelist-book" {:-fx-padding [(::s/spacer-md s/style) (::s/spacer-md s/style)]
                      :-fx-border-style "hidden hidden solid hidden"
                      :-fx-border-width 1
                      :-fx-border-color (::s/bg-light s/style)}

    ".notelist-note" {:-fx-padding [(::s/spacer-md s/style) (::s/spacer-md s/style)]
                      :-fx-background-color (::s/white s/style)
                      :-fx-border-style "hidden hidden solid hidden"
                      :-fx-border-width 1
                      :-fx-border-color (::s/bg-light s/style)
                      :-fx-pref-height 10}

    ".notelist-note-active" {:-fx-background-color (::s/cyan-lightest s/style)}

    ".notelist-book-title" {}

    ".notelist-open-book-icon" {:-fx-padding [3 0 0 0]}

    ".notelist-closed-book-icon" {:-fx-padding [4 0 0 0]}

    ".notelist-book-subtitle" {:-fx-text-fill (::s/text-grey-dark s/style)
                               :-fx-font-size (::s/font-size-xs s/style)}

    ".notelist-note-title" {:-fx-font-size (::s/font-size-md s/style)
                            :-fx-font-weight "bold"
                            :-fx-padding [0 0 (::s/spacer-xs s/style) 0]}

    ".notelist-note-text" {:-fx-text-fill (::s/text-grey-dark s/style)}}))