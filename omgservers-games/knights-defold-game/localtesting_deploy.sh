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

echo "Login using developer account"

./omgserversctl.sh developer createToken

echo "Create a new version"

./omgserversctl.sh developer createTenantVersion ${TENANT_ID} ${TENANT_PROJECT_ID} "./src/main/docker/knights-defold-game/config.json"
TENANT_VERSION_ID=$(./omgserversctl.sh environment printVariable TENANT_VERSION_ID)
if [ -z "${TENANT_VERSION_ID}" -o "${TENANT_VERSION_ID}" == "null" ]; then
  echo "ERROR: TENANT_VERSION_ID was not received"
  exit 1
fi

echo "Push docker image"

IMAGE_ID="omgservers/knights-defold-game:1.0.0-SNAPSHOT"
TARGET_IMAGE_ID="localhost:5000/omgservers/${TENANT_ID}/${TENANT_PROJECT_ID}/universal:${TENANT_VERSION_ID}"
docker login -u ${DEVELOPER_USER_ID} -p ${DEVELOPER_PASSWORD} localhost:5000
docker tag ${IMAGE_ID} ${TARGET_IMAGE_ID}
docker push ${TARGET_IMAGE_ID}

echo "Deploy a new version"

./omgserversctl.sh developer deployTenantVersion ${TENANT_ID} ${TENANT_STAGE_ID} ${TENANT_VERSION_ID}
TENANT_DEPLOYMENT_ID=$(./omgserversctl.sh environment printVariable TENANT_DEPLOYMENT_ID)
if [ -z "${TENANT_DEPLOYMENT_ID}" -o "${TENANT_DEPLOYMENT_ID}" == "null" ]; then
  echo "ERROR: TENANT_DEPLOYMENT_ID was not received"
  exit 1
fi

echo "All is done"