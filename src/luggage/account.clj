(ns luggage.account
  (:require
   [luggage.client :refer [client]]))

(defn dropbox-account [access-token]
  (-> (client access-token)
      (.users)
      (.getCurrentAccount)))

(def account (memoize dropbox-account))

(defn account-name [access-token]
  (-> access-token
      (account)
      (.getName)
      (.getDisplayName)))

(defn account-email [access-token]
  (-> access-token
      (account)
      (.getEmail)))

(defn account-info [access-token]
  {:name (account-name access-token)
   :email (account-email access-token)})


;; Playground

(comment (def token ""))
(comment (account token))
(comment (account-name token))
(comment (account-email token))