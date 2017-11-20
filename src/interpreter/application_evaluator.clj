(ns interpreter.application-evaluator
  (:require [interpreter.continuations :as continuations])
  (:require [interpreter.environments :as environments])
  (:require [interpreter.interpreter :as interpreter])
  (:require [interpreter.functions :as functions]))

(declare operands-continuation
         operator-continuation
         bodies-continuation)

(defn evaluate [application-exp evaluators local-env global-env k]
  (interpreter/map-evaluate-exp (:operands application-exp)
                                evaluators
                                local-env
                                global-env
                                (operands-continuation application-exp
                                                       evaluators
                                                       local-env
                                                       k)))

(defn- operands-continuation [application-exp evaluators local-env k]
  (fn [operands global-env]
    (interpreter/evaluate-exp (:operator application-exp)
                              evaluators
                              local-env
                              global-env
                              (operator-continuation evaluators
                                                     operands
                                                     local-env
                                                     k))))

(defn- operator-continuation [evaluators operands local-env k]
  (fn [operator global-env]
    (when (not (functions/function? operator))
      (throw (IllegalArgumentException. "Attempted to apply a non-procedure")))
    (let [function (second operator)]
      (interpreter/map-evaluate-exp (functions/bodies function)
                                    evaluators
                                    (functions/env-from-function function
                                                                 operands
                                                                 local-env)
                                    global-env
                                    (bodies-continuation k)))))

(defn- bodies-continuation [k]
  (fn [bodies global-env]
    (continuations/apply-k k (last bodies) global-env)))
