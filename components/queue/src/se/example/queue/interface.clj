(ns se.example.queue.interface
  (:require [se.example.queue.core :as core]))


(defn create-queue [channel opts]
  (core/create-queue channel opts))

(defn declare-exchange [channel opts]
  (core/declare-exchange channel opts))

(defn publish [channel opts]
  (core/publish channel opts))

(defn subscribe [channel opts]
  (core/subscribe channel opts))

(defn connect [opts]
(core/connect opts))

(defn bind [channel opts]
  (core/bind channel opts))

(defn channel [conn]
  (core/channel conn))