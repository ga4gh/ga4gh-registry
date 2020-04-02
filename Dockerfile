FROM gradle:jdk11

WORKDIR /usr/src/app

RUN wget https://github.com/ga4gh/ga4gh-registry/archive/v0.2.2.tar.gz \
    && tar -zxvf v0.2.2.tar.gz \
    && wget https://github.com/swagger-api/swagger-ui/archive/v3.25.0.tar.gz \
    && tar -zxvf v3.25.0.tar.gz

WORKDIR /usr/src/app/ga4gh-registry-0.2.2

RUN chmod 755 config/docker/run.sh

RUN mv src/main/resources/public/swagger/index.html . \
    && cp swagger-ui-3.25.0/dist/* src/main/resources/public/swagger/ \
    && mv ./index.html src/main/resources/public/swagger/

CMD config/docker/run.sh
