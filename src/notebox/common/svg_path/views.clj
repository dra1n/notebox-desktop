(ns notebox.common.svg-path.views
  (:require [notebox.common.styles :as s]))

(defn svg-path [{:keys [content hovered? scale-x scale-y]}]
  {:fx/type :svg-path
   :fill (if hovered?
           (::s/text-grey s/style)
           (::s/text-grey-slight s/style))
   :scale-x (or scale-x 1)
   :scale-y (or scale-y 1)
   :content content})
