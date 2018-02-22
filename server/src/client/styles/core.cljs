(ns client.styles.core
  (:require
    [garden.core :refer [css]]))

(def reset-styles
  [:body
   {:margin 0
    :padding 0}
   [:h1 :h2 :h3
    {:margin 0}]])

(def body-styles
  [:body
   {:font-size "14px"
    :font-family "Roboto, Arial"
    :line-height "1.35"
    :background "#ededed"}])

(def styles
  (css
    reset-styles
    body-styles
    [:#app]))
