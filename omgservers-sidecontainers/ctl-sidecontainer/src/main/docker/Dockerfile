FROM alpinelinux/docker-cli
RUN apk update \
    && apk add --no-cache curl jq bash zip \
    && mkdir -p /opt/omgserversctl

WORKDIR /opt/omgserversctl
COPY src/main/docker/*.sh .
RUN chmod u+x *.sh
COPY src/main/docker/omgserversctl.sh .
RUN chmod u+x omgserversctl.sh
ENTRYPOINT ["./bootstrap.sh"]