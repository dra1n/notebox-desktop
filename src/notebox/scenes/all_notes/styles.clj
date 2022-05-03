(ns notebox.scenes.all-notes.styles
  (:require [cljfx.css :as css]
            [notebox.scenes.shared.styles :as s]))

(def style
  (css/register
   ::style
   (merge
    s/style
    {"*" {:-fx-text-fill (::s/text s/style)
          :-fx-font-family (::s/font-family-base s/style)}
     ".scroll-pane" {:-fx-background-color "transparent"
                     :-fx-background-insets 0
                     :-fx-padding 0}
     ".scroll-pane:focused" {:-fx-background-insets 0}
     ".scroll-pane > .corner" {:-fx-background-color "transparent"
                               :-fx-background-insets 0}
     ".scroll-pane > .viewport" {:-fx-background-color "transparent"}
     ".scroll-pane > .scroll-bar" {:-fx-background-color "transparent"}
     ".scroll-pane > .scroll-bar > .increment-button,
      .scroll-pane > .scroll-bar > .increment-button > .increment-arrow,
      .scroll-pane > .scroll-bar > .decrement-button,
      .scroll-pane > .scroll-bar > .decrement-button > .decrement-arrow" {:-fx-padding 0}
     ".scroll-pane > .scroll-bar > .thumb" {:-fx-background-color "#888"
                                            :-fx-background-radius 8
                                            :-fx-background-insets 0
                                            :-fx-padding 0}
     ".scroll-pane > .scroll-bar > .track" {:-fx-background-color "#0002"
                                            :-fx-background-radius 8}
     ".scroll-pane > .scroll-bar:vertical" {:-fx-pref-width 8}
     ".scroll-pane > .scroll-bar:horizontal" {:-fx-pref-height 8}

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

     ".notelist-note-text" {:-fx-text-fill (::s/text-grey-dark s/style)}

     ".note" {:-fx-background-color (::s/white s/style)}})))

