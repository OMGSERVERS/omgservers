FROM alpine:latest
RUN apk update && apk add --no-cache curl jq bash
RUN adduser -D -s /bin/bash omgservers
COPY --chown=omgservers:omgservers src/main/docker/omgserversctl /home/omgservers/
RUN chmod +x /home/omgservers/omgserversctl
USER omgservers
WORKDIR /home/omgservers
CMD [ -n "${DELAY}" ] && echo "Waiting for ${DELAY} seconds, before start" && sleep ${DELAY}; \
    [ -n "${EXTERNAL_URL}" -o -n "${INTERNAL_URL}" ] && ./omgserversctl environment useEnvironment default ${EXTERNAL_URL} ${INTERNAL_URL}; \
    [ -n "${ADMIN_USER_ID}" -o -n "${ADMIN_PASSWORD}" ] && ./omgserversctl admin useCredentials ${ADMIN_USER_ID} ${ADMIN_PASSWORD}; \
    [ -n "${SUPPORT_USER_ID}" -o -n "${SUPPORT_PASSWORD}" ] && ./omgserversctl support useCredentials ${SUPPORT_USER_ID} ${SUPPORT_PASSWORD}; \
    [ -n "${DEVELOPER_USER_ID}" -o -n "${DEVELOPER_PASSWORD}" ] && ./omgserversctl developer useCredentials ${DEVELOPER_USER_ID} ${DEVELOPER_PASSWORD}; \
    tail -f /dev/null