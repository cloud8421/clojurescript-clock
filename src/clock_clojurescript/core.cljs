(ns ^:figwheel-always clock-clojurescript.core
    (:require [reagent.core :as reagent]
              [cljs-time.core :as time]
              [cljs-time.format :as format]
              [ajax.core :refer [GET]]
              [clock-clojurescript.weather :refer [get-weather]]
              [clock-clojurescript.state :refer [app-state tick]]))

(enable-console-print!)

(def time-formatter (format/formatter "HH:mm:ss"))
(def date-formatter (format/formatter "dd-MM-yyyy"))

(defn format-time [t]
  (format/unparse time-formatter t))

(defn format-date [t]
  (format/unparse date-formatter t))

(defn get-main-container []
  (. js/document (getElementById "app")))

(defn bootstrap-data []
  (.setInterval js/window tick 1000)
  (get-weather (time/now)))

(defn clock []
  (let [current-time (:current-time @app-state)
        weather (:weather @app-state)]
    [:div.clock
     [:h1 (format-time current-time)]
     [:h2 (format-date current-time)]
     [:h3 (:summary (:currently weather))]]))

(def clock-with-actions
  (with-meta clock
    {:component-did-mount bootstrap-data}))

(reagent/render-component [clock-with-actions] (get-main-container))
