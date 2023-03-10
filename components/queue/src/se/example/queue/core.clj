(ns se.example.queue.core
  (:require  [langohr.core      :as rmq]
             [langohr.channel   :as lch]
             [langohr.queue     :as lq]
             [taoensso.nippy :as nippy]
             [langohr.exchange :as exchange]
             [malli.core :as m]
             [langohr.consumers :as lc]
             [langohr.basic     :as lb]))

(defn create-queue
  [channel {:keys [queue-name
                          queue-opts]}]
  (lq/declare channel (name queue-name) queue-opts))

(defn declare-exchange
  [channel {:keys [exchange-name
                   exchange-type
                   exchange-opts]}]
  (exchange/declare channel (name exchange-name) (name exchange-type) exchange-opts))

(defn handle-message-fn
  [handler]
  (fn [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
    (let [data (nippy/thaw payload)]
      (tap> data)
      (handler [ch data]))))

(defn publish [channel {:keys [exchange-name queue-name message delay-ms skip-messsage-validation]}]
  (if skip-messsage-validation
    (lb/publish channel (name exchange-name) (name queue-name) (nippy/freeze message) (if delay-ms
                                                                                        {:headers {"x-delay" delay-ms}}
                                                                                        {}))
    (if
      (lb/publish channel (name exchange-name) (name queue-name) (nippy/freeze message) (if delay-ms
                                                                                          {:headers {"x-delay" delay-ms}}
                                                                                          {}))
      (throw (ex-info "Invalid message schema" {})))))

(defn bind [channel {:keys [queue-name exchange-name]}]
  (lq/bind channel (name queue-name) (name exchange-name) {:routing-key (name queue-name)}))

(defn subscribe [channel {:keys [queue-name message-handler subscribe-opts]}]
  (lc/subscribe channel (name queue-name) (handle-message-fn message-handler) subscribe-opts))

(defn connect
  [{:keys [username password port host]}]
  (rmq/connect {:username username
                :password password
                :port port
                :host host}))

(defn channel [conn]
  (lch/open conn))
