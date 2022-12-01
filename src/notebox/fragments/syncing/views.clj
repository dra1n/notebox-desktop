(ns notebox.fragments.syncing.views
  (:require [cljfx.api :as fx]
            [notebox.app-db.queries :as queries]
            [notebox.fragments.loader.views :refer [loader]]
            [notebox.common.transition.utils :as transition])
  (:import [javafx.scene Node]
           [javafx.animation TranslateTransition Interpolator]
           [javafx.util Duration]))

;; We can reuse our transition/start-transition-on utility here
;; But for the sake of having fx/ext-on-instance-lifecycle example
;; we have different implementation
(defn- animate-entrance [^Node node]
  (doto (TranslateTransition. (Duration/seconds 0.3) node)
    (.setFromX 155)
    (.setToX 0)
    (.setInterpolator Interpolator/EASE_OUT)
    (.play)))

(defn base-syncing [_]
  {:fx/type :h-box
   :max-width 135
   :max-height 30
   :style-class "syncing-block"
   :children [{:fx/type :v-box
               :style-class "syncing-spinner-block"
               :children [{:fx/type transition/start-transition-on
                           :transition {:fx/type :rotate-transition
                                        :duration [0.8 :s]
                                        :from-angle 0
                                        :to-angle 360
                                        :cycle-count :indefinite
                                        :auto-reverse false
                                        :interpolator :linear}
                           :desc {:fx/type loader
                                  :requested-width 12}}]}
              {:fx/type :label
               :text "data updating..."}]})

(defn animated-syncing [_]
  {:fx/type fx/ext-on-instance-lifecycle
   :on-created animate-entrance
   :desc {:fx/type base-syncing}})

(defn syncing [{:keys [fx/context]}]
  (let [syncing? (fx/sub-ctx context queries/syncing?)]
    (if syncing?
      {:fx/type animated-syncing}
      {:fx/type :v-box
       :max-width 0
       :max-height 0})))
