(ns client.state
  (:require
    [reagent.core :as r]))

(defonce state
  (r/atom
    {:chapters [{:title "title one" :text "text one"}
                {:title "title two" :text "text two"}]}))

(defn chapters []
  (@state :chapters))

(defn add-chapter! [title text]
  (swap! state update :chapters conj {:title title :text text}))
