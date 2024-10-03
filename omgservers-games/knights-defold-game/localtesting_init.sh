#!/bin/bash
set -e

echo "Use local instance of service"

./omgserversctl.sh environment useLocal

echo "Create a new tenant"

./omgserversctl.sh support createToken
./omgserversctl.sh support createTenant

TENANT_ID=$(./omgserversctl.sh environment printVariable TENANT_ID)
if [ -z "${TENANT_ID}" ]; then
  echo "TENANT_ID was not found"
  exit 1
fi

echo "Create a new tenant project"

./omgserversctl.sh support createTenantProject ${TENANT_ID}

TENANT_PROJECT_ID=$(./omgserversctl.sh environment printVariable TENANT_PROJECT_ID)
if [ -z "${TENANT_PROJECT_ID}" ]; then
  echo "TENANT_PROJECT_ID was not found"
  exit 1
fi

TENANT_STAGE_ID=$(./omgserversctl.sh environment printVariable TENANT_STAGE_ID)
if [ -z "${TENANT_STAGE_ID}" ]; then
  echo "TENANT_STAGE_ID was not found"
  exit 1
fi

TENANT_STAGE_SECRET=$(./omgserversctl.sh environment printVariable TENANT_STAGE_SECRET)
if [ -z "${TENANT_STAGE_SECRET}" ]; then
  echo "TENANT_STAGE_SECRET was not found"
  exit 1
fi

echo "Create a new developer account"

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

echo "Configure permissions"

./omgserversctl.sh support createTenantPermission ${TENANT_ID} ${DEVELOPER_USER_ID} PROJECT_MANAGEMENT
./omgserversctl.sh support createTenantPermission ${TENANT_ID} ${DEVELOPER_USER_ID} GETTING_DASHBOARD
./omgserversctl.sh support createTenantProjectPermission ${TENANT_ID} ${TENANT_PROJECT_ID} ${DEVELOPER_USER_ID} STAGE_MANAGEMENT
./omgserversctl.sh support createTenantProjectPermission ${TENANT_ID} ${TENANT_PROJECT_ID} ${DEVELOPER_USER_ID} VERSION_MANAGEMENT
./omgserversctl.sh support createTenantProjectPermission ${TENANT_ID} ${TENANT_PROJECT_ID} ${DEVELOPER_USER_ID} GETTING_DASHBOARD
./omgserversctl.sh support createTenantStagePermission ${TENANT_ID} ${TENANT_STAGE_ID} ${DEVELOPER_USER_ID} DEPLOYMENT_MANAGEMENT
./omgserversctl.sh support createTenantStagePermission ${TENANT_ID} ${TENANT_STAGE_ID} ${DEVELOPER_USER_ID} GETTING_DASHBOARD

echo "Login using developer account"

./omgserversctl.sh developer useCredentials ${DEVELOPER_USER_ID} ${DEVELOPER_PASSWORD}

echo "Output tenant details"

echo TENANT_ID=${TENANT_ID}
echo TENANT_PROJECT_ID=${TENANT_PROJECT_ID}
echo TENANT_STAGE_ID=${TENANT_STAGE_ID}
echo TENANT_STAGE_SECRET=${TENANT_STAGE_SECRET}
echo DEVELOPER_USER_ID=${DEVELOPER_USER_ID}
echo DEVELOPER_PASSWORD=${DEVELOPER_PASSWORD}

echo "Store project localtesting config"

LOCALTESTING_CONFIG="./src/main/docker/knights-defold-game/game/localtesting.lua"
cat > ${LOCALTESTING_CONFIG} << EOF
return {
  tenant_id = "${TENANT_ID}",
  tenant_stage_id = "${TENANT_STAGE_ID}",
  tenant_stage_secret = "${TENANT_STAGE_SECRET}",
}
EOF
echo "Project file ${LOCALTESTING_CONFIG} was written"
cat ${LOCALTESTING_CONFIG}

echo "All is done"