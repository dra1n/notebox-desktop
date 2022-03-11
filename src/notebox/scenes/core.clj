(ns notebox.scenes.core
  (:require [notebox.scenes.all-notes.views :refer [all-notes]]))

(def scenes {::all-notes all-notes})