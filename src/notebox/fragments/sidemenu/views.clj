(ns notebox.fragments.sidemenu.views
  (:require [cljfx.api :as fx]
            [clojure.string :as str]
            [notebox.common.styles :as s]
            [notebox.app-db.events :as events]
            [notebox.app-db.queries :as queries]))

;; Utils

(defn abbriviate-name [acc]
  (let [name-parts (-> acc :name (str/split #"\s"))
        name (->> name-parts
                  (map first)
                  (take 2)
                  (str/join ""))]
    (if (empty? name) "--" name)))


;; Views

(defn shevron-left-icon [_]
  {:fx/type :v-box
   :pref-width 20
   :alignment :top-center
   :children [{:fx/type :svg-path
               :fill (::s/text-grey-light s/style)
               :scale-x 1
               :scale-y 1
               :content  "M7.721 2.22a.75.75 0 0 1 1.061 1.06L4.061 8.002l4.721 4.721a.75.75 0 0 1-1.06 1.061L2.47 8.532a.75.75 0 0 1 0-1.06L7.722 2.22zm5 0a.75.75 0 0 1 1.061 1.06L9.061 8.002l4.721 4.721a.75.75 0 0 1-1.06 1.061L7.47 8.532a.75.75 0 0 1 0-1.06l5.252-5.252z"}]})

(defn shevron-right-icon [_]
  {:fx/type :v-box
   :pref-width 20
   :alignment :top-center
   :children [{:fx/type :svg-path
               :fill (::s/text-grey-light s/style)
               :scale-x 1
               :scale-y 1
               :content  "M3.53 2.22a.75.75 0 0 0-1.06 1.06l4.72 4.722-4.72 4.721a.75.75 0 0 0 1.06 1.061l5.252-5.252a.75.75 0 0 0 0-1.06L3.53 2.22zm5 0a.75.75 0 0 0-1.06 1.06l4.721 4.722-4.721 4.721a.75.75 0 0 0 1.06 1.061l5.252-5.252a.75.75 0 0 0 0-1.06L8.53 2.22z"}]})

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

(defn account-info-view-abbr [{:keys [fx/context]}]
  (let [account-info (fx/sub-ctx context queries/account-info)]
    {:fx/type :v-box
     :style-class "account-info-abbr"
     :children [{:fx/type account-name
                 :name (abbriviate-name account-info)}]}))

(defn sidemenu-collapse-button [{:keys [fx/context]}]
  (let [sidemenu-collapsed? (fx/sub-ctx context queries/sidemenu-collapsed?)]
    {:fx/type :button
     :pref-width (::s/menu-width s/style)
     :style-class "sidemenu-toggle-button"
     :graphic {:fx/type (if sidemenu-collapsed?
                          shevron-right-icon
                          shevron-left-icon)}
     :text (if sidemenu-collapsed?
             " "
             "Collapse sidebar")
     :on-action {:event/type ::events/toggle-sidemenu-collapsed}}))

(defn sidemenu [{:keys [fx/context]}]
  (let [sidemenu-collapsed? (fx/sub-ctx context queries/sidemenu-collapsed?)]
    {:fx/type :v-box
     :style-class "sidemenu-wrapper"
     :children [{:fx/type (if sidemenu-collapsed?
                            account-info-view-abbr
                            account-info-view)}
                {:fx/type :v-box
                 :v-box/vgrow :always
                 :children []}
                {:fx/type :v-box
                 :children [{:fx/type sidemenu-collapse-button}]}]}))