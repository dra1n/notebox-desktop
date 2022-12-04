(ns notebox.common.http-link.views
  (:import [java.awt Desktop]
           [java.net URI]))

(defn http-link [{:keys [url text style-class]}]
  {:fx/type :hyperlink
   :style-class style-class
   :text text
   :on-action (fn [_]
                (future
                  (try
                    (.browse (Desktop/getDesktop)
                             (URI. url))
                    (catch Exception e
                      (.printStackTrace e)))))})