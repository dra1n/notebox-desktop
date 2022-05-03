(ns notebox.fragments.note.views)

(defn note [{:keys [note]}]
  {:fx/type :v-box
   :children [{:fx/type :label
               :text (:title note)}
              {:fx/type :text
               :cache true
               :cache-hint :speed
               :wrapping-width 200
               :text (:text note)}]})