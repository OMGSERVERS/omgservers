FROM alpinelinux/docker-cli
RUN apk update \
    && apk add --no-cache curl jq bash zip \
    && mkdir -p /opt/omgserversctl

WORKDIR /opt/omgserversctl

ARG OMGSERVERS_VERSION
ENV OMGSERVERS_VERSION=${OMGSERVERS_VERSION}

COPY src/main/docker/omgserversctl.sh .
RUN chmod u+x omgserversctl.sh
ENTRYPOINT ["./omgserversctl.sh"]