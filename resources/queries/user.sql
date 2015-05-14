-- name: get-users
-- Fetches all users from database
SELECT * from "user";

-- name: create-user!
-- Inserts new user
INSERT INTO "user" (login, password, email, active) VALUES (:login, :password, :email, :active);
