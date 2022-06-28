(ns notebox.fragments.note.styles
  (:require [cljfx.css :as css]
            [notebox.common.styles :as s]))

(def style
  (css/register
   ::style
   {".note-content" {:-fx-padding [(::s/spacer-md s/style) (::s/spacer-xl s/style)]}
    ".note-title-wrapper" {:-fx-padding [(::s/spacer-md s/style) 0 (::s/spacer-lg s/style) 0]
                           :-fx-line-spacing (::s/line-height-md s/style)}
    ".note-book" {:-fx-text-fill (::s/text-grey s/style)
                  :-fx-font-size (::s/font-size-sm s/style)}
    ".note-title" {:-fx-fill (::s/text s/style)
                   :-fx-font-size (::s/font-size-lg s/style)
                   :-fx-font-weight "bold"
                   :-fx-padding [(::s/spacer-md s/style) 0 (::s/spacer-lg s/style) 0]}
    ".note-text-wrapper" {:-fx-padding [0 0 (::s/spacer-md s/style) 0]
                          :-fx-line-spacing (::s/line-height-md s/style)}
    ".note-text" {:-fx-fill (::s/text s/style)
                  :-fx-font-size (::s/font-size-md s/style)}
    ".note-tags" {:-fx-padding [(::s/spacer-md s/style) (::s/spacer-xl s/style)]
                  :-fx-border-style "solid hidden hidden hidden"
                  :-fx-border-width 1
                  :-fx-border-color (::s/bg-light s/style)}
    ".note-tag-title" {:-fx-text-fill (::s/text-grey s/style)
                       :-fx-font-size (::s/font-size-sm s/style)
                       :-fx-padding [0 0 (::s/spacer-sm s/style) 0]}
    ".note-tag-wrapper" {:-fx-padding [0 (::s/spacer-sm s/style) 0 0]}
    ".note-tag" {:-fx-font-size (::s/font-size-sm s/style)
                 :-fx-line-spacing (::s/line-height-md s/style)
                 :-fx-padding [0 (::s/spacer-xs s/style)]
                 :-fx-background-color (::s/cyan-light s/style)
                 :-fx-background-radius 3}}))