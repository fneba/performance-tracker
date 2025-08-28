-- Runs automatically on first container start (new volume)
-- Creates a dedicates app user and ensures the db exists and is owned

CREATE USER sports_user WITH PASSWORD 'mypassword';
CREATE DATABASE sports_dev OWNER sports_user;
\connect sports_dev
ALTER SCHEMA public OWNER TO sports_user;
GRANT ALL ON SCHEMA public TO sports_user;
