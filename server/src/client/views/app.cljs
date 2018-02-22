(ns client.views.app
  (:require
    [client.views.styles :refer [styles-view]]
    [client.state :as state]
    [reagent.core :as r]))

(defn chapter-view [chapter]
  [:div.chapter
   [:div.title (chapter :title)]
   [:div.text (chapter :text)]])

(defn existing-chapters []
  [:div.chapters
   (for [chapter (state/chapters)]
     [chapter-view chapter])])

(defn add-chapter []
  (let [chapter (r/atom {:title "" :text ""})]
    (fn []
      [:form.add-chapter
       {:on-submit (fn [e]
                     (.preventDefault e)
                     (state/add-chapter! (@chapter :title) (@chapter :text))
                     (reset! chapter {:title "" :text ""}))}
       [:input.title
        {:on-change (fn [e]
                      (swap! chapter assoc :title (.. e -target -value)))
         :value (@chapter :title)}]
       [:textarea.text
        {:on-change (fn [e]
                      (swap! chapter assoc :text (.. e -target -value)))
         :value (@chapter :text)}]
       [:input.submit
        {:type "submit"}]])))

(defn app-view []
  [:div.app
   [styles-view]
   [:h1 "Watson TTS Podcast"]
   [existing-chapters]
   [add-chapter]])
