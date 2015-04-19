(ns reagent-test.db
  (:require
    [reagent.core :as reagent :refer [atom]]
  )
)

(def app-state
  (reagent/atom
    {:bets
     [
      {:id 1
       :fact "Winner of presidential elections in Poland in April"
       :title "Who will win presidential elections?"
       :answers ["BK", "AD", "MO"]
       :solves_by 1428675249
       :creator "wojciech.ziniewicz@gmail.com"}
      {:id 2
       :fact "Winner party in parlamentary elections in Poland at 10.10.2015?"
       :solves_by 1428675249
       :title "Which party wins elections in Poland?"
       :answers ["PO", "PiS", "SLD"]
       :creator "Wojtek"}
      {:id 3
       :fact "Will Poland adopt Euro currency by 01.2016?"
       :title "Will Poland adopt Euro currency by 01.2016?"
       :answers ["yes" "no"]
       :solves_by 1428675249
       :creator "wojciech.ziniewicz@gmail.com"
       }
      ]
     }
    )
  )

(defn update-bets! [f & args]
  (apply swap! app-state update-in [:bets] f args))

(defn add-bet! [b]
  (update-bets! conj b))

(defn remove-bet! [b]
  (update-bets! (fn [cs]
                      (vec (remove #(= % b) cs)))
                    b))
