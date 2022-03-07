(ns notebox.notes.co-effects
  (:require [notebox.notes.db :refer [*state]]))

(defn state-co-effect []
  (deref *state))