(ns interpreter.core
  (:require [interpreter.parser :as parser])
  (:require [interpreter.defn-parser :as defn-parser])
  (:require [interpreter.if-parser :as if-parser]))

(declare load-parsers)

(defn -main [& args]
  (let [parsers (load-parsers)]
    (println "Ready!")
    (loop []
      (let [exp (read)]
        (println "->" (parser/parse-exp exp parsers))
        (recur)))))

(defn- load-parsers []
  {'defn defn-parser/parse
   'if if-parser/parse})
