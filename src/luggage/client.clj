(ns luggage.client
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [com.dropbox.core
            DbxAppInfo
            DbxPKCEWebAuth
            DbxRequestConfig
            DbxWebAuth
            DbxAuthFinish
            TokenAccessType]
           [com.dropbox.core.oauth DbxCredential DbxRefreshResult]
           [com.dropbox.core.v2 DbxClientV2]
           [java.io File]))

(def app-key "yeo22moig39n8c0")
(def config-path "~/.notebox")
(def credentials-path (str config-path "/credentials"))

(defn expand-home [s]
  (if (.startsWith s "~")
    (str/replace-first s "~" (System/getProperty "user.home"))
    s))

(def request-config (DbxRequestConfig. "notebox" "en_US"))

(defn dropbox-client [access-token]
  (DbxClientV2. request-config access-token))

(def client (memoize dropbox-client))

(def app-info (DbxAppInfo. app-key))

(def pkce-web-auth (DbxPKCEWebAuth. request-config app-info))

(defn create-authorize-url []
  (let [web-auth-request
        ^DbxWebAuth.Request
        (-> (DbxWebAuth/newRequestBuilder)
            (.withNoRedirect)
            (.withTokenAccessType (TokenAccessType/OFFLINE))
            (.build))]
    (.authorize pkce-web-auth web-auth-request)))

(defn finish-authorize-flow [code]
  (.finishFromCode pkce-web-auth code))

(defn read-credential []
  (let [^File input (File. (expand-home credentials-path))]
    (. DbxCredential/Reader (readFromFile input))))

(defn write-credential [^DbxAuthFinish auth-finish]
  (let [^DbxCredential credential (DbxCredential. (.getAccessToken auth-finish)
                                                  (.getExpiresAt auth-finish)
                                                  (.getRefreshToken auth-finish)
                                                  (.getKey app-info)
                                                  (.getSecret app-info))
        ^File output (File. (expand-home credentials-path))]
    (io/make-parents output)
    (.createNewFile output)
    (. DbxCredential/Writer (writeToFile credential output))))

(defn refresh-credential []
  (let [^DbxCredential credential (read-credential)]
    (cond (.aboutToExpire credential)
          (let [^DbxRefreshResult refresh-result (.refresh credential request-config)
                ^DbxCredential new-credential (DbxCredential. (.getAccessToken refresh-result)
                                                              (.getExpiresAt refresh-result)
                                                              (.getRefreshToken credential)
                                                              (.getKey app-info)
                                                              (.getSecret app-info))
                ^File output (File. (expand-home credentials-path))]
            (io/make-parents output)
            (.createNewFile output)
            (. DbxCredential/Writer (writeToFile new-credential output))))))


;; Playground

(comment (println (create-authorize-url)))
(comment (def auth-finish (finish-authorize-flow "w7gUxoguV8wAAAAAAAAWJnxI6NI9LBo2txw8hcLhV1Q")))
(comment (.getAccessToken auth-finish))
(comment (write-credential auth-finish))
(comment (def credential (read-credential)))
(comment (.aboutToExpire credential))
(comment (.refresh credential request-config))
(comment (refresh-credential))
(comment (do
           (io/make-parents (File. (expand-home "~/.noteboxx/rofl.txt")))
           (spit (expand-home "~/.noteboxx/rofl.txt") "hey")))