(defproject finance-watch "0.1.0-SNAPSHOT"
  :description "Gathers metrics about finances and exposes it as prometheus metrics"
  :url "https://github.com/open-services/finance-watch"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-http "3.10.0"]
                 [org.clojure/data.json "0.2.6"]
                 [iapetos "0.1.8"]
                 [io.prometheus/simpleclient_hotspot "0.6.0"]
                 [http-kit "2.3.0"]]
  :main ^:skip-aot finance-watch.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
