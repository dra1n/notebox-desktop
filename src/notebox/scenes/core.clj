(ns notebox.scenes.core
  (:require [notebox.scenes.all-notes.views :refer [all-notes]]
            [notebox.scenes.all-notes.styles :as all-notes.styles]))

(def scenes {::all-notes all-notes})

(def styles {:all-notes all-notes.styles/style})