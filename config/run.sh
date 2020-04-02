#!/bin/bash

if [ -z "${SERVERCONTEXTPATH}" ]; then echo "SERVERCONTEXTPATH not set"; exit 1; fi;
if [ -z "${SERVERPORT}" ]; then echo "SERVERPORT not set"; exit 1; fi;
if [ -z "${DBHOST}" ]; then echo "DBHOST not set"; exit 1; fi;
if [ -z "${DBPORT}" ]; then echo "DBPORT not set"; exit 1; fi;
if [ -z "${DBNAME}" ]; then echo "DBNAME not set"; exit 1; fi;
if [ -z "${DBSCHEMA}" ]; then echo "DBSCHEMA not set"; exit 1; fi;
if [ -z "${DBUSER}" ]; then echo "DBUSER not set"; exit 1; fi;
if [ -z "${DBPASSWORD}" ]; then echo "DBPASSWORD not set"; exit 1; fi;
if [ -z "${USESSL}" ]; then echo "USESSL not set"; exit 1; fi;

cat config/application.properties.template | \
sed "s/VAR_SERVERCONTEXTPATH/${SERVERCONTEXTPATH}/g" | \
sed "s/VAR_SERVERPORT/${SERVERPORT}/g" | \
sed "s/VAR_DBHOST/${DBHOST}/g" | \
sed "s/VAR_DBPORT/${DBPORT}/g" | \
sed "s/VAR_DBNAME/${DBNAME}/g" | \
sed "s/VAR_DBSCHEMA/${DBSCHEMA}/g" | \
sed "s/VAR_DBUSER/${DBUSER}/g" | \
sed "s/VAR_DBPASSWORD/${DBPASSWORD}/g" \
sed "s/VAR_USESSL/${USESSL}/g" \
> config/application.properties

tail -f /dev/null
