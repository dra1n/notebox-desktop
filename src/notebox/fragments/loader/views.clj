(ns notebox.fragments.loader.views
  (:require [clojure.java.io :as io]))

(defn loader [_]
  {:fx/type :image-view
   :image {:url (.toString (io/resource "images/oval.png"))}})


;; Playground

(comment (io/resource "images/oval.svg"))
(comment (.toString (io/resource "images/oval.svg")))