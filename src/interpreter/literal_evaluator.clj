(ns interpreter.literal-evaluator)

(defn evaluate [literal-exp evaluators local-env global-env]
  (:value literal-exp))
