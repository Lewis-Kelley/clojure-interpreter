(ns interpreter.interpreter
  (:require [interpreter.environments :as environments])
  (:require [interpreter.continuations :as continuations]))

(declare map-evaluate-exp-continuation
         conjoin-continuation
         evaluate-exp
         create-empty-env
         evaluate-exp)

(defn map-evaluate-exp [expressions evaluators local-env global-env k]
  (if (empty? expressions)
    (continuations/apply-k k '() global-env)
    (evaluate-exp (first expressions)
                  evaluators
                  local-env
                  global-env
                  (map-evaluate-exp-continuation expressions
                                                 evaluators
                                                 local-env
                                                 k))))

(defn- map-evaluate-exp-continuation [expressions evaluators local-env k]
  (fn [result global-env]
    (map-evaluate-exp (next expressions)
                      evaluators
                      local-env
                      global-env
                      (conjoin-continuation result k))))

(defn- conjoin-continuation [initial-result k]
  (fn [rest global-env]
    (continuations/apply-k k (cons initial-result rest) global-env)))

(defn evaluate [expression evaluators global-env]
  (evaluate-exp expression
                evaluators
                (environments/create-empty-env)
                global-env
                (fn [result global-env]
                  {:result result
                   :global-env global-env})))

(defn evaluate-exp [expression evaluators local-env global-env k]
  (if-let [evaluator (get evaluators (first expression))]
    (evaluator (second expression) evaluators local-env global-env k)
    (throw (IllegalArgumentException. "Unrecognized expression type"))))
