(ns notebox.fragments.sidemenu.views
  (:require [cljfx.api :as fx]
            [notebox.common.styles :as s]
            [notebox.app-db.events :as events]
            [notebox.app-db.queries :as queries]))

(defn dropbox-icon [_]
  {:fx/type :v-box
   :pref-width 12
   :alignment :top-center
   :style-class "notelist-closed-book-icon"
   :children [{:fx/type :svg-path
               :fill (::s/text-grey s/style)
               :scale-x 1
               :scale-y 1
               :content  "M5.81739 2.12229L2.9131 3.94022L5.81739 5.75816L2.9131 7.57609L0 5.7409L2.9109 3.92297L0 2.12229L2.9109 0.304352L5.81739 2.12229ZM2.9087 8.06087L5.81739 6.12174L8.72609 8.06087L5.81739 10L2.9087 8.06087ZM8.48774 3.92404L5.81739 5.74252L8.47358 7.57609L11.15 5.75762L8.47358 3.9413L11.15 2.12283L8.47358 0.304352L5.81739 2.12067L8.48774 3.92404Z"}]})

(defn account-name [{:keys [name]}]
  {:fx/type :v-box
   :children [{:fx/type :label
               :style-class "account-name-label"
               :text name}]})

(defn account-email [{:keys [email]}]
  {:fx/type :h-box
   :style-class "account-email"
   :children [{:fx/type dropbox-icon}
              {:fx/type :v-box
               :style-class "account-email-text"
               :children [{:fx/type :label
                           :style-class "account-email-label"
                           :text email}]}]})

(defn account-info-view [{:keys [fx/context]}]
  (let [account-info (fx/sub-ctx context queries/account-info)]
    {:fx/type :v-box
     :style-class "account-info"
     :children [{:fx/type account-name
                 :name (:name account-info)}
                {:fx/type account-email
                 :email (:email account-info)}]}))

(defn sidemenu [{:keys [fx/context]}]
  (let [syncing? (fx/sub-ctx context queries/syncing?)]
    {:fx/type :v-box
     :children [{:fx/type account-info-view}
                {:fx/type :label
                 :text "side menu"}
                {:fx/type :button
                 :text (str "refresh" (if syncing? " [syncing]" ""))
                 :on-action {:event/type ::events/fetch-notes-info}}]}))