(ns se.example.queue.core-test
  (:require [clojure.test :refer [deftest is]]
            [se.example.queue.core :as queue]
            [se.example.util.interface :as test-util]))

(deftest test-consume-and-publish
  (let [rabbit-container (test-util/rabbit-mq-container)]
    (with-open [container (:container rabbit-container)
                connection (test-util/rabbit-mq-connect container)
                channel (queue/channel connection)]
      (let [queue-name :aaa
            q        (queue/create-queue channel {:queue-name queue-name
                                                  :queue-opts {}})
            p        (promise)
            _setup-sub (queue/subscribe channel {:queue-name queue-name
                                                 :message-handler (fn [[ch payload]]
                                                                    (tap> {:payload payload})
                                                                    (deliver p payload))
                                                 :subscribe-opts {:auto-ack true}})
            _publish (queue/publish channel {:exchange-name ""
                                             :skip-message-validation true
                                             :queue-name queue-name
                                             :message {::hello ::world}})]
        (is (= {::hello ::world} (deref p)))))))
