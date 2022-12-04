(ns notebox.scenes.all-notes.views
  (:require  [clojure.java.io :as io]
             [cljfx.api :as fx]
             [cljfx.css :as css]
             [notebox.app-db.queries :as queries]
             [notebox.fragments.syncing.views :refer [syncing]]
             [notebox.fragments.sidemenu.views :refer [sidemenu]]
             [notebox.fragments.notes-list.views :refer [notes-list]]
             [notebox.fragments.note.views :refer [note]]
             [notebox.common.http-link.views :refer [http-link]]
             [notebox.common.styles :as s]))

(defn empty-note-view [_]
  {:fx/type :v-box
   :alignment :center
   :children [{:fx/type :h-box
               :alignment :center
               :pref-width 440
               :children [{:fx/type :image-view
                           :image {:url (.toString (io/resource "images/empty-note.svg"))}}
                          {:fx/type :v-box
                           :style-class "empty-note-description"
                           :children [{:fx/type :label
                                       :style-class "empty-note-title"
                                       :text "Select a note"}
                                      {:fx/type :flow-pane
                                       :max-width 300
                                       :children [{:fx/type :label
                                                   :style-class "empty-note-disclaimer"
                                                   :wrap-text true
                                                   :text "Choose a note to display it or just add"}
                                                  {:fx/type http-link
                                                   :style-class "empty-note-add-link"
                                                   :text "new one"
                                                   :url "https://notebox.in/app"}
                                                  {:fx/type :label
                                                   :style-class "empty-note-disclaimer"
                                                   :wrap-text true
                                                   :text "if you don't have any yet."}]}]}]}]})

(defn subscene-view [{:keys [fx/context]}]
  (let [subscene (fx/sub-ctx context queries/subscene)
        subscene-data (fx/sub-ctx context queries/subscene-data)]
    (cond (= subscene :show-note) {:fx/type note
                                   :note (:note subscene-data)
                                   :book (:book subscene-data)}
          :else {:fx/type empty-note-view})))

(defn all-notes [{:keys [fx/context]}]
  (let [styles (fx/sub-ctx context queries/styles)
        sidemenu-collapsed? (fx/sub-ctx context queries/sidemenu-collapsed?)]
    {:fx/type :scene
     :stylesheets ["styles.css"
                   (::css/url (:common styles))
                   (::css/url (:all-notes styles))
                   (::css/url (:note-list styles))
                   (::css/url (:syncing styles))
                   (::css/url (:sidemenu styles))
                   (::css/url (:note styles))]
     :root {:fx/type :stack-pane
            :children [{:fx/type :h-box
                        :children [{:fx/type :v-box
                                    :style-class "sidemenu"
                                    :pref-width (if sidemenu-collapsed? 40 (::s/menu-width s/style))
                                    :children [{:fx/type sidemenu
                                                :v-box/vgrow :always}]}
                                   {:fx/type :v-box
                                    :style-class "notelist"
                                    :pref-width (::s/notelist-width s/style)
                                    :children [{:fx/type notes-list}]}
                                   {:fx/type :v-box
                                    :style-class "note"
                                    :h-box/hgrow :always
                                    :children [{:fx/type subscene-view
                                                :v-box/vgrow :always}]}]}
                       {:fx/type syncing
                        :stack-pane/margin 20
                        :stack-pane/alignment :top-right}]}}))