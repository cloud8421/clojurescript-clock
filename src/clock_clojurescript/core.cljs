(ns ^:figwheel-always clock-clojurescript.core
    (:require [reagent.core :as reagent :refer [atom]]
              [cljs-time.core :as time]
              [cljs-time.format :as format]))

(enable-console-print!)

(def app-state (atom {:current-time (time/now)}))

(def time-formatter (format/formatter "HH:mm:ss"))

(defn tick []
  (swap! app-state assoc :current-time (time/now)))

(defn format-time [t]
  (format/unparse time-formatter t))

(defn get-main-container []
  (. js/document (getElementById "app")))

(defn clock []
  (let [time-string (-> (:current-time @app-state) format-time)]
    [:div.clock
     [:h1 time-string]]))

(.setInterval js/window tick 1000)

(reagent/render-component [clock] (get-main-container))
