(ns luggage.collections
  (:require [clojure.java.io :as io]
            [luggage.client :refer [client]]
            [cheshire.core :as json])
  (:import [com.dropbox.core.v2.files WriteMode]))


;; Contstants and utils

(defn meta-file-name [collections-name]
  (str "/" collections-name "/.meta.json"))

(defn collection-file-name [collections-name name]
  (str "/" collections-name "/" name ".json"))


;; Public API

(defn read-meta [{:keys [access-token collections-name]}]
  (-> (client access-token)
      (.files)
      (.download (meta-file-name collections-name))
      (.getInputStream)
      (slurp)
      (json/parse-string true)))

(defn write-meta [{:keys [access-token collections-name]} value]
  (-> value
      (json/generate-string)
      (.getBytes)
      (io/input-stream)
      ((fn [data-stream]
         (-> (client access-token)
             (.files)
             (.uploadBuilder (meta-file-name collections-name))
             (.withMode WriteMode/OVERWRITE)
             (.uploadAndFinish data-stream))))))

(defn read-collection [{:keys [access-token collections-name]} name]
  (-> (client access-token)
      (.files)
      (.download (collection-file-name collections-name name))
      (.getInputStream)
      (slurp)
      (json/parse-string true)))

(defn write-collection [{:keys [access-token collections-name]} name value]
  (-> value
      (json/generate-string)
      (.getBytes)
      (io/input-stream)
      ((fn [data-stream]
         (-> (client access-token)
             (.files)
             (.uploadBuilder (collection-file-name collections-name name))
             (.withMode WriteMode/OVERWRITE)
             (.uploadAndFinish data-stream))))))

(defn add-value [{:keys [access-token collections-name]} name value]
  (let [updated-collection
        (-> {:access-token access-token :collections-name collections-name}
            (read-collection name)
            (conj value))]
    (-> {:access-token access-token :collections-name collections-name}
        (write-collection name updated-collection))))

(defn update-value [{:keys [access-token collections-name]} name search-fn value]
  (->> name
       (read-collection {:access-token access-token :collections-name collections-name})
       (map (fn [item] (if (search-fn item) value item)))
       (write-collection {:access-token access-token :collections-name collections-name}
                         name)))

(defn create-collection [{:keys [access-token collections-name]} name]
  (let [new-meta
        (-> {:access-token access-token :collections-name collections-name}
            (read-meta)
            (update-in [:collectionsList] conj name))]
    (write-meta
     {:access-token access-token :collections-name collections-name} new-meta)
    (write-collection
     {:access-token access-token :collections-name collections-name} name [])))