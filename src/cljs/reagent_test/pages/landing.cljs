(ns reagent-test.pages.landing
  (:require [reagent-test.db :as db :refer [app-state]] )
)


(defn landing-page []
  [:div
   "Landing page"
   [:ul
    (for [b (:bets app-state)]
      [bet b])]
   ])

(defn bet [b]
  [:li
   [:span (title b)]
   [:span (creator b)]
   ])
