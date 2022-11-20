(ns notebox.app-db.effects
  (:require [clojure.string :as string]
            [cheshire.core :as json]
            [luggage.client :as client]
            [luggage.collections :as luggage]))

(def collections-name "notes")


;; Utils

(defn dispatch-n
  [events dispatch]
  (doseq [event events] (dispatch event)))


;; Auth Flow

(defn start-auth-flow
  [{:keys [auth-finished auth-login-required]} dispatch]
  (future
    (try (client/refresh-credential)
         (let [credential (client/read-credential)]
           (dispatch {:event/type auth-finished
                      :access-token (.getAccessToken credential)}))
         (catch Exception e
           (println e)
           (dispatch {:event/type auth-login-required
                      :authorize-url (client/create-authorize-url)})))))

(defn apply-auth-code
  [{:keys [auth-code auth-finished]} dispatch]
  (future
    (try (-> auth-code
             (client/finish-authorize-flow)
             (client/write-credential))
         (let [credential (client/read-credential)]
           (dispatch {:event/type auth-finished
                      :access-token (.getAccessToken credential)}))
         (catch Exception e
           (println e)))))


;; Notes Utils

(defn expired-access-token? [error]
  (= (get-in error ["error" ".tag"]) "expired_access_token"))

(defn invalid-access-token? [error]
  (= (get-in error ["error" ".tag"]) "invalid_access_token"))

(defn access-token-error? [error]
  (or (expired-access-token? error)
      (invalid-access-token? error)))

(defn normalize-note [note]
  (-> note
      (dissoc :book-title)
      (dissoc :book-slug)))

(defn matches-text [text]
  (fn [note]
    (or (empty? text)
        (let [filter-text (-> text (string/trim) (string/lower-case))]
          (or (and (:title note)
                   (string/includes? (string/lower-case (:title note))
                                     filter-text))
              (and (:tags note)
                   (string/includes? (string/lower-case (string/join " " (:tags note)))
                                     filter-text))
              (and (:text note)
                   (string/includes? (string/lower-case (:text note))
                                     filter-text)))))))

(defn perform-search [notes value]
  (filterv (matches-text value) notes))


;; Notes Search

(defn search-in-book
  [{:keys [notes value book dispatch-success]} dispatch]
  (let [search-results (perform-search notes value)]
    (dispatch {:event/type dispatch-success
               :book book
               :data search-results})))


;; Notes Effects

(defn fetch-notes-info
  [{:keys [token dispatch-success dispatch-error]} dispatch]
  (future
    (try
      (-> {:access-token token
           :collections-name collections-name}
          (luggage/read-meta)
          (:notesInfo)
          ((fn [data] (dispatch {:event/type dispatch-success
                                 :data data}))))
      (catch Exception e
        (let [error (-> e (.getMessage) (json/parse-string))]
          (cond (access-token-error? error)
                (dispatch {:event/type dispatch-error
                           :error error})))))))

(defn fetch-tags-info
  [{:keys [token dispatch-success dispatch-error]} dispatch]
  (future
    (try
      (-> {:access-token token
           :collections-name collections-name}
          (luggage/read-meta)
          (:tagsInfo)
          ((fn [data] (dispatch {:event/type dispatch-success
                                 :data data}))))
      (catch Exception e
        (let [error (-> e (.getMessage) (json/parse-string))]
          (cond (access-token-error? error)
                (dispatch {:event/type dispatch-error
                           :error error})))))))

(defn fetch-book
  [{:keys [token dispatch-success dispatch-error book]} dispatch]
  (future
    (try
      (-> {:access-token token
           :collections-name collections-name}
          (luggage/read-collection book)
          ((fn [data] (dispatch {:event/type dispatch-success
                                 :book book
                                 :data data}))))
      (catch Exception e
        ;; Exception message isn't always parsable
        ;; For example download exception will look something like this
        ;;   Exception in 2/files/download: {".tag":"path","path":"not_found"}
        ;; In this case it seems that future is not resolved and we 
        ;; silently swallow the exception. If this happens then try to
        ;; log the messsage before parsing
        (let [error (-> e (.getMessage) (json/parse-string))]
          (cond (access-token-error? error)
                (dispatch {:event/type dispatch-error
                           :error error})
                :else (println (.getMessage e))))))))

(defn fetch-books
  [{:keys [token dispatch-success dispatch-error books]} dispatch]
  (future
    (doseq [book books]
      (fetch-book {:token token
                   :dispatch-success dispatch-success
                   :dispatch-error dispatch-error
                   :book book}
                  dispatch))))

(defn update-note
  [{:keys [token callback error-callback book note]}]
  (try
    (-> {:access-token token :collections-name collections-name}
        (luggage/update-value book
                              (fn [item] (= (:slug item) (:slug note)))
                              note)
        (callback))
    (catch Exception e
      (let [error (-> e (.getMessage) (json/parse-string))]
        (cond (access-token-error? error)
              (error-callback error))))))

(defn add-note
  [{:keys [token callback error-callback book note]}]
  (try
    (-> {:access-token token :collections-name collections-name}
        (luggage/add-value book note)
        (callback))
    (catch Exception e
      (let [error (-> e (.getMessage) (json/parse-string))]
        (cond (access-token-error? error)
              (error-callback error))))))

;; (comment defn add-note-new-book
;;          [{:keys [token callback error-callback book old-book note]}]
;;          (let [book-slug (nano-id 10)
;;                book-title (:book-title note)
;;                note (normalize-note note)]
;;            (try
;;              (-> token
;;                  (luggage/add-note book note)
;;                  (callback))
;;              (catch Exception e
;;                (let [error (-> e (.getMessage) (json/parse-string))]
;;                  (cond (access-token-error? error)
;;                        (error-callback error)))))))
