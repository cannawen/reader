(ns server.handler
  (:require
    [compojure.core :refer [routes]]
    [compojure.handler]
    [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
    [server.routes.api :as api]))

(def app
  (routes
    (-> api/routes
        (wrap-json-body {:keywords? true})
        (wrap-json-response)
        compojure.handler/api)))
