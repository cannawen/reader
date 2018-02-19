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
    [cheshire.core :as json]
    [clj-rss.core :as rss]))

(defn save-to-file [file-name content]
  (with-open [o (io/output-stream file-name)]
    (.write o content)))

(defn save-chapter! [title text]
  (->> @(http/request {:url "https://stream.watsonplatform.net/text-to-speech/api/v1/synthesize"
                       :method :post
                       :basic-auth ["7c0a9e4c-e80f-45d5-bb2e-8eb9cfffbf55"
                                    "qtzLNyofVSzK"]         ;TODO revoke credentials
                       :headers {"Content-Type" "application/json"
                                 "Accept" "audio/mp3"}
                       :as :byte-array
                       :body (json/encode {"text" text})})
    :body
    (save-to-file (str "public/media/" title ".mp3"))))

(defn get-files [directory extension]
  (->> directory
    clojure.java.io/file
    file-seq
    (filter (fn [file]
              (clojure.string/ends-with? (.getPath file) extension)))
    (map (fn [file] (.getName file)))))

(defn generate-rss-item [file-name]
  {:title file-name :link (str "http://localhost:2351/" file-name)})

(defn generate-rss! []
  (let [description {:title "Generated RSS Feed" :link "http://localhost:2351/" :description "Watson-generated RSS Feed"}
        items (map generate-rss-item (get-files "public/media" "mp3"))
        rss (apply rss/channel-xml description items)]
    (spit "public/rss.xml" rss)))

(defroutes routes
  (context "/api" _

    (POST "/generate-rss" request
      (generate-rss!)
      (println "generated rss")
      {:status 200
       :body "OK"})

    (POST "/chapter" request
      (let [title (get-in request :body :title)
            text (get-in request :body :text)]
        (save-chapter! title text)
        (generate-rss!)
        (println "chapter processed")
        {:status 200
         :body "OK"}))))
