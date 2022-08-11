(ns notebox.scenes.login.views
  (:require [cljfx.api :as fx]
            [cljfx.css :as css]
            [notebox.app-db.queries :as queries]
            [notebox.fragments.logo.views :refer [logo]]
            [notebox.fragments.auth.views :refer [auth]]))

(defn login [{:keys [fx/context]}]
  (let [styles (fx/sub-ctx context queries/styles)]
    {:fx/type :scene
     :stylesheets ["styles.css"
                   (::css/url (:common styles))
                   (::css/url (:logo styles))
                   (::css/url (:auth styles))
                   (::css/url (:login styles))]
     :root {:fx/type :v-box
            :children [{:fx/type :v-box
                        :v-box/vgrow :always
                        :style-class "login"
                        :children [{:fx/type :v-box
                                    :children [{:fx/type :v-box
                                                :style-class "login-logo"
                                                :children [{:fx/type logo}]}
                                               {:fx/type :v-box
                                                :alignment :center
                                                :children [{:fx/type :v-box
                                                            :effect {:fx/type :drop-shadow
                                                                     :color "rgba(0, 0, 0, 0.2)"
                                                                     :radius 4
                                                                     :offset-y 2}
                                                            :max-width 420
                                                            :children [{:fx/type auth}]}]}]}]}]}}))