(ns notebox.app-db.queries
  (:require [cljfx.api :as fx]))


;; Notes

(def notes-db-key :notebox.notes)

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


;; Screens

(def screen-db-key :notebox.screen)

(defn screen [context]
  (fx/sub-val context get-in [screen-db-key :current]))

(defn assoc-screen [context value]
  (fx/swap-context context assoc-in [screen-db-key :current] value))