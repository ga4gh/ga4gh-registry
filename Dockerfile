FROM gradle:jdk11

USER root

WORKDIR /usr/src/app

##################################################
# COPY REQUIRED DIRECTORIES
##################################################

COPY config config
COPY src src
COPY build.gradle build.gradle

##################################################
# GET SWAGGER UI, MOVE TO PUBLIC HTML FOLDER
##################################################

RUN wget https://github.com/swagger-api/swagger-ui/archive/v3.25.0.tar.gz \
    && tar -zxvf v3.25.0.tar.gz

RUN mv src/main/resources/public/swagger/index.html . \
    && cp swagger-ui-3.25.0/dist/* src/main/resources/public/swagger/ \
    && mv ./index.html src/main/resources/public/swagger/

##################################################
# RUN THE DOCKER CONTAINER WRAPPER SCRIPT
##################################################

RUN chmod 755 config/docker/run.sh

CMD config/docker/run.sh
