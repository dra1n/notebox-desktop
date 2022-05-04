(ns notebox.fragments.note.views)

(defn note [{:keys [note]}]
  {:fx/type :scroll-pane
   :style-class "scroll-pane"
   :fit-to-width true
   :content {:fx/type :v-box
             :style-class "note-content"
             :children [{:fx/type :text-flow
                         :style-class "note-title-wrapper"
                         :pref-width 400
                         :children [{:fx/type :text
                                     :style-class "note-title"
                                     :cache true
                                     :cache-hint :speed
                                     :text (:title note)}]}
                        {:fx/type :text-flow
                         :style-class "note-text-wrapper"
                         :pref-width 400
                         :children [{:fx/type :text
                                     :style-class "note-text"
                                     :cache true
                                     :cache-hint :speed
                                     :text (:text note)}]}]}})