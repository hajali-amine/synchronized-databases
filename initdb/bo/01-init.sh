#!/bin/bash
set -e
export PGPASSWORD=$POSTGRES_PASSWORD;
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  BEGIN;
    CREATE TABLE product_sale(
                                 id SERIAL PRIMARY KEY NOT NULL,
                                 date DATE,
                                 region VARCHAR(255),
                                 product VARCHAR(255),
                                 qty INTEGER,
                                 cost FLOAT,
                                 amt REAL,
                                 tax FLOAT,
                                 total NUMERIC,
                                 sent BOOLEAN DEFAULT FALSE
    );
  COMMIT;
EOSQL