(ns interpreter.core
  (:require [interpreter.interpreter :as interpreter])
  (:require [interpreter.parser :as parser])
  (:require [interpreter.defn-parser :as defn-parser])
  (:require [interpreter.if-parser :as if-parser])
  (:require [interpreter.literal-evaluator :as literal-evaluator])
  (:require [interpreter.if-evaluator :as if-evaluator]))

(declare load-parsers
         load-evaluators)

(defn -main [& args]
  (let [parsers (load-parsers)
        evaluators (load-evaluators)]
    (println "Ready!")
    (loop []
      (let [exp (read)]
        (println "->" (-> exp
                          (parser/parse-exp parsers)
                          (interpreter/evaluate evaluators)))
        (recur)))))

(defn- load-parsers []
  {'defn defn-parser/parse
   'if if-parser/parse})

(defn- load-evaluators []
  {:literal literal-evaluator/evaluate
   :if if-evaluator/evaluate})
