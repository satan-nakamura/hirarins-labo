#!/bin/bash

function start_rmiregistry() {
    rmiregistry &
}

function start_batch_client() {
    java -cp /opt/apache-tomcat-6.0.53/webapps/legacy-app/WEB-INF/classes/:/opt/apache-tomcat-6.0.53/webapps/legacy-app/WEB-INF/batch/:/opt/apache-tomcat-6.0.53/webapps/legacy-app/WEB-INF/lib/log4j-1.2.17.jar:/opt/apache-tomcat-6.0.53/lib/postgresql-8.1-415.jdbc3.jar labo.hirarins.legacy.app.batch.Client &
}

function start_rmiserver() {
    java -cp /opt/apache-tomcat-6.0.53/webapps/legacy-app/WEB-INF/classes/:/opt/apache-tomcat-6.0.53/webapps/legacy-app/WEB-INF/batch/:/opt/apache-tomcat-6.0.53/webapps/legacy-app/WEB-INF/lib/log4j-1.2.17.jar:/opt/apache-tomcat-6.0.53/lib/postgresql-8.1-415.jdbc3.jar -Djava.rmi.server.codebase=file:///opt/apache-tomcat-6.0.53/webapps/legacy-app/WEB-INF/classes/ labo.hirarins.legacy.app.batch.Server &
}

start_rmiregistry
sleep 3

start_rmiserver
sleep 3

start_batch_client
sleep 3

/opt/apache-tomcat-6.0.53/bin/catalina.sh run
