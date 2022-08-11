(ns notebox.scenes.splash.styles
  (:require [cljfx.css :as css]
            [notebox.common.styles :as s]))

(def style
  (css/register
   ::style
   {".splash" {:-fx-alignment :center
               :-fx-background-color (::s/bg-lighter s/style)}
    ".splash-logo" {:-fx-padding [0 0 (::s/spacer-md s/style) 0]}}))