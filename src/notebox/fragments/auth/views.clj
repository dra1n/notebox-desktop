(ns notebox.fragments.auth.views
  (:require [cljfx.api :as fx]
            [cljfx.lifecycle :as lifecycle]
            [cljfx.mutator :as mutator]
            [cljfx.prop :as prop]
            [notebox.app-db.events :as events]
            [luggage.client :as luggage])
  (:import [javafx.scene.web WebView]))

(def web-view-with-ext-props
  (fx/make-ext-with-props
   {:on-location-changed (prop/make (mutator/property-change-listener
                                     #(.locationProperty (.getEngine ^WebView %)))
                                    lifecycle/change-listener)}))

;; The web-pane function returns the extended web-view that has the additional property :on-location-changed installed.
(defn web-pane [_]
  (let [authorize-url (luggage/create-authorize-url)]
    {:fx/type web-view-with-ext-props
     :desc {:fx/type :web-view
            :pref-height 600
            :pref-width 800
            :url authorize-url}
     :props {:on-location-changed {:event/type ::events/check-auth}}}))