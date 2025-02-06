FROM docker:dind

RUN apk add --no-cache docker-cli  \
    && mkdir -p /opt/omgservers

WORKDIR /opt/omgservers
COPY src/main/docker/configs /opt/omgservers/configs
COPY src/main/docker/scripts /opt/omgservers
RUN chmod u+x *.sh

ARG OMGSERVERS_VERSION
ENV OMGSERVERS_VERSION=${OMGSERVERS_VERSION}

ENTRYPOINT ["./entrypoint.sh"]