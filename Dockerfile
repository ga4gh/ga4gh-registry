FROM gradle:jdk11

USER root

WORKDIR /usr/src/app

RUN wget https://github.com/ga4gh/ga4gh-registry/archive/v0.2.4.tar.gz \
    && tar -zxvf v0.2.4.tar.gz \
    && wget https://github.com/swagger-api/swagger-ui/archive/v3.25.0.tar.gz \
    && tar -zxvf v3.25.0.tar.gz

WORKDIR /usr/src/app/ga4gh-registry-0.2.4

RUN mv src/main/resources/public/swagger/index.html . \
    && cp ../swagger-ui-3.25.0/dist/* src/main/resources/public/swagger/ \
    && mv ./index.html src/main/resources/public/swagger/

RUN gradle \
    && gradle wrapper \
    && ./gradlew wrapper \
    && ./gradlew build -x test

RUN chmod 755 config/docker/run.sh

CMD config/docker/run.sh
