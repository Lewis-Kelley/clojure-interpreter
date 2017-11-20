(ns interpreter.variable-evaluator
  (:require [interpreter.environments :as environments])
  (:require [interpreter.continuations :as continuations]))

(defn evaluate [variable-exp evaluators local-env global-env k]
  (continuations/apply-k
   k
   (try
     (environments/lookup-symbol local-env (:symbol variable-exp))
     (catch IllegalArgumentException e
       (environments/lookup-symbol global-env (:symbol variable-exp))))
   global-env))
