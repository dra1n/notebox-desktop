(ns notebox.fragments.auth.views
  (:require [cljfx.api :as fx]
            [clojure.string :as str]
            [notebox.common.http-link.views :refer [http-link]]
            [notebox.app-db.queries :as queries]
            [notebox.app-db.events :as events :refer [dispatch-event]]))

(defn auth-link [{:keys [fx/context]}]
  (let [authorize-url (fx/sub-ctx context queries/authorize-url)]
    {:fx/type http-link
     :style-class "auth-link"
     :text "Sign in with Dropbox"
     :url authorize-url}))

(defn auth [_]
  {:fx/type :v-box
   :style-class "auth-container"
   :children [{:fx/type :label
               :style-class "auth-title"
               :text "Login in 4 steps"}
              {:fx/type :v-box
               :style-class "auth-step"
               :children [{:fx/type :h-box
                           :children [{:fx/type :label
                                       :style-class "auth-label"
                                       :text "1. Go to "}
                                      {:fx/type auth-link}]}
                          {:fx/type :label
                           :style-class "auth-label-helper"
                           :text "Click to open the link in your browser"}]}
              {:fx/type :v-box
               :style-class "auth-step"
               :children [{:fx/type :label
                           :style-class "auth-label"
                           :text "2. Click \"Allow\""}
                          {:fx/type :label
                           :style-class "auth-label-helper"
                           :text "You might have to log in first"}]}
              {:fx/type :v-box
               :style-class "auth-step"
               :children [{:fx/type :label
                           :style-class "auth-label"
                           :text "3. Copy the authorization code."}]}
              {:fx/type :v-box
               :style-class "auth-step"
               :children [{:fx/type :label
                           :style-class "auth-label"
                           :text "4. Enter the authorization code here: "}]}
              {:fx/type :v-box
               :style-class "auth-step"
               :children [{:fx/type :text-field
                           :pref-width 320
                           :max-width 320
                           :on-text-changed #(dispatch-event
                                              {:event/type ::events/set-auth-code :data (str/trim %)})}]}
              {:fx/type :v-box
               :style-class "auth-start-button"
               :alignment :center
               :children [{:fx/type :button
                           :style-class "button-primary"
                           :text "START"
                           :on-action {:event/type ::events/apply-auth-code}}]}]})