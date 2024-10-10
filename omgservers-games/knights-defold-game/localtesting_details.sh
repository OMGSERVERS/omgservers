#!/bin/bash
set -e

echo "Use local instance of service"

./omgserversctl.sh environment useLocal

echo "Get tenant details"

TENANT_ID=$(./omgserversctl.sh environment printVariable TENANT_ID)
TENANT_PROJECT_ID=$(./omgserversctl.sh environment printVariable TENANT_PROJECT_ID)
TENANT_STAGE_ID=$(./omgserversctl.sh environment printVariable TENANT_STAGE_ID)
DEVELOPER_USER_ID=$(./omgserversctl.sh environment printVariable DEVELOPER_USER_ID)
DEVELOPER_PASSWORD=$(./omgserversctl.sh environment printVariable DEVELOPER_PASSWORD)

if [ -z "${TENANT_ID}" -o -z "${TENANT_PROJECT_ID}" -o -z "${TENANT_STAGE_ID}" -o -z "${DEVELOPER_USER_ID}" -o -z "${DEVELOPER_PASSWORD}" ]; then
  echo "Tenant was not initialized, use ./localtesting_init.sh first"
  exit 1
fi

echo TENANT_ID=${TENANT_ID}
echo TENANT_PROJECT_ID=${TENANT_PROJECT_ID}
echo TENANT_STAGE_ID=${TENANT_STAGE_ID}
echo DEVELOPER_USER_ID=${DEVELOPER_USER_ID}
echo DEVELOPER_PASSWORD=${DEVELOPER_PASSWORD}