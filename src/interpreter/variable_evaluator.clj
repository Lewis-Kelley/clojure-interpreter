(ns interpreter.variable-evaluator
  (:require [interpreter.environments :as environments]))

(defn evaluate [variable-exp evaluators local-env global-env]
  (try
    (environments/lookup-symbol local-env (:symbol variable-exp))
    (catch IllegalArgumentException e
      (environments/lookup-symbol global-env (:symbol variable-exp)))))
