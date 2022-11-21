(ns notebox.fragments.notes-list.views
  (:require [cljfx.api :as fx]
            [clojure.string :as str]
            [inflections.core :as inf]
            [notebox.common.utils :as utils]
            [notebox.app-db.events :as events :refer [dispatch-event]]
            [notebox.app-db.queries :as queries]
            [notebox.common.svg-path.views :refer [svg-path]]))

(defn add-class-if [name cond]
  (if cond name ""))


;; Handlers

(defn book-click-handler [{:keys [book notes visible-books]}]
  (if (contains? visible-books book)
    (dispatch-event {:event/type ::events/remove-visible-book :data book})
    (do (dispatch-event {:event/type ::events/set-visible-book :data book})
        (dispatch-event {:event/type ::events/set-last-active-book :data book})
        (cond (not (contains? notes book))
              (dispatch-event {:event/type ::events/fetch-book :data book})))))

(defn note-click-handler [{:keys [note book]}]
  (dispatch-event {:event/type ::events/set-subscene
                   :name :show-note
                   :data {:note note :book book}})
  (dispatch-event {:event/type ::events/set-last-active-note :data (:slug note)}))

(defn search-handler [value]
  (if (str/blank? value)
    (dispatch-event {:event/type ::events/finish-search})
    (do
      (dispatch-event {:event/type ::events/finish-search})
      (dispatch-event {:event/type ::events/start-search :data value}))))

(def debounced-search-handler (utils/debounce search-handler 500))


;; Components

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

(defn single-note [{:keys [book note active-note]}]
  {:fx/type :v-box
   :style-class ["notelist-note"
                 (add-class-if "notelist-note-active" (= (:slug note) active-note))]
   :on-mouse-clicked (fn [_]
                       (note-click-handler {:note note :book book}))
   :children [{:fx/type :label
               :style-class "notelist-note-title"
               :text  (if (str/blank? (:title note))
                        "No title"
                        (:title note))}
              {:fx/type :label
               :style-class "notelist-note-text"
               :text  (if (str/blank? (:text note))
                        "No additional text"
                        (:text note))}]})

(defn book-notes [{:keys [book notes active-note]}]
  {:fx/type :v-box
   :style-class "notelist-notes"
   :children (mapv (fn [note] {:fx/type single-note
                               :note note
                               :book book
                               :active-note active-note})
                   notes)})


(defn search-notes [{:keys [notes books active-note]}]
  {:fx/type :v-box
   :style-class "notelist-notes"
   :children (mapv (fn [note]
                     (let [book (->> books
                                     (filter (comp #{(:book note)} :slug))
                                     (first))]
                       {:fx/type single-note
                        :note note
                        :book book
                        :active-note active-note}))
                   notes)})

(defn book-block [{:keys [book fx/context]}]
  (let [visible-books (fx/sub-ctx context queries/visible-books)
        active-note (fx/sub-ctx context queries/last-active-note)
        notes (fx/sub-ctx context queries/notes)]
    {:fx/type :v-box
     :children [{:fx/type :v-box
                 :style-class "notelist-book"
                 :on-mouse-entered {:event/type ::events/set-hovered-book :data (:slug book)}
                 :on-mouse-exited {:event/type ::events/set-hovered-book :data nil}
                 :on-mouse-clicked (fn [_]
                                     (book-click-handler {:book (:slug book)
                                                          :visible-books visible-books
                                                          :notes notes}))
                 :children [{:fx/type :h-box
                             :children [{:fx/type book-icon
                                         :book (:slug book)}
                                        {:fx/type :v-box
                                         :children [{:fx/type :label
                                                     :style-class "notelist-book-title"
                                                     :text (:title book)}
                                                    {:fx/type :label
                                                     :style-class "notelist-book-subtitle"
                                                     :text (inf/pluralize (:count book) "note")}]}]}]}
                (if (and (seq (get notes (:slug book)))
                         (contains? visible-books (:slug book)))
                  {:fx/type book-notes
                   :active-note active-note
                   :book book
                   :notes (get notes (:slug book))}
                  {:fx/type :v-box :children []})]}))

(defn books-block [{:keys [fx/context]}]
  (let [books (fx/sub-ctx context queries/notes-info)]
    {:fx/type :scroll-pane
     :style-class "scroll-pane"
     :fit-to-width true
     :fit-to-height true
     :content {:fx/type :v-box
               :children (mapv (fn [book] {:fx/type book-block :book book})
                               books)}}))

(defn search-results-block [{:keys [fx/context]}]
  (let [notes (fx/sub-ctx context queries/search-results)
        books (fx/sub-ctx context queries/notes-info)
        active-note (fx/sub-ctx context queries/last-active-note)]
    {:fx/type search-notes
     :active-note active-note
     :notes notes
     :books books}))

(defn search-reset-button [{:keys [fx/context]}]
  (let [search-started? (fx/sub-ctx context queries/search-started?)]
    (if search-started?
      {:fx/type :button
       :text "clear"
       :on-action (fn [_]
                    (dispatch-event {:event/type ::events/finish-search})
                    (dispatch-event {:event/type ::events/set-search-input-value
                                     :data ""}))}
      {:fx/type :v-box :children nil})))

(defn books-or-search-results [{:keys [fx/context]}]
  (let [search-started? (fx/sub-ctx context queries/search-started?)]
    (if search-started?
      {:fx/type search-results-block}
      {:fx/type books-block})))

(defn search-field [{:keys [:fx/context search-handler]}]
  (let [search-input-value (fx/sub-ctx context queries/search-input-value)]
    {:fx/type :text-field
     :prompt-text "Search"
     :text search-input-value
     :on-text-changed (fn [value]
                        (search-handler value)
                        (dispatch-event {:event/type ::events/set-search-input-value
                                         :data value}))}))

(defn search-action [_]
  {:fx/type :v-box
   :style-class "notes-search"
   :children [{:fx/type :h-box
               :children [{:fx/type search-field
                           :h-box/hgrow :always
                           :search-handler debounced-search-handler}
                          {:fx/type :v-box
                           :style-class "notes-search-button"
                           :children [{:fx/type search-reset-button}]}]}]})

(defn notes-count [{:keys [:fx/context]}]
  (let [books-count (fx/sub-ctx context queries/books-count)
        notes-count (fx/sub-ctx context queries/notes-count)]
    {:fx/type :label
     :style-class "notes-count"
     :text (str
            (inf/pluralize books-count "book") ", "
            (inf/pluralize notes-count "note"))}))

(defn notes-search-results-count [{:keys [:fx/context]}]
  (let [search-results-count (fx/sub-ctx context queries/search-results-count)]
    {:fx/type :label
     :style-class "notes-count"
     :text
     (inf/pluralize search-results-count "search result")}))

(defn notes-count-info [{:keys [:fx/context]}]
  (let [search-started? (fx/sub-ctx context queries/search-started?)]
    (if search-started?
      {:fx/type notes-search-results-count}
      {:fx/type notes-count})))

(defn notes-list [_]
  {:fx/type :v-box
   :children [{:fx/type :v-box
               :children [{:fx/type search-action}
                          {:fx/type notes-count-info}
                          {:fx/type books-or-search-results}]}]})