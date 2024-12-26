#!/bin/bash
set -e

echo "Use local instance of service"

./omgserversctl.sh environment useLocal

echo "Create a new tenant"

./omgserversctl.sh support createToken
./omgserversctl.sh support createTenant

TENANT=$(./omgserversctl.sh environment printVariable TENANT)
if [ -z "${TENANT}" ]; then
  echo "TENANT was not found"
  exit 1
fi

echo "Create a new project"

./omgserversctl.sh support createProject ${TENANT}

PROJECT=$(./omgserversctl.sh environment printVariable PROJECT)
if [ -z "${PROJECT}" ]; then
  echo "PROJECT was not found"
  exit 1
fi

STAGE=$(./omgserversctl.sh environment printVariable STAGE)
if [ -z "${STAGE}" ]; then
  echo "STAGE was not found"
  exit 1
fi

SECRET=$(./omgserversctl.sh environment printVariable SECRET)
if [ -z "${SECRET}" ]; then
  echo "SECRET was not found"
  exit 1
fi

echo "Create a new developer account"

./omgserversctl.sh support createDeveloper

DEVELOPER_USER=$(./omgserversctl.sh environment printVariable DEVELOPER_USER)
if [ -z "${DEVELOPER_USER}" ]; then
  echo "DEVELOPER_USER was not found"
  exit 1
fi

DEVELOPER_PASSWORD=$(./omgserversctl.sh environment printVariable DEVELOPER_PASSWORD)
if [ -z "${DEVELOPER_PASSWORD}" ]; then
  echo "DEVELOPER_PASSWORD was not found"
  exit 1
fi

echo "Configure permissions"

./omgserversctl.sh support createTenantPermission ${TENANT} ${DEVELOPER_USER} PROJECT_MANAGER
./omgserversctl.sh support createTenantPermission ${TENANT} ${DEVELOPER_USER} TENANT_VIEWER
./omgserversctl.sh support createProjectPermission ${TENANT} ${PROJECT} ${DEVELOPER_USER} STAGE_MANAGER
./omgserversctl.sh support createProjectPermission ${TENANT} ${PROJECT} ${DEVELOPER_USER} VERSION_MANAGER
./omgserversctl.sh support createProjectPermission ${TENANT} ${PROJECT} ${DEVELOPER_USER} PROJECT_VIEWER
./omgserversctl.sh support createStagePermission ${TENANT} ${STAGE} ${DEVELOPER_USER} DEPLOYMENT_MANAGER
./omgserversctl.sh support createStagePermission ${TENANT} ${STAGE} ${DEVELOPER_USER} STAGE_VIEWER

echo "Login using developer account"

./omgserversctl.sh developer useCredentials ${DEVELOPER_USER} ${DEVELOPER_PASSWORD}

echo "Output tenant details"

echo TENANT=${TENANT}
echo PROJECT=${PROJECT}
echo STAGE=${STAGE}
echo SECRET=${SECRET}
echo DEVELOPER_USER=${DEVELOPER_USER}
echo DEVELOPER_PASSWORD=${DEVELOPER_PASSWORD}

echo "Store project localtesting config"

CONFIG="./src/main/docker/knights-defold-game/game/localtesting.lua"
cat > ${CONFIG} << EOF
return {
  tenant = "${TENANT}",
  stage = "${STAGE}",
  secret = "${SECRET}",
}
EOF
echo "Project file ${CONFIG} was written"
cat ${CONFIG}

echo "All is done"