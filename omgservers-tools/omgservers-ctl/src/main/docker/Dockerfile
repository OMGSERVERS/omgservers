FROM alpinelinux/docker-cli
RUN apk update \
    && apk add --no-cache curl jq bash zip \
    && mkdir -p /opt/omgservers

WORKDIR /opt/omgservers

COPY src/main/docker/scripts /opt/omgservers
RUN ls && chmod u+x *.sh

ARG OMGSERVERS_VERSION
ENV OMGSERVERS_VERSION=${OMGSERVERS_VERSION}

ENTRYPOINT ["./omgserversctl.sh"]