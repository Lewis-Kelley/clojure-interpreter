(ns interpreter.parser
  (:require [interpreter.defn-parser :as defn-parser])
  (:require [interpreter.if-parser :as if-parser]))

(declare parse-item
         parse-list
         parse-vector
         parse-map)

(defn parse-exp [raw-exp]
  (cond
    (list? raw-exp) (parse-list raw-exp)
    (map? raw-exp) (parse-map raw-exp)
    (vector? raw-exp) (parse-vector raw-exp)
    true (parse-item raw-exp)))

(defn- parse-item [raw-item]
  {:item raw-item})

(defn- parse-list [raw-list]
  (let [rand (first raw-list)]
    (cond
      (= rand 'defn) (defn-parser/parse raw-list)
      (= rand 'if) (if-parser/parse raw-list)
      true {:list (map parse-exp raw-list)})))

(defn- parse-vector [raw-vector]
  {:vector (map parse-exp raw-vector)})

(defn- parse-map [raw-map]
  {:map raw-map})
