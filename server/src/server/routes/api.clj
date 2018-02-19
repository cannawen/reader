(ns server.routes.api
  (:require
    [compojure.core :refer [POST defroutes context]]
    [clojure.java.io :as io]
    [ring.util.codec :as codec]
    [yaml.core :as yaml]
    [clj-time.core :as t]
    [clj-time.format :as f]
    [clj-time.coerce :as c]
    [clojure.java.shell :refer [sh with-sh-dir]]
    [org.httpkit.client :as http]
    [cheshire.core :as json]))

(defn save-to-file [fname content]
  (with-open [o (io/output-stream fname)]
    (.write o content)))

(defn save-chapter! [{:keys [text]}]

  (->> @(http/request {:url "https://stream.watsonplatform.net/text-to-speech/api/v1/synthesize"
                       :method :post
                       :basic-auth ["7c0a9e4c-e80f-45d5-bb2e-8eb9cfffbf55"
                                    "qtzLNyofVSzK"]
                       :headers {"Content-Type" "application/json"
                                 "Accept" "audio/mp3"}
                       :as :byte-array
                       :body (json/encode {"text" text})})
      :body
      (save-to-file "media-out/test.mp3")))

(defroutes routes
  (context "/api" _

    (POST "/chapter" request
      (save-chapter! (:body request))
      (println "chapter processed")
      {:status 200
       :body "OK"})))
