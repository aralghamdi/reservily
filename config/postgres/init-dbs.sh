#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
  -- Step 1: Create the users first
  CREATE USER users_db_admin WITH PASSWORD 'users_db_admin';
  CREATE USER restaurants_db_admin WITH PASSWORD 'restaurants_db_admin';
  CREATE USER keycloak_db_admin WITH PASSWORD 'keycloak_db_admin';
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
  -- Step 2: Create the databases with their respective owners
  CREATE DATABASE users OWNER users_db_admin;
  CREATE DATABASE restaurants OWNER restaurants_db_admin;
  CREATE DATABASE keycloak OWNER keycloak_db_admin;
EOSQL


for DB in users restaurants keycloak; do
USER="${DB}_db_admin"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname="$DB" <<EOSQL
  ALTER SCHEMA public OWNER TO $USER;
  GRANT ALL ON SCHEMA public TO $USER;
  GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO $USER;
  GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO $USER;
  ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO $USER;
  ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO $USER;
EOSQL
done