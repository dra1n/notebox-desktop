(ns notebox.fragments.notes-list.views
  (:require [cljfx.api :as fx]
            [inflections.core :as inf]
            [notebox.app-db.queries :as queries]))

(defn book-block [{:keys [book notes]}]
  {:fx/type :v-box
   :style-class "notelist-book"
   :children [{:fx/type :label
               :style-class "notelist-book-title"
               :text (:title book)}
              {:fx/type :label
               :style-class "notelist-book-subtitle"
               :text (inf/pluralize (:count book) "note")}]})

(defn notes-list [{:keys [fx/context]}]
  (let [books (fx/sub-ctx context queries/notes-info)
        books-count (fx/sub-ctx context queries/books-count)
        notes-count (fx/sub-ctx context queries/notes-count)]
    {:fx/type :v-box
     :children [{:fx/type :v-box
                 :children [{:fx/type :label
                             :style-class "notes-count"
                             :text (str
                                    (inf/pluralize books-count "book") ", "
                                    (inf/pluralize notes-count "note"))}
                            {:fx/type :v-box
                             :children (mapv (fn [book] {:fx/type book-block :book book})
                                             books)}]}]}))