(ns notebox.common.styles
  (:require [cljfx.css :as css]))

(def style
  (css/register
   ::style
   {;; colors
    ::cyan "#3CB0BD"
    ::cyan-dark "#8ED6DE"
    ::cyan-light "#ADE4EA"
    ::cyan-lightest "#D9F5F8"
    ::pink-dark "#FFC7AB"
    ::pink-light "#FFD4BF"

    ::text "#323232"
    ::text-grey-dark "#696468"
    ::text-grey "#888888"
    ::text-grey-slight "#afafaf"
    ::text-grey-light "#C6C6C6"

    ::bg-black "#000"
    ::bg-dark "#2C292B"
    ::bg-medium "#696468"
    ::bg-light "#DFDFDF"
    ::bg-lighter "#F6F6F6"
    ::bg-orange-light "#FFE7DC"
    ::bg-orange "#FFD4BF"
    ::bg-orange-bright "#FF6D26"
    ::white "#fff"

    ;; font-size
    ::font-size-xs "12px"
    ::font-size-sm "14px"
    ::font-size-md "16px"
    ::font-size-lg "24px"
    ::font-size-xl "36px"

    ::line-height-md "0.28em"
    ::line-height-sm "0.1em"

    ;; space
    ::spacer-xxs "4px"
    ::spacer-xs "7px"
    ::spacer-sm "9px"
    ::spacer-md "16px"
    ::spacer-lg "24px"
    ::spacer-xl "33px"
    ::spacer-xxl "48px"

    ;; size
    ::menu-width 200
    ::notelist-width 300
    ::mobile-header-height 50

    ;; z-index
    ::zindex-subzero "-1"
    ::zindex-zero-value "0"
    ::zindex-min-value "1"
    ::zindex-mobile-header "3"
    ::zindex-mobile-sidemenu-overlay "4"
    ::zindex-mobile-sidemenu "5"
    ::zindex-syncing "9"
    ::zindex-flash-message "10"

    ;; buttons
    ".button-primary" {:-fx-background-color "#ADE4EA"
                       :-fx-text-fill "#323232"
                       :-fx-background-radius 3
                       :-fx-background-insets 0
                       :-fx-padding [4 16]
                       ":hover" {:-fx-background-color "#8ED6DE"}
                       ":pressed" {:-fx-background-color "#3CB0BD"}}

    ;; inputs    
    ".text-field" {:-fx-background-color "#fff"
                   :-fx-background-radius 3
                   :-fx-border-radius 3
                   :-fx-border-color "#C6C6C6"
                   :-fx-border-width 1
                   :-fx-prompt-text-fill "#afafaf"
                   :-fx-text-fill "#323232"}

    ;; scrollbar
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
    ".scroll-pane > .scroll-bar:horizontal" {:-fx-pref-height 8}}))