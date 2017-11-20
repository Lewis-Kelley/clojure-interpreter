(ns interpreter.defn-evaluator
  (:require [interpreter.environments :as environments])
  (:require [interpreter.continuations :as continuations]))

(declare make-function
         extend-global-env)

(defn evaluate [defn-exp evaluators local-env global-env k]
  (let [function (make-function defn-exp local-env)
        new-global-env (extend-global-env defn-exp
                                          global-env
                                          function)]
    (continuations/apply-k k function new-global-env)))

(defn- make-function [defn-exp local-env]
  (list :function {:arguments (:arguments defn-exp)
                   :bodies (:bodies defn-exp)
                   :local-env local-env}))

(defn- extend-global-env [defn-exp global-env function]
  (environments/extend-environment global-env {(:name defn-exp) function}))
