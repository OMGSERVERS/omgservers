#!/bin/bash
set -e

echo "Use local instance of service"

./omgserversctl.sh environment useLocal

echo "Get tenant details"

TENANT=$(./omgserversctl.sh environment printVariable TENANT)
PROJECT=$(./omgserversctl.sh environment printVariable PROJECT)
STAGE=$(./omgserversctl.sh environment printVariable STAGE)
DEVELOPER_USER=$(./omgserversctl.sh environment printVariable DEVELOPER_USER)
DEVELOPER_PASSWORD=$(./omgserversctl.sh environment printVariable DEVELOPER_PASSWORD)

if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" -o -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
  echo "Tenant was not initialized, use ./localtesting_init.sh first"
  exit 1
fi

echo TENANT=${TENANT}
echo PROJECT=${PROJECT}
echo STAGE=${STAGE}
echo DEVELOPER_USER=${DEVELOPER_USER}
echo DEVELOPER_PASSWORD=${DEVELOPER_PASSWORD}