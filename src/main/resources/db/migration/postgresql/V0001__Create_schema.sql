SET search_path = public;

CREATE SCHEMA IF NOT EXISTS operations;

SET search_path = operations,public;

DO $$
    BEGIN
        IF NOT EXISTS(
                SELECT *
                FROM pg_catalog.pg_user
                WHERE usename = 'operations')
        THEN
            CREATE ROLE operations LOGIN PASSWORD 'operations';
        END IF;
    END
    $$;

ALTER USER operations SET search_path TO 'operations';

GRANT USAGE ON SCHEMA operations TO operations;
