(ns reagent-test.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [jayq.core :as jayq.core]
              [cljsjs.react :as react])
    (:use     [jayq.core :only [$]])
    (:import goog.History))

(enable-console-print!)

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

;; Navbar

(defn profile-dropdown-item []
  [:ul {:id "profile-dropdown" :class "dropdown-content"}
   [:li
    [:a {:href "#/finances"} "Finances"]]
   [:li
    [:a {:href "#/profile"} "Profile"]]
   ]
)

(defn signin-dropdown-item []
  [:ul {:id "signin-dropdown" :class "dropdown-content"}
   [:li
    [:a {:href "#/signin"} "Sign in"]]
   [:li
    [:a {:href "#/signup"} "Sign up"]]
   ]
)

(defn navbar-dropdowns-data []
  [:div.dropdown-data
  [profile-dropdown-item]
  [signin-dropdown-item]]
)

(defn navbar []
  [:div.navbar-container
    [navbar-dropdowns-data]
    [:nav
     [:div.nav-wrapper
       [:a {:href "" :class "brand-logo"} "Bababet"]
       [:ul {:class "right hide-on-med-and-down"}
        [:li
         [:a {:href "#/"} "Home"]]
        [:li
         [:a {:href "#/propose"} "Propose"]]
        [:li
         [:a {:href "#" :onclick "return false;" :class "profile-dropdown-navbar-item" :data-activates "profile-dropdown" }
          "Profile"
          [:i {:class "mdi-navigation-arrow-drop-down right"}]]]
        [:li
         [:a {:href "#" :class "signin-dropdown-navbar-item" :data-activates "signin-dropdown" }
          "Sign in"
          [:i {:class "mdi-navigation-arrow-drop-down right"}]]]
      ]
     ]
    ]
  ]
)

;;
(defn landing-page []
  [:div
   [navbar]
   [:div "Landing page"]
  ]
)

(defn signin-page []
  [:div
   [navbar]
   [:div "Signin page"]
  ]
)

(defn signup-page []
  [:div
   [navbar]
   [:div "Signup page"]
  ]
)

(defn propose-page []
  [:div
   [navbar]
   [:div "Propose page"]
  ]
)

(defn bet-page [id]
  [:div
   [navbar]
   [:div (str "Bet page: " id)]
  ]
)

(defn profile-page []
  [:div
   [navbar]
   [:div "Profile"]
  ]
)

(defn finances-page []
  [:div
   [navbar]
   [:div "Finances"]
  ]
)

(defn current-page []
  [:div
    [navbar]]
    [:div [(session/get :current-page)]
  ]
)

;; -------------------------
;; Routes

(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :current-page #'landing-page))

(secretary/defroute "/signin" []
  (session/put! :current-page #'signin-page))

(secretary/defroute "/signup" []
  (session/put! :current-page #'signup-page))

(secretary/defroute "/propose" []
  (session/put! :current-page #'propose-page))

(secretary/defroute "/bet/:id" {id :id}
  (session/put! :current-page #'bet-page)
  (js/console.log (str "Bet: " id))
  )

(secretary/defroute "/profile" []
  (session/put! :current-page #'profile-page))

(secretary/defroute "/finances" []
  (session/put! :current-page #'finances-page))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn activate-dropdowns []
  (.dropdown ($ ".profile-dropdown-navbar-item"))
  (.dropdown ($ ".signin-dropdown-navbar-item"))
  (js/console.log "Activated dropdowns")
)

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))
       (activate-dropdowns)
      ))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app"))
  (activate-dropdowns)
)

(defn init! []
  (hook-browser-navigation!)
  (mount-root)
)
