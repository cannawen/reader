(ns client.styles.core
  (:require
    [garden.core :refer [css]]))

(def reset-styles
  [:body
   {:margin 0
    :padding 20}
   [:h1 :h2 :h3
    {:margin 0}]])

(def body-styles
  [:body
   {:font-size "14px"
    :font-family "Roboto, Arial"
    :line-height "1.35"
    :background "#ededed"
    :display "flex"
    :flex-direction "row"
    :justify-content "center"}])

(def add-chapter-styles
  [:.add-chapter
   [:>.title
    {:display "block"
     :border "1px solid #aaa"
     :padding "5px"
     :margin-bottom "5px"
     :width "100%"}]
   [:>.text
    {:display "block"
     :border "1px solid #aaa"
     :padding "5px"
     :margin-bottom "5px"
     :width "100%"
     :height "300px"}]])

(def react-container
  [:.app
   {:display "flex"
    :flex-direction "column"
    :justify-content "center"}])

(def styles
  (css
    reset-styles
    body-styles
    [:#app
     {:width "60%"}
     react-container
     add-chapter-styles]))
