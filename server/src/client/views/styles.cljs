(ns client.views.styles
  (:require
    [client.styles.core :refer [styles]]))

(defn styles-view []
  [:style
   {:type "text/css"
    :dangerouslySetInnerHTML
    {:__html styles}}])
