(ns interpreter.defn-parser-test
  (:require [clojure.test :refer :all]
            [interpreter.defn-parser :refer :all]))

(deftest simple-with-docstring
  (testing "Parse a defn with a docstring and no nested expressions."
    (is {:defn {:name 'function
                :docstring "A docstring"
                :arguments []
                :bodies '(1)}}
        (parse '(defn function "A docstring" [] 1) {}))))

(deftest simple-no-docstring
  (testing "Parse a defn with a docstring and no nested expressions."
    (is {:defn {:name 'function
                :arguments []
                :bodies '(1)}}
        (parse '(defn function [] 1) {}))))
