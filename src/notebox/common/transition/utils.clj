(ns notebox.common.transition.utils
  (:require [cljfx.api :as fx]))

(defn- let-refs [refs desc]
  {:fx/type fx/ext-let-refs
   :refs refs
   :desc desc})

(defn- get-ref [ref]
  {:fx/type fx/ext-get-ref
   :ref ref})

(def transition-target-prop
  "For transitions that target a node this map specifies
  the prop to write to. eg., :fade-transition takes :node prop,
  but :fill-transition takes a :shape prop."
  (into {}
        (concat (map vector
                     #{:fade-transition
                       :scale-transition
                       :rotate-transition
                       :path-transition
                       :translate-transition
                       :parallel-transition
                       :sequential-transition}
                     (repeat :node))
                (map vector
                     #{:fill-transition
                       :stroke-transition}
                     (repeat :shape)))))

(defn add-target-prop [desc node]
  (let [target-prop (get transition-target-prop (:fx/type desc))]
    (cond-> desc
      target-prop (assoc target-prop node))))

(defn start-transition-on
  "Returns the given desc the given transition animation attached
  and running."
  [{:keys [desc transition]}]
  (let-refs {::transition-node desc}
            (let [tn (get-ref ::transition-node)]
              (let-refs {::transition (-> transition
                                          (add-target-prop tn)
                                          (assoc :status :running))}
                        tn))))
