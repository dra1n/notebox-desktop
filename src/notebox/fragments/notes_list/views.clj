(ns notebox.fragments.notes-list.views
  (:require [cljfx.api :as fx]
            [inflections.core :as inf]
            [notebox.app-db.events :as events :refer [dispatch-event]]
            [notebox.app-db.queries :as queries]
            [notebox.scenes.shared.styles :as s]))


;; Handlers

(defn book-click-handler [book visible-books]
  (if (contains? visible-books book)
    (dispatch-event {:event/type ::events/remove-visible-book :data book})
    (do (dispatch-event {:event/type ::events/set-visible-book :data book})
        (dispatch-event {:event/type ::events/set-last-active-book :data book}))))


;; Components

(defn svg-path [{:keys [content hovered? scale-x scale-y]}]
  {:fx/type :svg-path
   :fill (if hovered?
           (::s/text-grey s/style)
           (::s/text-grey-slight s/style))
   :scale-x (or scale-x 1)
   :scale-y (or scale-y 1)
   :content content})

(defn closed-book-icon [{:keys [book fx/context]}]
  (let [hovered-book (fx/sub-ctx context queries/hovered-book)
        hovered? (= book hovered-book)]
    {:fx/type :v-box
     :pref-width 24
     :alignment :top-center
     :style-class "notelist-closed-book-icon"
     :children [{:fx/type svg-path
                 :hovered? hovered?
                 :scale-x 1.2
                 :scale-y 1.2
                 :content  "m9 7.734375v-7.21875c0-.28574219-.21495536-.515625-.48214286-.515625h-6.58928571c-1.06473214 0-1.92857143.92382813-1.92857143 2.0625v6.875c0 1.1386719.86383929 2.0625 1.92857143 2.0625h6.58928571c.2671875 0 .48214286-.2298828.48214286-.515625v-.34375c0-.16113281-.0703125-.30722656-.17879464-.40175781-.084375-.33085938-.084375-1.27402344 0-1.60488281.10848214-.09238282.17879464-.23847657.17879464-.39960938zm-6.42857143-4.85546875c0-.07089844.05424107-.12890625.12053572-.12890625h4.25892857c.06629464 0 .12053571.05800781.12053571.12890625v.4296875c0 .07089844-.05424107.12890625-.12053571.12890625h-4.25892857c-.06629465 0-.12053572-.05800781-.12053572-.12890625zm0 1.375c0-.07089844.05424107-.12890625.12053572-.12890625h4.25892857c.06629464 0 .12053571.05800781.12053571.12890625v.4296875c0 .07089844-.05424107.12890625-.12053571.12890625h-4.25892857c-.06629465 0-.12053572-.05800781-.12053572-.12890625zm5.090625 5.37109375h-5.73348214c-.35558036 0-.64285714-.30722656-.64285714-.6875 0-.378125.28928571-.6875.64285714-.6875h5.73348214c-.03816964.36738281-.03816964 1.00761719 0 1.375z"}]}))

(defn open-book-icon [{:keys [book fx/context]}]
  (let [hovered-book (fx/sub-ctx context queries/hovered-book)
        hovered? (= book hovered-book)]
    {:fx/type :v-box
     :style-class "notelist-open-book-icon"
     :pref-width 24
     :children [{:fx/type :group
                 :children [{:fx/type svg-path
                             :hovered? hovered?
                             :content "m16.4475 1.13959c.3051 0 .5525.23361.5525.5218v9.57161c0 .4182-.359.7573-.8019.7573h-5.7049c.0077.0373.0115.0754.0115.1136v.265c0 .3486-.2992.6311-.66822.6311h-2.48585c-.36906 0-.66824-.2825-.66824-.6311v-.265c0-.0389.00408-.0769.01185-.1136h-5.892353c-.44287 0-.801887-.3391-.801887-.7573v-9.52607c0-.31334.268969-.56734.60076-.56734.33179 0 .60076.254.60076.56734v9.12407c1.97837.0103 5.54739-.8252 6.80448.9069l.00108.0144c.30143.0971.74508.1168 1.08353.0031l-.00016-.0175c1.21405-1.6728 4.85795-.8967 6.80445-.9069v-9.16961c0-.28819.2474-.5218.5526-.5218z"}
                            {:fx/type svg-path
                             :hovered? hovered?
                             :content "m8.12852 1.20045v9.56095c-.83339-1.14838-3.40573-.92458-5.85789-.9372v-9.7515781c2.5238-.0532496 4.6626-.4074229 5.85789 1.1278281z"}
                            {:fx/type svg-path
                             :hovered? hovered?
                             :content "m8.9679 1.20045v9.56095c.83344-1.14838 3.4058-.92458 5.8579-.9372v-9.7515781c-2.5238-.0532496-4.6626-.4074229-5.8579 1.1278281z"}]}]}))

(defn book-icon [{:keys [book fx/context]}]
  (let [visible-books (fx/sub-ctx context queries/visible-books)]
    (if (contains? visible-books book)
      {:fx/type open-book-icon :book book}
      {:fx/type closed-book-icon :book book})))

(defn book-block [{:keys [book fx/context]}]
  (let [visible-books (fx/sub-ctx context queries/visible-books)]
    {:fx/type :v-box
     :style-class "notelist-book"
     :on-mouse-entered {:event/type ::events/set-hovered-book :data (:slug book)}
     :on-mouse-exited {:event/type ::events/set-hovered-book :data nil}
     :on-mouse-clicked (fn [_] (book-click-handler (:slug book) visible-books))
     :children [{:fx/type :h-box
                 :children [{:fx/type book-icon
                             :book (:slug book)}
                            {:fx/type :v-box
                             :children [{:fx/type :label
                                         :style-class "notelist-book-title"
                                         :text (:title book)}
                                        {:fx/type :label
                                         :style-class "notelist-book-subtitle"
                                         :text (inf/pluralize (:count book) "note")}]}]}]}))

(defn notes-list [{:keys [fx/context]}]
  (let [books (fx/sub-ctx context queries/notes-info)
        books-count (fx/sub-ctx context queries/books-count)
        notes-count (fx/sub-ctx context queries/notes-count)]
    {:fx/type :v-box
     :children [{:fx/type :v-box
                 :children [{:fx/type :label
                             :style-class "notes-count"
                             :text (str
                                    (inf/pluralize books-count "book") ", "
                                    (inf/pluralize notes-count "note"))}
                            {:fx/type :v-box
                             :children (mapv (fn [book] {:fx/type book-block :book book})
                                             books)}]}]}))