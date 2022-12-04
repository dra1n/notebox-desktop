(ns notebox.common.buttons.views
  (:require [cljfx.api :as fx]
            [notebox.app-db.queries :as queries]
            [notebox.app-db.events :as events]))

(defn button-with-confirmation-dialog [{:keys [fx/context
                                               confirmation-id
                                               on-confirmed
                                               button
                                               dialog-pane]}]
  {:fx/type fx/ext-let-refs
   :refs {::dialog {:fx/type :dialog
                    :showing (fx/sub-ctx context queries/show-confirmation? confirmation-id)
                    :on-hidden {:event/type ::events/on-confirmation-dialog-hidden
                                :confirmation-id confirmation-id
                                :on-confirmed on-confirmed}
                    :dialog-pane (merge {:fx/type :dialog-pane
                                         :button-types [:cancel :ok]}
                                        dialog-pane)}}
   :desc (merge {:fx/type :button
                 :on-action {:event/type ::events/show-confirmation
                             :confirmation-id confirmation-id}}
                button)})