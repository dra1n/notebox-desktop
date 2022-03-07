(ns notebox.app-db.queries)


;; Notes

(def notes-db-key :notebox.notes)

(defn notes-info [db]
  (-> db notes-db-key :notes-info))

(defn assoc-notes-info [db value]
  (assoc-in db [notes-db-key :notes-info] value))