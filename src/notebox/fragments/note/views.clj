(ns notebox.fragments.note.views
  (:require [notebox.common.svg-path.views :refer [svg-path]]))


(defn book-icon [& _args]
  {:fx/type :v-box
   :pref-width 18
   :alignment :center-left
   :children [{:fx/type svg-path
               :scale-x 1
               :scale-y 1
               :content  "m9 7.734375v-7.21875c0-.28574219-.21495536-.515625-.48214286-.515625h-6.58928571c-1.06473214 0-1.92857143.92382813-1.92857143 2.0625v6.875c0 1.1386719.86383929 2.0625 1.92857143 2.0625h6.58928571c.2671875 0 .48214286-.2298828.48214286-.515625v-.34375c0-.16113281-.0703125-.30722656-.17879464-.40175781-.084375-.33085938-.084375-1.27402344 0-1.60488281.10848214-.09238282.17879464-.23847657.17879464-.39960938zm-6.42857143-4.85546875c0-.07089844.05424107-.12890625.12053572-.12890625h4.25892857c.06629464 0 .12053571.05800781.12053571.12890625v.4296875c0 .07089844-.05424107.12890625-.12053571.12890625h-4.25892857c-.06629465 0-.12053572-.05800781-.12053572-.12890625zm0 1.375c0-.07089844.05424107-.12890625.12053572-.12890625h4.25892857c.06629464 0 .12053571.05800781.12053571.12890625v.4296875c0 .07089844-.05424107.12890625-.12053571.12890625h-4.25892857c-.06629465 0-.12053572-.05800781-.12053572-.12890625zm5.090625 5.37109375h-5.73348214c-.35558036 0-.64285714-.30722656-.64285714-.6875 0-.378125.28928571-.6875.64285714-.6875h5.73348214c-.03816964.36738281-.03816964 1.00761719 0 1.375z"}]})

(defn tags-view [{:keys [tags]}]
  {:fx/type :v-box
   :style-class "note-tags"
   :children [{:fx/type :label
               :style-class "note-tag-title"
               :text "Tags"}
              {:fx/type :h-box
               :children (mapv (fn [t]
                                 {:fx/type :v-box
                                  :style-class "note-tag-wrapper"
                                  :children [{:fx/type :v-box
                                              :alignment :center-left
                                              :style-class "note-tag"
                                              :children [{:fx/type :label
                                                          :text t}]}]}) tags)}]})

(defn note [{:keys [note book]}]
  {:fx/type :v-box
   :children [{:fx/type :v-box
               :v-box/vgrow :always
               :children [{:fx/type :scroll-pane
                           :style-class "scroll-pane"
                           :fit-to-width true
                           :content {:fx/type :v-box
                                     :style-class "note-content"
                                     :children [{:fx/type :text-flow
                                                 :pref-width 400
                                                 :children [{:fx/type :h-box
                                                             :children [{:fx/type book-icon}
                                                                        {:fx/type :label
                                                                         :style-class "note-book"
                                                                         :text (:title book)}]}]}
                                                {:fx/type :text-flow
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
                                                             :text (:text note)}]}]}}]}
              (if (seq (:tags note))
                {:fx/type tags-view
                 :tags (:tags note)}
                {:fx/type :v-box :children []})]})