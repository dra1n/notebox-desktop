(ns notebox.scenes.all-notes.styles
  (:require [cljfx.css :as css]
            [notebox.scenes.shared.styles :as s]))

(def style
  (css/register
   ::style
   (merge
    s/style
    {"*" {:-fx-font-family (::s/font-family-base s/style)
          :-fx-text-fill (::s/text s/style)}

     ".sidemenu" {:-fx-background-color (::s/bg-dark s/style)}

     ".notelist" {:-fx-font-size (::s/font-size-sm s/style)
                  :-fx-background-color (::s/bg-lighter s/style)
                  :-fx-border-style "hidden solid hidden hidden"
                  :-fx-border-width 1
                  :-fx-border-color (::s/bg-light s/style)}

     ".notes-count" {:-fx-padding [(::s/spacer-md s/style) (::s/spacer-lg s/style)]
                     :-fx-border-style "hidden hidden solid hidden"
                     :-fx-border-width 1
                     :-fx-border-color (::s/bg-light s/style)
                     :-fx-pref-width (::s/notelist-width s/style)}

     ".notelist-book" {:-fx-padding [(::s/spacer-md s/style) (::s/spacer-lg s/style)]
                       :-fx-border-style "hidden hidden solid hidden"
                       :-fx-border-width 1
                       :-fx-border-color (::s/bg-light s/style)}

     ".notelist-book-title" {:-fx-font-family (::s/font-family-base s/style)}

     ".notelist-book-subtitle" {:-fx-text-fill (::s/text-grey-dark s/style)
                                :-fx-font-size (::s/font-size-xs s/style)}

     ".note" {:-fx-background-color (::s/white s/style)}})))

