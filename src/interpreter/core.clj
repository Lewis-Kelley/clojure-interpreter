(ns interpreter.core
  (:require [interpreter.environments :as environments])
  (:require [interpreter.interpreter :as interpreter])
  (:require [interpreter.parser :as parser])
  (:require [interpreter.defn-parser :as defn-parser])
  (:require [interpreter.if-parser :as if-parser])
  (:require [interpreter.literal-evaluator :as literal-evaluator])
  (:require [interpreter.variable-evaluator :as variable-evaluator])
  (:require [interpreter.application-evaluator :as application-evaluator])
  (:require [interpreter.if-evaluator :as if-evaluator])
  (:require [interpreter.defn-evaluator :as defn-evaluator]))

(declare load-parsers
         load-evaluators)

(defn -main [& args]
  (let [parsers (load-parsers)
        evaluators (load-evaluators)]
    (println "Ready!")
    (loop [global-env (environments/create-empty-env)]
      (let [evaluation (-> (read)
                           (parser/parse-exp parsers)
                           (interpreter/evaluate evaluators global-env))]
        (println "->" (:result evaluation))
        (recur (:global-env evaluation))))))

(defn- load-parsers []
  {'defn defn-parser/parse
   'if if-parser/parse})

(defn- load-evaluators []
  {:literal literal-evaluator/evaluate
   :variable variable-evaluator/evaluate
   :application application-evaluator/evaluate
   :if if-evaluator/evaluate
   :defn defn-evaluator/evaluate})
