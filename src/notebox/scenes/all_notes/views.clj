(ns notebox.scenes.all-notes.views
  (:require [cljfx.api :as fx]
            [cljfx.css :as css]
            [notebox.app-db.queries :as queries]
            [notebox.fragments.sidemenu.views :refer [sidemenu]]
            [notebox.fragments.notes-list.views :refer [notes-list]]
            [notebox.fragments.note.views :refer [note]]
            [notebox.common.styles :as s]))

(defn subscene-view [{:keys [fx/context]}]
  (let [subscene (fx/sub-ctx context queries/subscene)
        subscene-data (fx/sub-ctx context queries/subscene-data)]
    (cond (= subscene :show-note) {:fx/type note
                                   :note (:note subscene-data)
                                   :book (:book subscene-data)}
          :else {:fx/type :label
                 :text "Empty note"})))

(defn all-notes [{:keys [fx/context]}]
  (let [styles (fx/sub-ctx context queries/styles)]
    {:fx/type :scene
     :stylesheets ["styles.css"
                   (::css/url (:common styles))
                   (::css/url (:all-notes styles))
                   (::css/url (:note-list styles))
                   (::css/url (:note styles))]
     :root {:fx/type :h-box
            :children [{:fx/type :v-box
                        :style-class "sidemenu"
                        :pref-width (::s/menu-width s/style)
                        :children [{:fx/type sidemenu}]}
                       {:fx/type :v-box
                        :style-class "notelist"
                        :pref-width (::s/notelist-width s/style)
                        :children [{:fx/type notes-list}]}
                       {:fx/type :v-box
                        :style-class "note"
                        :h-box/hgrow :always
                        :children [{:fx/type subscene-view}]}]}}))