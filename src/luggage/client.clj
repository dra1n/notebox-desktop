(ns luggage.client
  (:import [com.dropbox.core DbxRequestConfig]
           [com.dropbox.core.v2 DbxClientV2]))

(def config (DbxRequestConfig. "notebox" "en_US"))

(defn dropbox-client [access-token]
  (DbxClientV2. config access-token))

(def client (memoize dropbox-client))
