(ns interpreter.interpreter)

(declare create-empty-env
         evaluate-exp)

(defn evaluate [expression evaluators]
  (evaluate-exp expression
                evaluators
                (create-empty-env)
                (create-empty-env)))

(defn- create-empty-env []
  (list :empty-env '()))

(defn evaluate-exp [expression evaluators local-env global-env]
  (if-let [evaluator (get evaluators (first expression))]
    (evaluator (second expression) evaluators local-env global-env)
    (throw (IllegalArgumentException. "Unrecognized expression type"))))
