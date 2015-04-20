(ns reagent-test.pages.landing
  (:require [reagent-test.db :as db] )
)


(defn bet [b]
  [:li
   [:span (:title b)]
   [:span (:answers b)]
   [:span (:creator b)]
   ])

(defn landing-page []
  [:div
   "Landing page"
   [:ul
    (for [b (:bets @db/app-state)]
      [bet b])]
   ])

