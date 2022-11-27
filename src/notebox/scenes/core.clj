(ns notebox.scenes.core
  (:require [notebox.scenes.splash.views :refer [splash]]
            [notebox.scenes.all-notes.views :refer [all-notes]]
            [notebox.scenes.login.views :refer [login]]
            [notebox.common.styles :as common.styles]
            [notebox.fragments.note.styles :as note.styles]
            [notebox.fragments.logo.styles :as logo.styles]
            [notebox.fragments.auth.styles :as auth.styles]
            [notebox.fragments.notes-list.styles :as notes-list.styles]
            [notebox.fragments.sidemenu.styles :as sidemenu.styles]
            [notebox.scenes.all-notes.styles :as all-notes.styles]
            [notebox.scenes.splash.styles :as splash.styles]
            [notebox.scenes.login.styles :as login.styles]))

(def scenes {:splash splash
             :all-notes all-notes
             :login login})

(def styles {:common common.styles/style
             :logo logo.styles/style
             :auth auth.styles/style
             :all-notes all-notes.styles/style
             :splash splash.styles/style
             :login login.styles/style
             :note note.styles/style
             :note-list notes-list.styles/style
             :sidemenu sidemenu.styles/style})