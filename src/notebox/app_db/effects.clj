(ns notebox.app-db.effects
  (:require [cheshire.core :as json]
            [luggage.collections :as luggage]))

(def access-token "sl.BDWMcP8l08_1kuAzmAq0Zsf_XoIgnbFkjVOau1kxBnuucDygfoMcWnSgUxSla12h1ViL_w_x3YAv-L-MnP8P7ZHqKs0JTz6Crl_-gBv8ZhGk_BgtspQAep5HfZeHbU4HNYQlW2-RmT1p")
(def collections-name "notes")


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
  [{:keys [token callback error-callback]}]
  (try
    (-> {:access-token token
         :collections-name collections-name}
        (luggage/read-meta)
        (:tagsInfo)
        (callback))
    (catch Exception e
      (let [error (-> e (.getMessage) (json/parse-string))]
        (cond (access-token-error? error)
              (error-callback error))))))

(defn fetch-book
  [{:keys [token callback error-callback book]}]
  (try
    (-> {:access-token token
         :collections-name collections-name}
        (luggage/read-collection book)
        (callback))
    (catch Exception e
      (let [error (-> e (.getMessage) (json/parse-string))]
        (cond (access-token-error? error)
              (error-callback error))))))

(defn fetch-books
  [{:keys [token callback error-callback books]}]
  (doseq [book books]
    (fetch-book {:token token
                 :callback callback
                 :error-callback error-callback
                 :book book})))

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

;; Repl stuff

(comment (fetch-tags-info {:token access-token
                           :error-callback println
                           :callback println}))

(comment (fetch-book {:token access-token
                      :error-callback println
                      :book "tolstann"
                      :callback println}))

(comment (fetch-books {:token access-token
                       :error-callback println
                       :books ["todo"]
                       :callback println}))

(comment (update-note {:token access-token
                       :error-callback println
                       :book "tolstann"
                       :note {:slug "a3" :title "rofl!!!"}
                       :callback println}))

(comment (add-note {:token access-token
                    :error-callback println
                    :book "tolstann"
                    :note {:slug "a8" :title "new note"}
                    :callback println}))

(comment (luggage/create-collection
          {:access-token access-token :collections-name collections-name}
          "nada3"))