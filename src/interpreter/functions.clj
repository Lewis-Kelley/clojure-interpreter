(ns interpreter.functions
  (:require [interpreter.environments :as environments]))

(declare make-parameter-map
         assert-arguments-count)

(defn create-function [parameters bodies current-env]
  (list :function {:parameters parameters
                   :bodies bodies
                   :local-env current-env}))

(defn bodies [function]
  (:bodies function))

(defn env-from-function [function arguments current-env]
  (let [parameters (:parameters function)]
    (assert-arguments-count parameters arguments)
    (environments/extend-environment current-env
                                     (make-parameter-map parameters arguments))))

(defn- assert-arguments-count [parameters arguments]
  (when (not (= (count parameters) (count arguments)))
      (throw (IllegalArgumentException. "Incorrect number of parameters passed"))))

(defn- make-parameter-map [parameters arguments]
  (if (empty? parameters)
    {}
    (assoc (make-parameter-map (next parameters) (next arguments))
           (first parameters) (first arguments))))

(defn function? [form]
  (and (list? form) (= (first form) :function)))
