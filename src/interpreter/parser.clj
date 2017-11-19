(ns interpreter.parser)

(declare parse-exp
         parse-item
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
  {:item raw-item})

(defn- parse-list [raw-list parsers]
  (if-let [parser (get parsers (first raw-list))]
    (parser raw-list parsers)
    {:list (map-parse-exp raw-list parsers)}))

(defn- parse-vector [raw-vector parsers]
  {:vector (map-parse-exp raw-vector parsers)})

(defn- parse-map [raw-map]
  {:map raw-map})
