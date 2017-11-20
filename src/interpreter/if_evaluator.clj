(ns interpreter.if-evaluator
  (:require [interpreter.interpreter :as interpreter]))

(declare if-continuation)

(defn evaluate [if-exp evaluators local-env global-env k]
  (interpreter/evaluate-exp (:conditional if-exp)
                            evaluators
                            local-env
                            global-env
                            (if-continuation if-exp evaluators local-env k)))

(defn- if-continuation [if-exp evaluators local-env k]
  (fn [conditional-value global-env]
    (let [exp (if conditional-value
                (:then if-exp)
                (:else if-exp))]
      (interpreter/evaluate-exp exp
                                evaluators
                                local-env
                                global-env
                                k))))
