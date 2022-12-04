(ns notebox.fragments.sidemenu.styles
  (:require [cljfx.css :as css]
            [notebox.common.styles :as s]))

(def style
  (css/register
   ::style
   {".sidemenu-wrapper" {}
    ".sidemenu-toggle-button" {:-fx-background-color (::s/bg-medium s/style)
                               :-fx-text-fill (::s/text-grey-light s/style)
                               :-fx-pref-height 40
                               :-fx-padding [0 (::s/spacer-sm s/style)]}
    ".sidemenu-menuitem" {:-fx-text-fill (::s/text-grey-light s/style)
                          :-fx-pref-height 40
                          :-fx-padding [(::s/spacer-sm s/style) (::s/spacer-lg s/style)]}
    ".sidemenu-menuitem:hover" {:-fx-background-color (::s/bg-medium s/style)}
    ".account-info" {:-fx-padding [(::s/spacer-md s/style) (::s/spacer-lg s/style)]
                     :-fx-border-style "hidden hidden solid hidden"
                     :-fx-border-width 1
                     :-fx-border-color (::s/bg-medium s/style)}
    ".account-info-abbr" {:-fx-padding [(::s/spacer-sm s/style) (::s/spacer-xxs s/style)]
                          :-fx-border-style "hidden hidden solid hidden"
                          :-fx-border-width 1
                          :-fx-border-color (::s/bg-medium s/style)}
    ".account-name-label" {:-fx-text-fill (::s/text-grey-light s/style)
                           :-fx-font-weight "bold"
                           :-fx-font-size (::s/font-size-sm s/style)}
    ".account-email" {:-fx-padding [2 0 0 0]}
    ".account-email-text" {:-fx-padding [0 0 0 5]}
    ".account-email-label" {:-fx-text-fill (::s/text-grey s/style)
                            :-fx-font-size (::s/font-size-xs s/style)}}))