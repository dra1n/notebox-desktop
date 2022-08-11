(ns notebox.scenes.login.styles
  (:require [cljfx.css :as css]
            [notebox.common.styles :as s]))

(def style
  (css/register
   ::style
   {".login" {:-fx-alignment :center
              :-fx-background-color (::s/bg-lighter s/style)}
    ".login-logo" {:-fx-padding [-60 0 (::s/spacer-lg s/style) 0]}}))