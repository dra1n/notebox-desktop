(ns notebox.app-db.queries
  (:require [cljfx.api :as fx]))


;; Notes

(def notes-db-key :notebox.notes)


(defn syncing? [context]
  (let [sync-values (fx/sub-val context get-in [notes-db-key :syncing])]
    (some true? (vals sync-values))))

(defn assoc-syncing [context source value]
  (fx/swap-context context assoc-in [notes-db-key :syncing source] value))


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

(defn assoc-notes-info [context value]
  (fx/swap-context context assoc-in [notes-db-key :notes-info] value))


(defn book [context book]
  (fx/sub-val context get-in [notes-db-key :notes book]))

(defn assoc-book [context book value]
  (fx/swap-context context assoc-in [notes-db-key :notes book] value))

(defn notes [context]
  (fx/sub-val context get-in [notes-db-key :notes]))


(defn books-count [context]
  (-> (fx/sub-ctx context notes-info)
      (count)))

(defn notes-count [context]
  (->> (fx/sub-ctx context notes-info)
       (map :count)
       (apply +)))


(defn last-active-note [context]
  (fx/sub-val context get-in [notes-db-key :last-active-note]))

(defn assoc-last-active-note [context value]
  (fx/swap-context context assoc-in [notes-db-key :last-active-note] value))


;; Subscenes

(defn subscene [context]
  (fx/sub-val context get-in [notes-db-key :subscene]))

(defn subscene-data [context]
  (fx/sub-val context get-in [notes-db-key :subscene-data]))

(defn assoc-subscene [context name data]
  (-> context
      (fx/swap-context assoc-in [notes-db-key :subscene] name)
      (fx/swap-context assoc-in [notes-db-key :subscene-data] data)))


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