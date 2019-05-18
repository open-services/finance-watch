(ns finance-watch.core
  (:require [clj-http.client :as http]
            [clojure.data.json :refer [read-str]]
            [iapetos.core :as prometheus]
            [iapetos.export :as export]
            [iapetos.collector.jvm :as jvm]
            [org.httpkit.server :refer [run-server]])
  (:gen-class))

(defonce registry
  (-> (prometheus/collector-registry)
      (jvm/initialize)
      (prometheus/register
        ;; TODO currently hardcoded to open-registry
        (prometheus/gauge :open-registry/balance)
        (prometheus/gauge :open-registry/yearly-income)
        (prometheus/gauge :open-registry/backers-count)
        (prometheus/gauge :open-registry/contributors-count))))

(defn split-on-hyphen [s]
  (clojure.string/split s #"-"))

(defn split-camel-case [s]
  (-> s
      (.replaceAll "([A-Z]+)([A-Z][a-z])" "$1-$2")
      (.replaceAll "([a-z\\d])([A-Z])" "$1-$2")
      (split-on-hyphen)))

(defn camel->hyphen [s]
  (clojure.string/join "-" (split-camel-case s)))

(def format-key (comp keyword
                      clojure.string/lower-case
                      camel->hyphen))

(defn get-metrics [collective]
  (-> "https://opencollective.com/%s.json"
      (format collective)
      (http/get)
      :body
      (read-str :key-fn format-key)))

(defn set-metric [r s m]
  ;; TODO currently hardcoded to open-registry
  (let [k (keyword "open-registry" (name s))]
    (prometheus/set r k (s m))))

(defn update-metrics [collective]
  (let [res (get-metrics collective)]
    (println "Updating metrics")
    (println res)
    (-> registry
        (set-metric :balance res)
        (set-metric :yearly-income res)
        (set-metric :backers-count res)
        (set-metric :contributors-count res))))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/plain; charset=UTF-8"}
   :body (export/text-format registry)})

(defn watch-collective [collective update-interval]
  (future
    (while true
      (update-metrics collective)
      (Thread/sleep (* 1000 update-interval)))))

(def address (str (or (System/getenv "ADDRESS") "127.0.0.1")))
(def port (Integer/parseInt (or (System/getenv "PORT") "8080")))
(def update-interval (Integer/parseInt (or (System/getenv "UPDATE_INTERVAL") "60")))

(defn -main
  [& args]
  (watch-collective "open-registry" update-interval)
  (println "Now watching collective open-registry")
  (println (format "Starting server at %s:%s" address port))
  (run-server app {:address address
                   :port port}))
