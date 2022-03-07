(ns notebox.app-db.db)

(defmulti event-handler :event/type)

(def *state (atom {}))
