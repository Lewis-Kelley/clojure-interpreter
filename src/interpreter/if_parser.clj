(ns interpreter.if-parser)

(declare parse-conditional
         parse-then
         parse-else
         wrap-if-exp)

(defn parse [raw-if]
  (-> {}
      (parse-conditional raw-if)
      (parse-then raw-if)
      (parse-else raw-if)
      (wrap-if-exp)))

(defn- parse-conditional [if-exp raw-if]
  (assoc if-exp :conditional (nth raw-if 1)))

(defn- parse-then [if-exp raw-if]
  (assoc if-exp :then (nth raw-if 2)))

(defn- parse-else [if-exp raw-if]
  (if (< (count raw-if) 4)
    if-exp
    (assoc if-exp :else (nth raw-if 3))))

(defn- wrap-if-exp [if-exp]
  {:if if-exp})
