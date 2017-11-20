(ns interpreter.parser)

(declare parse-exp
         parse-item
         literal?
         parse-list
         parse-vector
         parse-map)

(defn map-parse-exp [raw-exps parsers]
  (map (fn [raw-exp] (parse-exp raw-exp parsers)) raw-exps))

(defn parse-exp [raw-exp parsers]
  (cond
    (list? raw-exp) (parse-list raw-exp parsers)
    (map? raw-exp) (parse-map raw-exp)
    (vector? raw-exp) (parse-vector raw-exp parsers)
    true (parse-item raw-exp)))

(defn- parse-item [raw-item]
  (if (literal? raw-item)
    (list :literal {:value raw-item})
    (list :variable {:symbol raw-item})))

(defn- literal? [item]
  (or (number? item) (nil? item)))

(defn- parse-list [raw-list parsers]
  (if-let [parser (get parsers (first raw-list))]
    (parser raw-list parsers)
    (list :application {:operator (parse-exp (first raw-list) parsers)
                        :operands (map-parse-exp (next raw-list) parsers)})))

(defn- parse-vector [raw-vector parsers]
  (list :vector {:elements (map-parse-exp raw-vector parsers)}))

(defn- parse-map [raw-map]
  (list :map raw-map))
