(ns reagent-test.pages.landing
  (:require [reagent-test.db :as db] )
)


;; TODO (wz) create link_to

(defn bet [b]
  [:a {:href (str "/#/bet/" (:key b)) :class "collection-item"} (:title b)]
  )

(defn landing-page []
  [:div.container {:height "600px"}
    [:div {:class "collection"}
      (for [b (:bets @db/app-state)]
        [bet b])]
  ]
   )
