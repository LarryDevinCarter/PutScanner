-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER put_scanner_owner
WITH PASSWORD 'djml87890921';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO put_scanner_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO put_scanner_owner;

CREATE USER put_scanner_appuser
WITH PASSWORD 'djml87890921';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO put_scanner_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO put_scanner_appuser;