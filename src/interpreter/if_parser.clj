(ns interpreter.if-parser
  (:require [interpreter.parser :as parser]))

(declare parse-conditional
         parse-then
         parse-else)

(defn parse [raw-if parsers]
  (list :if (-> {}
                (parse-conditional raw-if parsers)
                (parse-then raw-if parsers)
                (parse-else raw-if parsers))))

(defn- parse-conditional [if-exp raw-if parsers]
  (assoc if-exp :conditional (parser/parse-exp (nth raw-if 1) parsers)))

(defn- parse-then [if-exp raw-if parsers]
  (assoc if-exp :then (parser/parse-exp (nth raw-if 2) parsers)))

(defn- parse-else [if-exp raw-if parsers]
  (if (< (count raw-if) 4)
    if-exp
    (assoc if-exp :else (parser/parse-exp (nth raw-if 3) parsers))))
