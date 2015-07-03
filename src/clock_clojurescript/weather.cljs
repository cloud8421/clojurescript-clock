(ns ^:figwheel-always clock-clojurescript.weather
    (:require [reagent.core :as reagent]
              [cljs-time.format :as format]
              [ajax.core :refer [GET]]
              [clock-clojurescript.state :refer [update-weather]]))

(defonce api-url "https://new-bamboo-calendar-api.herokuapp.com")
(defonce api-headers {:accept "application/vnd.calendar-v1+json"})
(defonce default-lat 51.5181212)
(defonce default-lng -0.0971355)

(def iso-formatter (format/formatters :date-time))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn get-weather [t]
  (let [current-time (format/unparse iso-formatter t)]
    (GET (str api-url "/weather") {:params {:lat default-lat
                                            :lng default-lng
                                            :time current-time}
                                   :headers api-headers
                                   :keywords? true
                                   :response-format :json
                                   :handler update-weather
                                   :error-handler error-handler})))
