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

echo "Login using developer account"

./omgserversctl.sh developer createToken

echo "Create a new version"

./omgserversctl.sh developer createVersion ${TENANT} ${PROJECT} "./src/main/docker/knights-defold-game/config.json"
VERSION=$(./omgserversctl.sh environment printVariable VERSION)
if [ -z "${VERSION}" -o "${VERSION}" == "null" ]; then
  echo "ERROR: VERSION was not received"
  exit 1
fi

echo "Push docker image"

IMAGE="omgservers/knights-defold-game:1.0.0-SNAPSHOT"
TARGET_IMAGE="localhost:5000/omgservers/${TENANT}/${PROJECT}/universal:${VERSION}"
docker login -u ${DEVELOPER_USER} -p ${DEVELOPER_PASSWORD} localhost:5000
docker tag ${IMAGE} ${TARGET_IMAGE}
docker push ${TARGET_IMAGE}

echo "Deploy a new version"

./omgserversctl.sh developer deployVersion ${TENANT} ${STAGE} ${VERSION}
DEPLOYMENT=$(./omgserversctl.sh environment printVariable DEPLOYMENT)
if [ -z "${DEPLOYMENT}" -o "${DEPLOYMENT}" == "null" ]; then
  echo "ERROR: DEPLOYMENT was not received"
  exit 1
fi

echo "All is done"