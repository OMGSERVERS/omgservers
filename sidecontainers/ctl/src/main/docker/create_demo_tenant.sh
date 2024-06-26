#!/bin/bash
set -e

./omgserversctl support createToken
./omgserversctl support createTenant

TENANT_ID=$(./omgserversctl environment printVariable TENANT_ID)
if [ -z "${TENANT_ID}" ]; then
  echo "TENANT_ID was not found"
  exit 1
fi

./omgserversctl support createProject ${TENANT_ID}

PROJECT_ID=$(./omgserversctl environment printVariable PROJECT_ID)
if [ -z "${PROJECT_ID}" ]; then
  echo "PROJECT_ID was not found"
  exit 1
fi

STAGE_ID=$(./omgserversctl environment printVariable STAGE_ID)
if [ -z "${STAGE_ID}" ]; then
  echo "STAGE_ID was not found"
  exit 1
fi

STAGE_SECRET=$(./omgserversctl environment printVariable STAGE_SECRET)
if [ -z "${STAGE_SECRET}" ]; then
  echo "STAGE_SECRET was not found"
  exit 1
fi

./omgserversctl support createDeveloper

DEVELOPER_USER_ID=$(./omgserversctl environment printVariable DEVELOPER_USER_ID)
if [ -z "${DEVELOPER_USER_ID}" ]; then
  echo "DEVELOPER_USER_ID was not found"
  exit 1
fi

DEVELOPER_PASSWORD=$(./omgserversctl environment printVariable DEVELOPER_PASSWORD)
if [ -z "${DEVELOPER_PASSWORD}" ]; then
  echo "DEVELOPER_PASSWORD was not found"
  exit 1
fi

./omgserversctl support createTenantPermission ${TENANT_ID} ${DEVELOPER_USER_ID} GET_DASHBOARD
./omgserversctl support createStagePermission ${TENANT_ID} ${STAGE_ID} ${DEVELOPER_USER_ID} VERSION_MANAGEMENT

./omgserversctl developer useCredentials ${DEVELOPER_USER_ID} ${DEVELOPER_PASSWORD}

echo
echo Demo tenant was created:
echo TENANT_ID=${TENANT_ID}
echo PROJECT_ID=${PROJECT_ID}
echo STAGE_ID=${STAGE_ID}
echo STAGE_SECRET=${STAGE_SECRET}
echo DEVELOPER_USER_ID=${DEVELOPER_USER_ID}
echo DEVELOPER_PASSWORD=${DEVELOPER_PASSWORD}