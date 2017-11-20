(ns interpreter.interpreter
  (:require [interpreter.environments :as environments]))

(declare create-empty-env
         evaluate-exp)

(defn evaluate [expression evaluators]
  (evaluate-exp expression
                evaluators
                (environments/create-empty-env)
                (environments/create-empty-env)
                (fn [result global-env]
                  result)))

(defn evaluate-exp [expression evaluators local-env global-env k]
  (if-let [evaluator (get evaluators (first expression))]
    (evaluator (second expression) evaluators local-env global-env k)
    (throw (IllegalArgumentException. "Unrecognized expression type"))))
