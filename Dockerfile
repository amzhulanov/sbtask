FROM postgres:13
COPY create_database_ltm.sql /docker-entrypoint-initdb.d/


create_database_ltm.sql

CREATE DATABASE ltm;
CREATE USER ltmadmin WITH PASSWORD 'ltmadminpassword';
GRANT ALL PRIVILEGES ON DATABASE ltm TO ltmadmin;
\c ltm
CREATE SCHEMA ltm AUTHORIZATION ltmadmin;
SET search_path TO ltm;