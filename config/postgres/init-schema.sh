#!/bin/bash
set -e

POSTGRES_USER=root
CONTAINER_NAME=postgres


docker exec -i $CONTAINER_NAME psql -U $POSTGRES_USER -d users <<'EOSQL'
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    reference_id VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);
EOSQL


docker exec -i $CONTAINER_NAME psql -U $POSTGRES_USER -d restaurants <<'EOSQL'
-- Create tables
CREATE TABLE IF NOT EXISTS cuisine (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS city (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS district (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    name VARCHAR(255) NOT NULL,
    city_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurant (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    name VARCHAR(255) NOT NULL UNIQUE,
    cuisine_id BIGINT NOT NULL,
    city_id BIGINT NOT NULL,
    district_id BIGINT NOT NULL,
    open_time TIME NOT NULL,
    close_time TIME NOT NULL,
    created_by VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurant_table (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    seat_count INT NOT NULL,
    restaurant_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    reference_no VARCHAR(255) NOT NULL,
    table_id BIGINT NOT NULL,
    date_time TIMESTAMP NOT NULL,
    end_date_time TIMESTAMP NOT NULL,
    duration_minutes BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    created_by VARCHAR(255) NOT NULL
);

-- Insert data for city
INSERT INTO city (name, created_at) VALUES
('Riyadh'), ('Jeddah'), ('Dammam'), ('Makkah');

INSERT INTO district (name, city_id, created_at) VALUES
('Olaya',     (SELECT id FROM city WHERE name = 'Riyadh')),
('Al-Malaz',  (SELECT id FROM city WHERE name = 'Riyadh')),
('Al-Narjis', (SELECT id FROM city WHERE name = 'Riyadh')),
('Al-Rimal',  (SELECT id FROM city WHERE name = 'Riyadh')),
('Al-Hamra',   (SELECT id FROM city WHERE name = 'Jeddah')),
('Al-Safa',    (SELECT id FROM city WHERE name = 'Jeddah')),
('Al-Rawdah',  (SELECT id FROM city WHERE name = 'Jeddah')),
('Al-Murjan',  (SELECT id FROM city WHERE name = 'Jeddah')),
('Al-Shati',     (SELECT id FROM city WHERE name = 'Dammam')),
('Al-Faisaliah', (SELECT id FROM city WHERE name = 'Dammam')),
('Al-Khalij',    (SELECT id FROM city WHERE name = 'Dammam')),
('Al-Mazruiyah', (SELECT id FROM city WHERE name = 'Dammam')),
('Al-Aziziyah',   (SELECT id FROM city WHERE name = 'Makkah')),
('Al-Shawqiyyah', (SELECT id FROM city WHERE name = 'Makkah')),
('Al-Nuzhah',     (SELECT id FROM city WHERE name = 'Makkah')),
('Al-Taneem',     (SELECT id FROM city WHERE name = 'Makkah'));


INSERT INTO cuisine (name, created_at) VALUES
('Saudi'), ('Italian'), ('Indian'), ('Chinese'), ('American');
EOSQL
