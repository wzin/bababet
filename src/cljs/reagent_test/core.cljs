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
     {:fact "Winner of presidential elections in Poland in April"
      :title "Who will win presidential elections?"
      :answers ["BK", "AD", "MO"]
      :solves_by 1428675249
      :creator "wojciech.ziniewicz@gmail.com"}
     {:fact "Winner party in parlamentary elections in Poland at 10.10.2015?"
      :solves_by 1428675249
      :title "Which party wins elections in Poland?"
      :answers ["PO", "PiS", "SLD"]
      :creator "Wojtek"}
     {:fact "Will Poland adopt Euro currency by 01.2016?"
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
    [:a {:href "#/finance"} "Finance"]]
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
       [:a {:href "#!" :class "brand-logo"} "Bababet"]
       [:ul {:class "right hide-on-med-and-down"}
        [:li
         [:a {:href "#/home"} "Home"]]
        [:li
         [:a {:href "#/bet"} "Bet"]]
        [:li
         [:a {:href "#!" :class "profile-dropdown-navbar-item" :data-activates "profile-dropdown" }
          "Profile"
          [:i {:class "mdi-navigation-arrow-drop-down right"}]]]
        [:li
         [:a {:href "#!" :class "signin-dropdown-navbar-item" :data-activates "signin-dropdown" }
          "Sign in"
          [:i {:class "mdi-navigation-arrow-drop-down right"}]]]
      ]
     ]
    ]
  ]
)

;;

(defn home-page []
  [:div
   [navbar]
   [:div [:a {:href "#/about"} "go to about page"]]
  ]
)

(defn about-page []
  [:div
   [navbar]
   [:div [:a {:href "#/"} "go to the home page"]]
  ]
)

(defn profile-page []
  [:div
   [navbar]
   [:div [:a {:href "#/"} "go to the home page"]]
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
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn activate-dropdowns []
  (.dropdown ($ ".profile-dropdown-navbar-item"))
  (.dropdown ($ ".signin-dropdown-navbar-item"))
             )

;;$(".profile-dropdown-navbar-item").dropdown()

(defn init! []
  (hook-browser-navigation!)
  (mount-root)
  (activate-dropdowns)
)
