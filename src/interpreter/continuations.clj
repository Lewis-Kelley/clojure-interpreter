(ns interpreter.continuations)

(defn apply-k [k value global-env]
  (k value global-env))
