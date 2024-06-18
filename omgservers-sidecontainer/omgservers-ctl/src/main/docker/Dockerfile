FROM alpine:latest
RUN apk update && apk add --no-cache curl jq bash
RUN adduser -D -s /bin/bash omgservers
COPY --chown=omgservers:omgservers src/main/docker/omgserversctl /home/omgservers/
RUN chmod +x /home/omgservers/omgserversctl
COPY --chown=omgservers:omgservers src/main/docker/bootstrap.sh /home/omgservers/
RUN chmod +x /home/omgservers/bootstrap.sh
USER omgservers
WORKDIR /home/omgservers
ENTRYPOINT ["./bootstrap.sh"]