(ns notebox.notes.db)

(defmulti event-handler :event/type)

(def *state
  (atom {:notes-info {}
         :books {}
         :notes {}}))
