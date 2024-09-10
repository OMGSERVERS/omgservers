#!/bin/bash
set -e

echo "Use local instance of service"

./omgserversctl.sh environment useLocal

echo "Get tenant details"

TENANT_ID=$(./omgserversctl.sh environment printVariable TENANT_ID)
PROJECT_ID=$(./omgserversctl.sh environment printVariable PROJECT_ID)
STAGE_ID=$(./omgserversctl.sh environment printVariable STAGE_ID)
DEVELOPER_USER_ID=$(./omgserversctl.sh environment printVariable DEVELOPER_USER_ID)
DEVELOPER_PASSWORD=$(./omgserversctl.sh environment printVariable DEVELOPER_PASSWORD)

if [ -z "${TENANT_ID}" -o -z "${PROJECT_ID}" -o -z "${STAGE_ID}" -o -z "${DEVELOPER_USER_ID}" -o -z "${DEVELOPER_PASSWORD}" ]; then
  echo "Tenant was not initialized, use ./localtesting_init.sh first"
  exit 1
fi

echo TENANT_ID=${TENANT_ID}
echo PROJECT_ID=${PROJECT_ID}
echo STAGE_ID=${STAGE_ID}
echo DEVELOPER_USER_ID=${DEVELOPER_USER_ID}
echo DEVELOPER_PASSWORD=${DEVELOPER_PASSWORD}

echo "Login using developer account"

./omgserversctl.sh developer createToken

echo "Create a new version"

./omgserversctl.sh developer createVersion ${TENANT_ID} ${STAGE_ID} "./src/main/docker/knights-defold-game/config.json"
VERSION_ID=$(./omgserversctl.sh environment printVariable VERSION_ID)
if [ -z "${VERSION_ID}" -o "${VERSION_ID}" == "null" ]; then
  echo "ERROR: VERSION_ID was not received"
  exit 1
fi

echo "Push docker image"

IMAGE_ID="omgservers/knights-defold-game:1.0.0-SNAPSHOT"
TARGET_IMAGE_ID="localhost:5000/omgservers/${TENANT_ID}/${PROJECT_ID}/${STAGE_ID}/universal:${VERSION_ID}"
docker login -u ${DEVELOPER_USER_ID} -p ${DEVELOPER_PASSWORD} localhost:5000
docker tag ${IMAGE_ID} ${TARGET_IMAGE_ID}
docker push ${TARGET_IMAGE_ID}

echo "Request version deployment"

./omgserversctl.sh developer deployVersion ${TENANT_ID} ${VERSION_ID}

echo "All is done"