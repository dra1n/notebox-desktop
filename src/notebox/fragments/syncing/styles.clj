(ns notebox.fragments.syncing.styles
  (:require [cljfx.css :as css]
            [notebox.common.styles :as s]))

(def style
  (css/register
   ::style
   {".syncing-block" {:-fx-border-style "solid inside"
                      :-fx-border-width 1
                      :-fx-border-insets 8
                      :-fx-border-radius 8
                      :-fx-border-color (::s/bg-lighter s/style)
                      :-fx-background-color (::s/bg-lighter s/style)
                      :-fx-background-radius 8}
    ".syncing-spinner-block" {:-fx-padding [2 (::s/spacer-xs s/style) 0 0]}}))