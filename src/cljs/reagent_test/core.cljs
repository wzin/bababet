(ns reagent-test.core
  (:require
            [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [jayq.core :as jayq.core]
            [reagent-test.pages.landing :as landing]
            [reagent-test.pages.signin :as signin]
            [reagent-test.pages.signup :as signup]
            [reagent-test.pages.propose :as propose]
            [reagent-test.pages.profile :as profile]
            [reagent-test.pages.finances :as finances]
            [reagent-test.pages.bet :as bet]
            [reagent-test.router :as router]
            [reagent-test.db :as db]
            [cljsjs.react :as react])
  (:use     [jayq.core :only [$]])
  (:import goog.History))

(enable-console-print!)

;; -------------------------
;; Routes

(defn current-page []
  [:div [(session/get :current-page)]]
   )

(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :current-page #'landing/landing-page-content))

(secretary/defroute "/signin" []
  (session/put! :current-page #'signin/signin-page-content))

(secretary/defroute "/signup" []
  (session/put! :current-page #'signup/signup-page-content))

(secretary/defroute "/propose" []
  (session/put! :current-page #'propose/propose-page-content))

(secretary/defroute "/profile" []
  (session/put! :current-page #'profile/profile-page-content))

(secretary/defroute "/finances" []
  (session/put! :current-page #'finances/finances-page-content))

(secretary/defroute bet-route "/bet/:id" {id :id}
  (session/put! :current-page #'bet/bet-page-content)
  (js/console.log (str "Bet: " id))
  )

;; -------------------------
;; History
;; must be called after routes have been defined

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))
      ))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app"))
)

(defn init! []
  (hook-browser-navigation!)
  (mount-root)
)
