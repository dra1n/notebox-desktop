(ns notebox.fragments.auth.views
  (:require [cljfx.api :as fx]
            [clojure.string :as str]
            [notebox.app-db.queries :as queries]
            [notebox.app-db.events :as events :refer [dispatch-event]])
  (:import [java.awt Desktop]
           [java.net URI]))

(defn auth-link [{:keys [fx/context]}]
  (let [authorize-url (fx/sub-ctx context queries/authorize-url)]
    {:fx/type :hyperlink
     :text "Sign in with Dropbox"
     :on-action (fn [_]
                  (future
                    (try
                      (.browse (Desktop/getDesktop)
                               (URI. authorize-url))
                      (catch Exception e
                        (.printStackTrace e)))))}))

(defn auth [_]
  {:fx/type :v-box
   :children [{:fx/type :h-box
               :children [{:fx/type :label :text "1. Go to "}
                          {:fx/type auth-link}]}
              {:fx/type :label :text "2. Click \"Allow\" (you might have to log in first)."}
              {:fx/type :label :text "3. Copy the authorization code."}
              {:fx/type :label :text "4. Enter the authorization code here: "}
              {:fx/type :text-field
               :on-text-changed #(dispatch-event
                                  {:event/type ::events/set-auth-code :data (str/trim %)})}
              {:fx/type :button
               :text "Start"
               :on-action {:event/type ::events/apply-auth-code}}]})