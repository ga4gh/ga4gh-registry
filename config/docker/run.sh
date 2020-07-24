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
if [ -z "${AUTHSECRET}" ]; then echo "AUTHSECRET not set"; exit 1; fi;

python config/docker/configure.py

gradle
gradle wrapper
./gradlew wrapper
./gradlew build -x test
./gradlew bootRun --args="--spring.config.location=file:./config/docker/application.properties"
