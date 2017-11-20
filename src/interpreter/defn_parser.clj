(ns interpreter.defn-parser
  (:require [interpreter.parser :as parser]))

(declare parse-name
         parse-docstring
         parse-args
         parse-bodies)

(defn parse [raw-defn parsers]
  (list :defn (-> {}
                  (parse-name raw-defn)
                  (parse-docstring raw-defn)
                  (parse-args raw-defn)
                  (parse-bodies raw-defn parsers))))

(defn- parse-name [defn-exp raw-defn]
  (assoc defn-exp :name (nth raw-defn 1)))

(defn- parse-docstring [defn-exp raw-defn]
  (if (string? (nth raw-defn 2))
    (assoc defn-exp :docstring (nth raw-defn 2))
    defn-exp))

(defn- parse-args [defn-exp raw-defn]
  (cond
    (vector? (nth raw-defn 2)) (assoc defn-exp :arguments (nth raw-defn 2))
    (vector? (nth raw-defn 3)) (assoc defn-exp :arguments (nth raw-defn 3))
    true (throw (IllegalArgumentException. "No vector of arguments provided to defn."))))

(defn- parse-bodies [defn-exp raw-defn parsers]
  (let [bodies (nthnext raw-defn (if (contains? defn-exp :docstring) 4 3))]
    (println bodies)
    (if (not (empty? bodies))
      (assoc defn-exp :bodies (parser/map-parse-exp bodies parsers))
      (throw (IllegalArgumentException. "Empty defn.")))))
