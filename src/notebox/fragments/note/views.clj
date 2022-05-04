(ns notebox.fragments.note.views)

(defn note [{:keys [note]}]
  {:fx/type :v-box
   :children [{:fx/type :label
               :style-class "note-title"
               :text (:title note)}
              {:fx/type :text-flow
               :pref-width 400
               :children [{:fx/type :text
                           :cache true
                           :style-class "note-text"
                           :cache-hint :speed
                           :text (:text note)}]}]})