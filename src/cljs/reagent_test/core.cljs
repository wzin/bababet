(ns reagent-test.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [cljsjs.react :as react])
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

;; -------------------------
;; Views
;;    <nav>
;;    <div class="nav-wrapper">
;;      <a href="#" class="brand-logo">Logo</a>
;;      <ul id="nav-mobile" class="right hide-on-med-and-down">
;;        <li><a href="sass.html">Sass</a></li>
;;        <li><a href="components.html">Components</a></li>
;;        <li><a href="javascript.html">JavaScript</a></li>
;;      </ul>
;;    </div>
;;    </nav>

(defn home-page []
  [:div [:h2 "Landing page"]
   [:div [:a {:href "#/about"} "go to about page"]]])

(defn about-page []
  [:div [:h2 "About page"]
   [:div [:a {:href "#/"} "go to the home page"]]])

(defn profile-page []
  [:div [:h2 "Profile page"]
   [:div [:a {:href "#/"} "go to the home page"]]])

(defn current-page []
  [:div [(session/get :current-page)]])

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

(defn init! []
  (hook-browser-navigation!)
  (mount-root))
