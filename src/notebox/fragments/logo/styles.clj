(ns notebox.fragments.logo.styles
  (:require [cljfx.css :as css]))

(def style
  (css/register
   ::style
   {".logo-first" {:-fx-padding [0 5 0 0]}}))