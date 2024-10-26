#!/bin/bash
export PGPASSWORD='kevin'
BASEDIR=$(dirname $0)
DATABASE=home_inventory
psql -U postgres -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres $DATABASE &&
psql -U postgres -d $DATABASE -f "$BASEDIR/home_inventory_schema.sql" &&
psql -U postgres -d $DATABASE -f "$BASEDIR/data.sql" &&
psql -U postgres -d $DATABASE -f "$BASEDIR/user.sql"
