FROM alpine:latest
RUN apk update \
    && apk add --no-cache curl jq bash zip \
    && mkdir -p /opt/omgserversctl
WORKDIR /opt/omgserversctl
COPY src/main/docker/*.sh .
RUN chmod u+x *.sh
COPY src/main/docker/omgserversctl .
RUN chmod u+x omgserversctl
ENTRYPOINT ["./bootstrap.sh"]