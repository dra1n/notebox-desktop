(ns notebox.app-db.co-effects
  (:require [notebox.app-db.db :refer [*state]]))

(defn state-co-effect []
  (deref *state))