(ns client.state
  (:require
    [reagent.core :as r]))

(defonce state
  (r/atom
    {:chapters []}))

(defn chapters []
  (@state :chapters))

(defn add-chapter! [title text]
  (swap! state update :chapters conj {:title title :text text}))
