(ns notebox.app-db.db
  (:require [settings]
            [clojure.java.io :as io]
            [cljfx.api :as fx]
            [clojure.core.cache :as cache]
            [utils.core :refer [expand-home]]))

(def expanded-state-path (expand-home settings/state-path))

(def serialized-state
  (if (.exists (io/file expanded-state-path))
    (read-string (slurp (expand-home settings/state-path)))
    {}))

(defmulti event-handler :event/type)

(def *state (atom (fx/create-context
                   serialized-state
                   cache/lru-cache-factory)))
