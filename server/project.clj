(defproject server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [; server
                 [org.clojure/clojure "1.9.0"]
                 [http-kit "2.2.0"]
                 [javax.servlet/servlet-api "2.5"]
                 [compojure "1.6.0"]
                 [ring-middleware-format "0.7.2"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-codec "1.1.0"]
                 [hiccup "1.0.5"]
                 [io.forward/yaml "1.0.6"]
                 [clj-time "0.14.2"]
                 [clj-rss "0.2.3"]

                 ; client
                 [org.clojure/clojurescript "1.9.908"]
                 [garden "1.3.2"]
                 [reagent "0.8.0-alpha1"]
                 [re-frame "0.10.1"]]
  :main ^:skip-aot server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}

  :plugins [[lein-figwheel "0.5.14"]]

  :figwheel {:server-port 6124}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel {:on-jsload "client.core/reload"}
                        :compiler {:main "client.core"
                                   :asset-path "/js/out"
                                   :output-to "resources/public/js/reader.js"
                                   :output-dir "resources/public/js/out"}}]})
