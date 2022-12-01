(ns notebox.fragments.loader.views
  (:require [clojure.java.io :as io]))

(defn loader [{:keys [requested-width]}]
  {:fx/type :image-view
   :image {:url (.toString (io/resource "images/oval.png"))
           :requested-width requested-width
           :preserve-ratio true}})


;; Playground

(comment (io/resource "images/oval.svg"))
(comment (.toString (io/resource "images/oval.svg")))