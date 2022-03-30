(ns notebox.app-db.effects
  (:require [cheshire.core :as json]
            [luggage.collections :as luggage]))

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
        (let [error (-> e (.getMessage) (json/parse-string))]
          (cond (access-token-error? error)
                (dispatch {:event/type dispatch-error
                           :error error})))))))

(defn fetch-books
  [{:keys [token callback error-callback books]} dispatch]
  (doseq [book books]
    (fetch-book {:token token
                 :callback callback
                 :error-callback error-callback
                 :book book}
                dispatch)))

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
