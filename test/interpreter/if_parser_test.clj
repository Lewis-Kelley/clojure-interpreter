(ns interpreter.if-parser-test
  (:require [clojure.test :refer :all]
            [interpreter.if-parser :refer :all]))

(deftest parse-simple-two-if
  (testing "Parse a two-armed if statement without nested expressions."
    (is {:if {:conditional nil
              :then 1
              :else 2}}
        (parse '(if nil
                  1
                  2)
               {}))))

(deftest parse-simple-one-if
  (testing "Parse a one-armed if statement without nested expressions."
    (is {:if {:conditional nil
              :then 1}}
        (parse '(if nil 1)
               {}))))
