-- name: db-get-users
-- Fetches all users from database
SELECT * from "user";

-- name: db-insert-user!
-- Inserts new user
INSERT INTO "user" (login, password, email, active) VALUES (:login, :password, :email, :active);

