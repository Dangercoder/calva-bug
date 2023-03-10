(ns se.example.util.interface
  (:require [se.example.util.core :as core]))

(defn rabbit-mq-container []
  (core/rabbit-mq-container))

(defn rabbit-mq-connect [rabbit-container]
  (core/rabbit-mq-connect rabbit-container))
