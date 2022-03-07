(ns notebox.notes.queries)

(def db-key :notebox.notes)

(defn notes-info [db]
  (-> db db-key :notes-info))

(defn assoc-notes-info [db value]
  (assoc-in db [db-key :notes-info] value))