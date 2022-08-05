(ns luggage.client
  (:import [com.dropbox.core
            DbxRequestConfig
            DbxAppInfo
            DbxWebAuth
            DbxPKCEWebAuth
            TokenAccessType]
           [com.dropbox.core.v2 DbxClientV2]))

(def app-key "2t7xyn3a902rv0z")

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

;; Playground
(comment (println (create-authorize-url)))
(comment (def access-code "w7gUxoguV8wAAAAAAAAWHn5VqgJLqh5wDoG4bGTf25Y"))
(comment (.finishFromCode pkce-web-auth access-code))

