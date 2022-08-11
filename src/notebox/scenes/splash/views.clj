(ns notebox.scenes.splash.views
  (:require [cljfx.api :as fx]
            [cljfx.css :as css]
            [notebox.common.transition.utils :as transition]
            [notebox.app-db.queries :as queries]
            [notebox.fragments.logo.views :refer [logo]]
            [notebox.fragments.loader.views :refer [loader]]))


(defn splash [{:keys [fx/context]}]
  (let [styles (fx/sub-ctx context queries/styles)]
    {:fx/type :scene
     :stylesheets ["styles.css"
                   (::css/url (:common styles))
                   (::css/url (:splash styles))
                   (::css/url (:logo styles))]
     :root {:fx/type :v-box
            :children [{:fx/type :v-box
                        :v-box/vgrow :always
                        :style-class "splash"
                        :children [{:fx/type :v-box
                                    :style-class "splash-logo"
                                    :children [{:fx/type logo}]}
                                   {:fx/type transition/start-transition-on
                                    :transition {:fx/type :rotate-transition
                                                 :duration [0.8 :s]
                                                 :from-angle 0
                                                 :to-angle 360
                                                 :cycle-count :indefinite
                                                 :auto-reverse false
                                                 :interpolator :linear}
                                    :desc {:fx/type loader}}]}]}}))