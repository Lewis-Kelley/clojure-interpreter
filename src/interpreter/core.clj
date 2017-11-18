(ns interpreter.core
  (:require [interpreter.parser :as parser]))

(defn -main
  "Main entry point of the program."
  [& args]
  (loop []
    (let [exp (read)]
      (println "->" (parser/parse-exp exp))
      (recur))))
