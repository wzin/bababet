(ns clj.reagent-test.user
  (:require [yesql.core :refer [defqueries]]))

(defqueries "queries/user.sql")

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname "//localhost:5432/bababet_test"
              :user "bababet"})

(defrecord User [id login password email active?])

(def a 1)
