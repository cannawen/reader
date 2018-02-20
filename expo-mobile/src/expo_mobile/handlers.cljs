(ns expo-mobile.handlers
  (:require
    [re-frame.core :refer [reg-event-db ->interceptor]]
    [clojure.spec.alpha :as s]
    [expo-mobile.db :as db :refer [app-db]]))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
  :initialize-db
  (fn [_ _]
    app-db))

(reg-event-db
  :set-greeting
  (fn [db [_ value]]
    (assoc db :greeting value)))
