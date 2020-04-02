FROM gradle:jdk11

WORKDIR /usr/src/app

RUN wget https://github.com/ga4gh/ga4gh-registry/archive/v0.2.1.tar.gz \
    && tar -zxvf v0.2.1.tar.gz \
    && wget https://github.com/swagger-api/swagger-ui/archive/v3.25.0.tar.gz \
    && tar -zxvf v3.25.0.tar.gz

WORKDIR /usr/src/app/ga4gh-registry-0.2.1

RUN mv src/main/resources/public/swagger/index.html . \
    && cp ../swagger-ui-3.25.0/dist/* src/main/resources/public/swagger/ \
    && mv ./index.html src/main/resources/public/swagger/

CMD tail -f /dev/null
