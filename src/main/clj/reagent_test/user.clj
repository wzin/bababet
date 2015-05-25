(ns clj.reagent-test.user
  (:require [yesql.core :refer [defqueries]]))

(defqueries "queries/user.sql")

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname "//localhost:5432/bababet_test"
              :user "bababet"})

(defrecord User [id login password email active?])

(defn- empty-user []
  (User. nil nil nil nil nil))

(defn- user-from-row [row]
  (merge (empty-user) row))

(defn get-users [db]
  (let [result-set (db-get-users db)]
    (map user-from-row result-set)))

(defn insert-user [db user]
  (db-insert-user! db (:login user) (:password user) (:email user) (:active? user)))

