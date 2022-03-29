(ns notebox.app-db.queries
  (:require [cljfx.api :as fx]))


;; Notes

(def notes-db-key :notebox.notes)

(defn hovered-book [context]
  (fx/sub-val context get-in [notes-db-key :hovered-book]))

(defn assoc-hovered-book [context value]
  (fx/swap-context context assoc-in [notes-db-key :hovered-book] value))

(defn visible-books [context]
  (fx/sub-val context get-in [notes-db-key :visible-books]))

(defn assoc-visible-book [context value]
  (fx/swap-context
   context
   update-in
   [notes-db-key :visible-books]
   (fnil conj #{})
   value))

(defn remove-visible-book [context value]
  (fx/swap-context context update-in [notes-db-key :visible-books] disj value))

(defn assoc-last-active-book [context value]
  (fx/swap-context context assoc-in [notes-db-key :last-active-book] value))

(defn notes-info [context]
  (fx/sub-val context get-in [notes-db-key :notes-info]))

(defn books-count [context]
  (-> (fx/sub-ctx context notes-info)
      (count)))

(defn notes-count [context]
  (->> (fx/sub-ctx context notes-info)
       (map :count)
       (apply +)))

(defn assoc-notes-info [context value]
  (fx/swap-context context assoc-in [notes-db-key :notes-info] value))


;; Scences

(def scene-db-key :notebox.scene)

(defn scene [context]
  (fx/sub-val context get-in [scene-db-key :current]))

(defn assoc-scene [context value]
  (fx/swap-context context assoc-in [scene-db-key :current] value))


;; Styles

(def styles-db-key :notebox.styles)

(defn styles [context]
  (fx/sub-val context styles-db-key))

(defn assoc-styles [context value]
  (fx/swap-context context assoc styles-db-key value))