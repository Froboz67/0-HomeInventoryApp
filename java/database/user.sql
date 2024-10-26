-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER home_inventory_owner
WITH PASSWORD 'inventory';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO home_inventory_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO home_inventory_owner;

CREATE USER home_inventory_appuser
WITH PASSWORD 'inventory';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO home_inventory_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO home_inventory_appuser;
