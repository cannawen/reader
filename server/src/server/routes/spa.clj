(ns server.routes.spa
  (:require
    [compojure.core :refer [GET defroutes]]
    [compojure.route :as route]
    [hiccup.core :refer [html]]))

(def index-page
  [:html
   [:head
    [:title "Reader"]]
   [:body
    [:div#app]
    [:script {:type "text/javascript" :src "/js/reader.js"}]
    [:script {:type "text/javascript"}
     "client.core.init();"]]])

(defroutes routes
  (route/resources "/")

  (GET "/*" []
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (html index-page)}))
