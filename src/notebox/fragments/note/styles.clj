(ns notebox.fragments.note.styles
  (:require [cljfx.css :as css]))

(def style
  (css/register
   ::style
   {".note-title" {}
    ".note-text" {}}))