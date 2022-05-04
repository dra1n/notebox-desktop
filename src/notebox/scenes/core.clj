(ns notebox.scenes.core
  (:require [notebox.scenes.all-notes.views :refer [all-notes]]
            [notebox.scenes.login.views :refer [login]]
            [notebox.common.styles :as common.styles]
            [notebox.fragments.note.styles :as note.styles]
            [notebox.fragments.notes-list.styles :as notes-list.styles]
            [notebox.scenes.all-notes.styles :as all-notes.styles]))

(def scenes {:all-notes all-notes
             :login login})

(def styles {:common common.styles/style
             :all-notes all-notes.styles/style
             :note note.styles/style
             :note-list notes-list.styles/style})