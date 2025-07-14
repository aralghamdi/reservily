#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
  CREATE DATABASE users;
  CREATE DATABASE restaurants;
  CREATE DATABASE keycloak;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
  CREATE USER users_db_admin WITH PASSWORD 'users_db_admin';
  CREATE USER restaurants_db_admin WITH PASSWORD 'restaurants_db_admin';
  CREATE USER keycloak_db_admin WITH PASSWORD 'keycloak_db_admin';

  GRANT ALL PRIVILEGES ON DATABASE users TO users_db_admin;
  GRANT ALL PRIVILEGES ON DATABASE restaurants TO restaurants_db_admin;
  GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak_db_admin;
EOSQL

for DB in users restaurants keycloak; do
  USER="${DB}_db_admin"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname="$DB" <<-EOSQL
    ALTER SCHEMA public OWNER TO $USER;
    GRANT ALL ON SCHEMA public TO $USER;
  EOSQL
done