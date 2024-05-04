-- **************************************************************
-- This script destroys the database and associated users
-- **************************************************************

-- The following line terminates any active connections to the database so that it can be destroyed
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'put_scanner';

DROP DATABASE put_scanner;

DROP USER put_scanner_owner;
DROP USER put_scanner_appuser;
