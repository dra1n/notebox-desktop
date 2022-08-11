(ns notebox.fragments.auth.styles
  (:require [cljfx.css :as css]
            [notebox.common.styles :as s]))

(def style
  (css/register
   ::style
   {".auth-container" {:-fx-background-color (::s/white s/style)
                       :-fx-padding (::s/spacer-xxl s/style)}
    ".auth-title" {:-fx-font-size (::s/font-size-lg s/style)
                   :-fx-padding [0 0 (::s/spacer-md s/style)]}
    ".auth-link" {:-fx-font-size (::s/font-size-md s/style)
                  :-fx-pref-height "24px"
                  :-fx-underline true
                  :-fx-text-fill (::s/cyan s/style)}
    ".auth-step" {:-fx-padding [0 0 (::s/spacer-sm s/style) 0]}
    ".auth-label" {:-fx-font-size (::s/font-size-md s/style)
                   :-fx-pref-height "24px"}
    ".auth-start-button" {:-fx-padding [(::s/spacer-md s/style) 0 0 0]}
    ".auth-label-helper" {:-fx-text-fill (::s/text-grey s/style)
                          :-fx-font-size (::s/font-size-sm s/style)
                          :-fx-padding [0 0 0 12]}}))