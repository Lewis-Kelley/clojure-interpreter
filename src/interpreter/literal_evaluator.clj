(ns interpreter.literal-evaluator
  (:require [interpreter.continuations :as continuations]))

(defn evaluate [literal-exp evaluators local-env global-env k]
  (continuations/apply-k k (:value literal-exp) global-env))
