(ns se.example.util.core
  (:require [clj-test-containers.core :as tc]
            [se.example.queue.interface :as queue]))

(defn rabbit-mq-container []
   (-> (tc/create {:image-name    "heidiks/rabbitmq-delayed-message-exchange:latest"
                   :exposed-ports [5672 15672]
                   :env-vars      {"RABBITMQ_DEFAULT_USER" "admin"
                                   "RABBITMQ_DEFAULT_PASS" "password"}})
       
       (tc/start!)))

(defn rabbit-mq-connect [rabbit-container]
  (queue/connect {:username "admin"
                  :password "password"
                  :port (get (:mapped-ports rabbit-container) 5672)
                  :host (:host rabbit-container)}))