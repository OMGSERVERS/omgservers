#!/bin/bash
set -e

if [ -n "${DELAY}" ]; then
  echo "Waiting for ${DELAY} seconds, before start" && sleep ${DELAY}
fi

if [ -n "${EXTERNAL_URL}" -o -n "${INTERNAL_URL}" ]; then
   ./omgserversctl.sh environment useEnvironment default ${EXTERNAL_URL} ${INTERNAL_URL}; \
fi

if [ -n "${ADMIN_USER_ID}" -o -n "${ADMIN_PASSWORD}" ]; then
  ./omgserversctl.sh admin useCredentials ${ADMIN_USER_ID} ${ADMIN_PASSWORD}; \
fi

if [ -n "${SUPPORT_USER_ID}" -o -n "${SUPPORT_PASSWORD}" ]; then
  ./omgserversctl.sh support useCredentials ${SUPPORT_USER_ID} ${SUPPORT_PASSWORD}; \
fi

if [ -n "${DEVELOPER_USER_ID}" -o -n "${DEVELOPER_PASSWORD}" ]; then
 ./omgserversctl.sh developer useCredentials ${DEVELOPER_USER_ID} ${DEVELOPER_PASSWORD}
fi

if [ -n "${BACKGROUND}" ]; then
  tail -f /dev/null
else
  ./omgserversctl.sh $@
fi
