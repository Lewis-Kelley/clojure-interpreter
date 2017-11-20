(ns interpreter.environments)

(defn create-empty-env []
  (list :empty-env '()))

(defn extend-environment [old-environment new-env-map]
  (list :env (assoc new-env-map :extended-env old-environment)))

(defn lookup-symbol [environment symbol]
  (when (= :empty-env (first environment))
    (throw (IllegalArgumentException. "Symbol not found in environment.")))
  (let [env-map (second environment)]
    (if (contains? env-map symbol)
      (get env-map symbol)
      (lookup-symbol (:extended-env env-map) symbol))))
