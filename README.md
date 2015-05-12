# Bababet

## Requirements

- Java 7+
- leiningen

## First run

```
lein deps
```

## Running

Prepare two shell sessions:

- first - figwheel dev environment
```shell
lein fighweel
```
- second - application itself
```shell
lein run
```

Open following link in browser: http://localhost:3000

## Installing postgres

```shell
# install postgres
brew install postgres
# create user
createuser -d -P bababet # [Password: bababet]
# create database for tests
createdb -E utf8 -O bababet bababet_test
# check
psql -l
# connect
psql bababet
# connect via TCP
psql -h localhost -p 5432 bababet
```

## Creating new migrationb

```clojure
;; in REPL
(require '[joplin.core :as joplin])

(require '[joplin.jdbc.database])

(def target {:db {:type :sql
                  :url "jdbc:postgresql://localhost:5432/bababet_test?user=bababet&password=bababet"}
				  :migrator "migrations"})

(joplin/create-migration target "create-user-table")
```
