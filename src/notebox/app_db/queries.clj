(ns notebox.app-db.queries
  (:require [cljfx.api :as fx]))


;; Notes

(def notes-db-key :notebox.notes)


(defn syncing? [context]
  (let [sync-values (fx/sub-val context get-in [notes-db-key :syncing])]
    (some true? (vals sync-values))))

(defn assoc-syncing [context source value]
  (fx/swap-context context assoc-in [notes-db-key :syncing source] value))


(defn hovered-book [context]
  (fx/sub-val context get-in [notes-db-key :hovered-book]))

(defn assoc-hovered-book [context value]
  (fx/swap-context context assoc-in [notes-db-key :hovered-book] value))

(defn search-input-value [context]
  (fx/sub-val context get-in [notes-db-key :search-input-value]))

(defn assoc-search-input-value [context value]
  (fx/swap-context context assoc-in [notes-db-key :search-input-value] value))


(defn visible-books [context]
  (fx/sub-val context get-in [notes-db-key :visible-books]))

(defn assoc-visible-book [context value]
  (fx/swap-context
   context
   update-in
   [notes-db-key :visible-books]
   (fnil conj #{})
   value))

(defn remove-visible-book [context value]
  (fx/swap-context context update-in [notes-db-key :visible-books] disj value))

(defn assoc-last-active-book [context value]
  (fx/swap-context context assoc-in [notes-db-key :last-active-book] value))


(defn notes-info [context]
  (fx/sub-val context get-in [notes-db-key :notes-info]))

(defn assoc-notes-info [context value]
  (fx/swap-context context assoc-in [notes-db-key :notes-info] value))


(defn book [context book]
  (fx/sub-val context get-in [notes-db-key :notes book]))

(defn assoc-book [context book value]
  (fx/swap-context context assoc-in [notes-db-key :notes book] value))

(defn reset-books [context]
  (fx/swap-context context assoc-in [notes-db-key :notes] nil))

(defn notes [context]
  (fx/sub-val context get-in [notes-db-key :notes]))


(defn books-count [context]
  (-> (fx/sub-ctx context notes-info)
      (count)))

(defn notes-count [context]
  (->> (fx/sub-ctx context notes-info)
       (map :count)
       (apply +)))


(defn last-active-note [context]
  (fx/sub-val context get-in [notes-db-key :last-active-note]))

(defn assoc-last-active-note [context value]
  (fx/swap-context context assoc-in [notes-db-key :last-active-note] value))

(defn reset-notes [context]
  (fx/swap-context context assoc notes-db-key nil))


;; Search

(defn search-value [context]
  (fx/sub-val context get-in [notes-db-key :search-value]))

(defn assoc-search-started [context value]
  (-> context
      (fx/swap-context assoc-in [notes-db-key :search-started?] true)
      (fx/swap-context assoc-in [notes-db-key :search-value] value)))

(defn assoc-search-finished [context]
  (-> context
      (fx/swap-context assoc-in [notes-db-key :search-started?] false)
      (fx/swap-context update-in [notes-db-key] dissoc :search-value)
      (fx/swap-context update-in [notes-db-key] dissoc :search-results)))

(defn assoc-search-results [context book value]
  (let [search-results (map #(merge {:book book} %) value)]
    (-> context
        (fx/swap-context update-in [notes-db-key :search-results] concat search-results)
        (fx/swap-context update-in [notes-db-key :search-results] distinct))))

(defn search-results [context]
  (fx/sub-val context get-in [notes-db-key :search-results]))

(defn search-results-count [context]
  (-> context
      (fx/sub-ctx search-results)
      (count)))

(defn search-started? [context]
  (fx/sub-val context get-in [notes-db-key :search-started?]))


;; Account

(def account-db-key :notebox.account)

(defn account-info [context]
  (fx/sub-val context get-in [account-db-key :account-info]))

(defn assoc-account-info [context value]
  (fx/swap-context context assoc-in [account-db-key :account-info] value))

(defn reset-account [context]
  (fx/swap-context context assoc account-db-key nil))


;; Subscenes

(defn subscene [context]
  (fx/sub-val context get-in [notes-db-key :subscene]))

(defn subscene-data [context]
  (fx/sub-val context get-in [notes-db-key :subscene-data]))

(defn assoc-subscene [context name data]
  (-> context
      (fx/swap-context assoc-in [notes-db-key :subscene] name)
      (fx/swap-context assoc-in [notes-db-key :subscene-data] data)))


;; Scences

(def scene-db-key :notebox.scene)


(defn scene [context]
  (fx/sub-val context get-in [scene-db-key :current]))

(defn assoc-scene [context value]
  (fx/swap-context context assoc-in [scene-db-key :current] value))


;; Styles

(def styles-db-key :notebox.styles)


(defn styles [context]
  (fx/sub-val context styles-db-key))

(defn assoc-styles [context value]
  (fx/swap-context context assoc styles-db-key value))


;; UI

(def ui-db-key :notebox.ui)


(defn sidemenu-collapsed? [context]
  (fx/sub-val context get-in [ui-db-key :sidemenu-collapsed?]))

(defn assoc-sidemenu-collapsed [context value]
  (fx/swap-context context assoc-in [ui-db-key :sidemenu-collapsed?] value))

(defn show-confirmation? [context confirmation-id]
  (fx/sub-val context get-in [ui-db-key :confirmation confirmation-id :showing?] false))

(defn assoc-show-confirmation [context confirmation-id value]
  (fx/swap-context context assoc-in [ui-db-key :confirmation confirmation-id :showing?] value))


;; Auth

(def auth-db-key :notebox.auth)

(defn auth-code [context]
  (fx/sub-val context get-in [auth-db-key :code]))

(defn assoc-auth-code [context value]
  (fx/swap-context context assoc-in [auth-db-key :code] value))

(defn access-token [context]
  (fx/sub-val context get-in [auth-db-key :access-token]))

(defn assoc-access-token [context value]
  (fx/swap-context context assoc-in [auth-db-key :access-token] value))

(defn authorize-url [context]
  (fx/sub-val context get-in [auth-db-key :authorize-url]))

(defn assoc-authorize-url [context value]
  (fx/swap-context context assoc-in [auth-db-key :authorize-url] value))

(defn pkce-web-auth [context]
  (fx/sub-val context get-in [auth-db-key :pkce-web-auth]))

(defn assoc-pkce-web-auth [context value]
  (fx/swap-context context assoc-in [auth-db-key :pkce-web-auth] value))

(defn reset-auth [context]
  (fx/swap-context context assoc auth-db-key nil))