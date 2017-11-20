(ns interpreter.interpreter
  (:require [interpreter.environments :as environments]))

(declare create-empty-env
         evaluate-exp)

(defn evaluate [expression evaluators]
  (evaluate-exp expression
                evaluators
                (environments/create-empty-env)
                (environments/extend-environment (environments/create-empty-env)
                                                 {'x 3})))

(defn evaluate-exp [expression evaluators local-env global-env]
  (if-let [evaluator (get evaluators (first expression))]
    (evaluator (second expression) evaluators local-env global-env)
    (throw (IllegalArgumentException. "Unrecognized expression type"))))
