(ns ^:figwheel-always clock-clojurescript.state
    (:require [reagent.core :as reagent :refer [atom]]
              [cljs-time.core :as time]
              [cljs-time.format :as format]))

(def app-state (atom {:current-time (time/now)
                      :weather {}}))

(defn tick []
  (swap! app-state assoc :current-time (time/now)))

(defn update-weather [new-data]
  (swap! app-state assoc :weather new-data))
