#!/bin/bash

# change postgres user password
psql -U postgres -c "alter user postgres with password 'password'" postgres
# create hirarin schema
psql -U postgres -c "CREATE SCHEMA hirarin AUTHORIZATION postgres;" postgres
# set search_path
psql -U postgres -c 'alter user postgres set search_path to hirarin,"$user",public' postgres
# create tables to hirarin schema
psql -U postgres -f /tmp/create-tables.sql postgres
