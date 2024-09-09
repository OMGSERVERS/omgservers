#!/bin/bash
set -e

./omgserversctl.sh environment useLocal

./omgserversctl.sh support createToken
./omgserversctl.sh support createTenant

TENANT_ID=$(./omgserversctl.sh environment printVariable TENANT_ID)
if [ -z "${TENANT_ID}" ]; then
  echo "TENANT_ID was not found"
  exit 1
fi

./omgserversctl.sh support createProject ${TENANT_ID}

PROJECT_ID=$(./omgserversctl.sh environment printVariable PROJECT_ID)
if [ -z "${PROJECT_ID}" ]; then
  echo "PROJECT_ID was not found"
  exit 1
fi

STAGE_ID=$(./omgserversctl.sh environment printVariable STAGE_ID)
if [ -z "${STAGE_ID}" ]; then
  echo "STAGE_ID was not found"
  exit 1
fi

STAGE_SECRET=$(./omgserversctl.sh environment printVariable STAGE_SECRET)
if [ -z "${STAGE_SECRET}" ]; then
  echo "STAGE_SECRET was not found"
  exit 1
fi

./omgserversctl.sh support createDeveloper

DEVELOPER_USER_ID=$(./omgserversctl.sh environment printVariable DEVELOPER_USER_ID)
if [ -z "${DEVELOPER_USER_ID}" ]; then
  echo "DEVELOPER_USER_ID was not found"
  exit 1
fi

DEVELOPER_PASSWORD=$(./omgserversctl.sh environment printVariable DEVELOPER_PASSWORD)
if [ -z "${DEVELOPER_PASSWORD}" ]; then
  echo "DEVELOPER_PASSWORD was not found"
  exit 1
fi

./omgserversctl.sh support createTenantPermission ${TENANT_ID} ${DEVELOPER_USER_ID} PROJECT_MANAGEMENT
./omgserversctl.sh support createTenantPermission ${TENANT_ID} ${DEVELOPER_USER_ID} GETTING_DASHBOARD
./omgserversctl.sh support createProjectPermission ${TENANT_ID} ${PROJECT_ID} ${DEVELOPER_USER_ID} STAGE_MANAGEMENT
./omgserversctl.sh support createStagePermission ${TENANT_ID} ${STAGE_ID} ${DEVELOPER_USER_ID} VERSION_MANAGEMENT
./omgserversctl.sh support createStagePermission ${TENANT_ID} ${STAGE_ID} ${DEVELOPER_USER_ID} GETTING_DASHBOARD

./omgserversctl.sh developer useCredentials ${DEVELOPER_USER_ID} ${DEVELOPER_PASSWORD}

echo
echo Tenant was initialized:
echo TENANT_ID=${TENANT_ID}
echo PROJECT_ID=${PROJECT_ID}
echo STAGE_ID=${STAGE_ID}
echo STAGE_SECRET=${STAGE_SECRET}
echo DEVELOPER_USER_ID=${DEVELOPER_USER_ID}
echo DEVELOPER_PASSWORD=${DEVELOPER_PASSWORD}

LOCALTESTING_CONFIG="./knights-defold-game/main/localtesting.lua"
cat > ${LOCALTESTING_CONFIG} << EOF
return {
  tenant_id = "${TENANT_ID}",
  stage_id = "${STAGE_ID}",
  stage_secret = "${STAGE_SECRET}",
}
EOF
echo "Project file ${LOCALTESTING_CONFIG} was written"
cat ${LOCALTESTING_CONFIG}