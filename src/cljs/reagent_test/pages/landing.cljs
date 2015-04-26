(ns reagent-test.pages.landing
  (:require [reagent-test.db :as db]
            [reagent-test.router :as router]
            [reagent-test.core :as core]
            )
)

;; TODO (wz) create link_to

(defn bet [b]
  [:tr
   [:td {:class "truncate"} (:title b)]
   [:td {:class "truncate"} (:creator b)]
   [:td
    [:a {:href (core/bet-route {:id (:key b)})}
     "Bet"]
    ]
   ]
  )

(defn landing-page-content []
  [:div
   [:h4 "Ledger"]
   [:div {:class "row"}
    [:div {:class "col s8"}
     [:table {:class "hoverable"}
      [:thead
       [:tr
        [:th {:data-field "id"} "Name"]
        [:th {:data-field "author"} "Author"]
        [:th {:data-field "bet"} "Bet"]
        ]
       ]
      [:tbody
       (for [b (:bets @db/app-state)]
         [bet b])
       ]
      ]
     ]
    ]
   ]
  )
