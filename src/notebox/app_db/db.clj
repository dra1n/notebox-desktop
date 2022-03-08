(ns notebox.app-db.db
  (:require [cljfx.api :as fx]
            [clojure.core.cache :as cache]))

(defmulti event-handler :event/type)

(def *state (atom (fx/create-context {} cache/lru-cache-factory)))
