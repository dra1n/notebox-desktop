(ns notebox.app-db.db
  (:require [clojure.java.io :as io]
            [cljfx.api :as fx]
            [clojure.core.cache :as cache]
            [utils.core :refer [expand-home]]))

(def config-path "~/.notebox")
(def state-path (str config-path "/state"))

(def expanded-state-path (expand-home state-path))

(def serialized-state
  (if (.exists (io/file expanded-state-path))
    (read-string (slurp (expand-home state-path)))
    {}))

(defmulti event-handler :event/type)

(def *state (atom (fx/create-context
                   serialized-state
                   cache/lru-cache-factory)))
