#!/bin/bash
set -euo pipefail

# Initialization script to run in the official Postgres Docker image
# This script will create an additional database named `finance_db` and
# create a sample `accounts` table with a couple of seed rows.

# Environment variables provided by the postgres image:
# POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_DB
: "${POSTGRES_USER:=postgres}"
: "${POSTGRES_DB:=mydb}"

echo "[init] Running create_postgres_db.sh as user=${POSTGRES_USER}, default db=${POSTGRES_DB}"

# Check if finance_db exists; if not, create it.
EXIST=$(psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -tAc "SELECT 1 FROM pg_database WHERE datname = 'finance_db';" || true)
if [ "${EXIST}" != "1" ]; then
  echo "[init] Creating database: finance_db"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -c "CREATE DATABASE finance_db;"
else
  echo "[init] Database finance_db already exists; skipping create"
fi

# Create sample schema and seed data in finance_db
cat <<'EOSQL' | psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname finance_db
-- Create accounts table if it doesn't exist
CREATE TABLE IF NOT EXISTS accounts (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  balance NUMERIC(12,2) DEFAULT 0
);

-- Insert seed rows if they don't already exist
INSERT INTO accounts (name, balance)
SELECT 'Checking', 1000.00
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE name = 'Checking');

INSERT INTO accounts (name, balance)
SELECT 'Savings', 5000.00
WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE name = 'Savings');
EOSQL

echo "[init] Initialization complete"
