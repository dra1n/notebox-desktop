(ns notebox.fragments.sidemenu.views
  (:require [cljfx.api :as fx]
            [notebox.app-db.events :as events]
            [notebox.app-db.queries :as queries]))

(defn sidemenu [{:keys [fx/context]}]
  (let [syncing? (fx/sub-ctx context queries/syncing?)]
    {:fx/type :v-box
     :children [{:fx/type :label
                 :text "side menu"}
                {:fx/type :button
                 :text (str "refresh" (if syncing? " [syncing]" ""))
                 :on-action {:event/type ::events/fetch-notes-info}}
                {:fx/type :button
                 :text "switch scene"
                 :on-action {:event/type ::events/set-scene :data :login}}]}))