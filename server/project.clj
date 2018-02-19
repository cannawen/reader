(defproject server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [http-kit "2.2.0"]
                 [javax.servlet/servlet-api "2.5"]
                 [compojure "1.6.0"]
                 [ring-middleware-format "0.7.2"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-codec "1.1.0"]
                 [hiccup "1.0.5"]
                 [io.forward/yaml "1.0.6"]
                 [clj-time "0.14.2"]
                 [clj-rss "0.2.3"]]
  :main ^:skip-aot server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
