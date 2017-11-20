(ns interpreter.if-evaluator
  (:require [interpreter.interpreter :as interpreter]))

(defn evaluate [if-exp evaluators local-env global-env]
  (if (interpreter/evaluate-exp (:conditional if-exp)
                                evaluators
                                local-env
                                global-env)
    (interpreter/evaluate-exp (:then if-exp)
                              evaluators
                              local-env
                              global-env)
    (interpreter/evaluate-exp (:else if-exp)
                              evaluators
                              local-env
                              global-env)))
