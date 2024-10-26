-- **************************************************************
-- This script destroys the database and associated users
-- **************************************************************

-- The following line terminates any active connections to the database so that it can be destroyed
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'home_inventory';

DROP DATABASE home_inventory;

DROP USER home_inventory_owner;
DROP USER home_inventory_appuser;
