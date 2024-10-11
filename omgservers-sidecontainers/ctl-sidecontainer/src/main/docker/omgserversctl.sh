#!/bin/bash
set -e

# HELP

help() {
  echo "OMGSERVERS ctl, v1.0.0"
  echo "Usage:"
  if [ -z "$1" -o "$1" = "help" ]; then
    echo " omgserversctl help"
  fi
  if [ -z "$1" -o "$1" = "logs" ]; then
    echo " omgserversctl logs"
  fi
  # Environment
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment printCurrent" ]; then
    echo " omgserversctl environment printCurrent"
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment printVariable" ]; then
    echo " omgserversctl environment printVariable <variable_name>"
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment useEnvironment" ]; then
    echo " omgserversctl environment useEnvironment <name> <external_url> <internal_url>"
    if [ "$1" = "environment useEnvironment" ]; then
      echo "   produces:"
      echo "     - ENVIRONMENT_NAME"
      echo "     - EXTERNAL_URL"
      echo "     - INTERNAL_URL"
    fi
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment usePublic" ]; then
    echo " omgserversctl environment usePublic"
    if [ "$1" = "environment usePublic" ]; then
      echo "   produces:"
      echo "     - ENVIRONMENT_NAME"
      echo "     - EXTERNAL_URL"
      echo "     - INTERNAL_URL"
    fi
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment useLocal" ]; then
    echo " omgserversctl environment useLocal"
    if [ "$1" = "environment useLocal" ]; then
      echo "   produces:"
      echo "     - ENVIRONMENT_NAME"
      echo "     - EXTERNAL_URL"
      echo "     - INTERNAL_URL"
    fi
  fi
  # Admin
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin useCredentials" ]; then
    echo " omgserversctl admin useCredentials <user_id> <password>"
    if [ "$1" = "admin useCredentials" ]; then
      echo "   produces:"
      echo "     - ADMIN_USER_ID"
      echo "     - ADMIN_PASSWORD"
    fi
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin printCurrent" ]; then
    echo " omgserversctl admin printCurrent"
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin createToken" ]; then
    echo " omgserversctl admin createToken"
    if [ "$1" = "admin createToken" ]; then
      echo "   produces:"
      echo "     - ADMIN_TOKEN"
    fi
  fi
  # Support
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support useCredentials" ]; then
    echo " omgserversctl support useCredentials <user_id> <password>"
    if [ "$1" = "support useCredentials" ]; then
      echo "   produces:"
      echo "     - SUPPORT_USER_ID"
      echo "     - SUPPORT_PASSWORD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support printCurrent" ]; then
    echo " omgserversctl support printCurrent"
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createToken" ]; then
    echo " omgserversctl support createToken"
    if [ "$1" = "support createToken" ]; then
      echo "   produces:"
      echo "     - SUPPORT_TOKEN"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenant" ]; then
    echo " omgserversctl support createTenant"
    if [ "$1" = "support createTenant" ]; then
      echo "   produces:"
      echo "     - TENANT_ID"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenant" ]; then
    echo " omgserversctl support deleteTenant <tenant_id>"
    if [ "$1" = "support deleteTenant" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenantProject" ]; then
    echo " omgserversctl support createTenantProject <tenant_id>"
    if [ "$1" = "support createTenantProject" ]; then
      echo "   produces:"
      echo "     - TENANT_PROJECT_ID"
      echo "     - TENANT_STAGE_ID"
      echo "     - TENANT_STAGE_SECRET"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenantProject" ]; then
    echo " omgserversctl support deleteTenantProject <tenant_id> <tenant_project_id>"
    if [ "$1" = "support deleteTenantProject" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createDeveloper" ]; then
    echo " omgserversctl support createDeveloper"
    if [ "$1" = "support createDeveloper" ]; then
      echo "   produces:"
      echo "     - DEVELOPER_USER_ID"
      echo "     - DEVELOPER_PASSWORD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenantPermission" ]; then
    echo " omgserversctl support createTenantPermission <tenant_id> <user_id> <tenant_permission>"
    if [ "$1" = "support createTenantPermission" ]; then
      echo "   tenant_permission:"
      echo "     - PROJECT_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenantPermission" ]; then
    echo " omgserversctl support deleteTenantPermission <tenant_id> <user_id> <tenant_permission>"
    if [ "$1" = "support deleteTenantPermission" ]; then
      echo "   tenant_permission:"
      echo "     - PROJECT_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenantProjectPermission" ]; then
    echo " omgserversctl support createTenantProjectPermission <tenant_id> <tenant_project_id> <user_id> <tenant_project_permission>"
    if [ "$1" = "support createTenantProjectPermission" ]; then
      echo "   tenant_project_permission:"
      echo "     - STAGE_MANAGEMENT"
      echo "     - VERSION_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenantProjectPermission" ]; then
    echo " omgserversctl support deleteTenantProjectPermission <tenant_id> <tenant_project_id> <user_id> <tenant_project_permission>"
    if [ "$1" = "support deleteTenantProjectPermission" ]; then
      echo "   tenant_project_permission:"
      echo "     - STAGE_MANAGEMENT"
      echo "     - VERSION_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenantStagePermission" ]; then
    echo " omgserversctl support createTenantStagePermission <tenant_id> <tenant_stage_id> <user_id> <tenant_stage_permission>"
    if [ "$1" = "support createTenantStagePermission" ]; then
      echo "   tenant_stage_permission:"
      echo "     - DEPLOYMENT_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenantStagePermission" ]; then
    echo " omgserversctl support deleteTenantStagePermission <tenant_id> <tenant_stage_id> <user_id> <tenant_stage_permission>"
    if [ "$1" = "support deleteTenantStagePermission" ]; then
      echo "   tenant_stage_permission:"
      echo "     - DEPLOYMENT_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  # Developer
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer useCredentials" ]; then
    echo " omgserversctl developer useCredentials <user_id> <password>"
    if [ "$1" = "developer useCredentials" ]; then
      echo "   produces:"
      echo "     - DEVELOPER_USER_ID"
      echo "     - DEVELOPER_PASSWORD"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer printCurrent" ]; then
    echo " omgserversctl developer printCurrent"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createToken" ]; then
    echo " omgserversctl developer createToken"
    if [ "$1" = "developer createToken" ]; then
      echo "   produces:"
      echo "     - DEVELOPER_TOKEN"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getTenantDashboard" ]; then
    echo " omgserversctl developer getTenantDashboard <tenant_id>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createTenantProject" ]; then
    echo " omgserversctl developer createTenantProject <tenant_id>"
    if [ "$1" = "developer createTenantProject" ]; then
      echo "   produces:"
      echo "     - TENANT_PROJECT_ID"
      echo "     - TENANT_STAGE_ID"
      echo "     - TENANT_STAGE_SECRET"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getTenantProjectDashboard" ]; then
    echo " omgserversctl developer getTenantProjectDashboard <tenant_id> <tenant_project_id>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteTenantProject" ]; then
      echo " omgserversctl developer deleteTenantProject <tenant_id> <tenant_project_id>"
      if [ "$1" = "developer deleteTenantProject" ]; then
        echo "   produces:"
        echo "     - DELETED"
      fi
    fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createTenantStage" ]; then
    echo " omgserversctl developer createTenantStage <tenant_id> <tenant_project_id>"
    if [ "$1" = "developer createTenantStage" ]; then
      echo "   produces:"
      echo "     - TENANT_STAGE_ID"
      echo "     - TENANT_STAGE_SECRET"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getTenantStageDashboard" ]; then
    echo " omgserversctl developer getTenantStageDashboard <tenant_id> <tenant_stage_id>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteTenantStage" ]; then
    echo " omgserversctl developer deleteTenantStage <tenant_id> <tenant_stage_id>"
    if [ "$1" = "developer deleteTenantStage" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createTenantVersion" ]; then
    echo " omgserversctl developer createTenantVersion <tenant_id> <tenant_project_id> <config_path>"
    if [ "$1" = "developer createTenantVersion" ]; then
      echo "   produces:"
      echo "     - TENANT_VERSION_ID"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer uploadFilesArchive" ]; then
    echo " omgserversctl developer uploadFilesArchive <tenant_id> <tenant_version_id> <files_directory_path>"
    if [ "$1" = "developer uploadFilesArchive" ]; then
      echo "   produces:"
      echo "     - TENANT_FILES_ARCHIVE_ID"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getTenantVersionDashboard" ]; then
    echo " omgserversctl developer getTenantVersionDashboard <tenant_id> <tenant_version_id>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteTenantVersion" ]; then
    echo " omgserversctl developer deleteTenantVersion <tenant_id> <tenant_version_id>"
    if [ "$1" = "developer deleteTenantVersion" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deployTenantVersion" ]; then
    echo " omgserversctl developer deployTenantVersion <tenant_id> <tenant_stage_id> <tenant_version_id>"
    if [ "$1" = "developer deployTenantVersion" ]; then
      echo "   produces:"
      echo "     - TENANT_DEPLOYMENT_ID"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getTenantDeploymentDashboard" ]; then
    echo " omgserversctl developer getTenantDeploymentDashboard <tenant_id> <tenant_deployment_id>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteTenantDeployment" ]; then
    echo " omgserversctl developer deleteTenantDeployment <tenant_id> <tenant_deployment_id>"
    if [ "$1" = "developer deleteTenantDeployment" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteLobby" ]; then
    echo " omgserversctl developer deleteLobby <lobby_id>"
    if [ "$1" = "developer deleteLobby" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteMatchmaker" ]; then
    echo " omgserversctl developer deleteMatchmaker <matchmaker_id>"
    if [ "$1" = "developer deleteMatchmaker" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
}

logs() {
  cat ${OMGSERVERSCTL_DIRECTORY}/logs
}

# ENVIRONMENT

environment_printCurrent() {
  internal_useEnvironment

  env | grep OMGSERVERSCTL_
}

environment_printVariable() {
  internal_useEnvironment

  VARIABLE_NAME=$1

  if [ -z "${VARIABLE_NAME}" ]; then
    help "environment printVariable"
    exit 1
  fi

  VARIABLE_NAME="OMGSERVERSCTL_${VARIABLE_NAME}"

  if [ -z "${!VARIABLE_NAME}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Variable was not found, VARIABLE_NAME=${VARIABLE_NAME}"
    exit 1
  else
    echo -n ${!VARIABLE_NAME}
  fi
}

environment_useEnvironment() {
  ENVIRONMENT_NAME=$1
  EXTERNAL_URL=$2
  INTERNAL_URL=$3

  if [ -z "${ENVIRONMENT_NAME}" -o -z "${EXTERNAL_URL}" -o -z "${INTERNAL_URL}" ]; then
    help "environment useEnvironment"
    exit 1
  fi

  echo "export OMGSERVERSCTL_ENVIRONMENT_NAME=${ENVIRONMENT_NAME}" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_EXTERNAL_URL=${EXTERNAL_URL}" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_INTERNAL_URL=${INTERNAL_URL}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $ENVIRONMENT_NAME) Environment was set, NAME=${ENVIRONMENT_NAME}, EXTERNAL_URL=${EXTERNAL_URL}, INTERNAL_URL=${INTERNAL_URL}"
}

environment_usePublic() {
  environment_useEnvironment public https://api.omgservers.com https://api.omgservers.com
}

environment_useLocal() {
  environment_useEnvironment local http://localhost:8080 http://localhost:8080
  admin_useCredentials 223221505901723648 admin
  support_useCredentials 231928170708729857 support
}

# ADMIN

admin_useCredentials() {
  internal_useEnvironment

  ADMIN_USER_ID=$1
  ADMIN_PASSWORD=$2

  if [ -z "${ADMIN_USER_ID}" -o -z "${ADMIN_PASSWORD}" ]; then
    help "admin useCredentials"
    exit 1
  fi

  echo "export OMGSERVERSCTL_ADMIN_USER_ID=${ADMIN_USER_ID}" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_ADMIN_PASSWORD=${ADMIN_PASSWORD}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Admin credentials were set, ADMIN_USER_ID=${ADMIN_USER_ID}"

  internal_useEnvironment
  admin_createToken
}

admin_printCurrent() {
  internal_useEnvironment

  ADMIN_USER_ID=${OMGSERVERSCTL_ADMIN_USER_ID}
  ADMIN_PASSWORD=${OMGSERVERSCTL_ADMIN_PASSWORD}
  ADMIN_TOKEN=${OMGSERVERSCTL_ADMIN_TOKEN}

  if [ -z "${ADMIN_USER_ID}" -o -z "${ADMIN_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current admin was not set"
    exit 1
  fi

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current admin was found, ADMIN_USER_ID=${ADMIN_USER_ID}, (without token)"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current admin was found, ADMIN_USER_ID=${ADMIN_USER_ID}, (token exists)"
  fi
}

admin_createToken() {
  internal_useEnvironment

  ADMIN_USER_ID=${OMGSERVERSCTL_ADMIN_USER_ID}
  ADMIN_PASSWORD=${OMGSERVERSCTL_ADMIN_PASSWORD}

  if [ -z "${ADMIN_USER_ID}" -o -z "${ADMIN_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current admin credentials were not found"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using admin, ADMIN_USER_ID=${ADMIN_USER_ID}"

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/admin/request/create-token"
  REQUEST="{\"user_id\": \"${ADMIN_USER_ID}\", \"password\": \"${ADMIN_PASSWORD}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/admin-create-token_${ADMIN_USER_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-create-token_${ADMIN_USER_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  ADMIN_TOKEN=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-create-token_${ADMIN_USER_ID}.json | jq -r .raw_token)
  if [ -z "$ADMIN_TOKEN" -o "$ADMIN_TOKEN" == "null" ]; then
    echo "ERROR: ADMIN_TOKEN was not received"
    exit 1
  fi

  echo "export OMGSERVERSCTL_ADMIN_TOKEN=$ADMIN_TOKEN" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Admin token was created"
}

# SUPPORT

support_useCredentials() {
  internal_useEnvironment

  SUPPORT_USER_ID=$1
  SUPPORT_PASSWORD=$2

  if [ -z "${SUPPORT_USER_ID}" -o -z "${SUPPORT_PASSWORD}" ]; then
    help "support useCredentials"
    exit 1
  fi

  echo "export OMGSERVERSCTL_SUPPORT_USER_ID=${SUPPORT_USER_ID}" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_SUPPORT_PASSWORD=${SUPPORT_PASSWORD}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Support credentials were set, SUPPORT_USER_ID=${SUPPORT_USER_ID}"

  internal_useEnvironment
  support_createToken
}

support_printCurrent() {
  internal_useEnvironment

  SUPPORT_USER_ID=${OMGSERVERSCTL_SUPPORT_USER_ID}
  SUPPORT_PASSWORD=${OMGSERVERSCTL_SUPPORT_PASSWORD}
  SUPPORT_TOKEN=${OMGSERVERSCTL_SUPPORT_TOKEN}

  if [ -z "${SUPPORT_USER_ID}" -o -z "${SUPPORT_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support was not found"
    exit 1
  fi

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current support was found, SUPPORT_USER_ID=$SUPPORT_USER_ID, (without token)"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current support was found, SUPPORT_USER_ID=$SUPPORT_USER_ID, (token exists)"
  fi
}

support_createToken() {
  internal_useEnvironment

  SUPPORT_USER_ID=$OMGSERVERSCTL_SUPPORT_USER_ID
  SUPPORT_PASSWORD=$OMGSERVERSCTL_SUPPORT_PASSWORD

  if [ -z "${SUPPORT_USER_ID}" -o -z "${SUPPORT_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support credentials were not found"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using support, SUPPORT_USER_ID=$SUPPORT_USER_ID"

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-token"
  REQUEST="{\"user_id\": \"${SUPPORT_USER_ID}\", \"password\": \"${SUPPORT_PASSWORD}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-token_${SUPPORT_USER_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-token_${SUPPORT_USER_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  RAW_TOKEN=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-token_${SUPPORT_USER_ID}.json | jq -r .raw_token)
  if [ -z "$RAW_TOKEN" -o "$RAW_TOKEN" == "null" ]; then
    echo "ERROR: RAW_TOKEN was not received"
    exit 1
  fi

  echo "export OMGSERVERSCTL_SUPPORT_TOKEN=$RAW_TOKEN" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Support token was created"
}

support_createTenant() {
  internal_useEnvironment

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-tenant"
  REQUEST="{}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  TENANT_ID=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant.json | jq -r .id)
  if [ -z "$TENANT_ID" -o "$TENANT_ID" == "null" ]; then
    echo "ERROR: TENANT_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_ID=$TENANT_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant was created, TENANT_ID=$TENANT_ID"
}

support_deleteTenant() {
  internal_useEnvironment

  TENANT_ID=$1

  if [ -z "${TENANT_ID}" ]; then
    help "support deleteTenant"
    exit 1
  fi

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-tenant"
  REQUEST="{\"tenant_id\": \"${TENANT_ID}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant_${TENANT_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant_${TENANT_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant_${TENANT_ID}.json | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant was deleted, TENANT_ID=${TENANT_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant was not deleted, TENANT_ID=${TENANT_ID}"
  fi
}

support_createTenantProject() {
  internal_useEnvironment

  TENANT_ID=$1

  if [ -z "${TENANT_ID}" ]; then
    help "support createTenantProject"
    exit 1
  fi

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-tenant-project"
  REQUEST="{\"tenant_id\": ${TENANT_ID}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant-project_${TENANT_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  TENANT_PROJECT_ID=$(cat ${RESPONSE_FILE} | jq -r .tenant_project_id)
  if [ -z "$TENANT_PROJECT_ID" -o "$TENANT_PROJECT_ID" == "null" ]; then
    echo "ERROR: TENANT_PROJECT_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_PROJECT_ID=$TENANT_PROJECT_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  TENANT_STAGE_ID=$(cat ${RESPONSE_FILE} | jq -r .tenant_stage_id)
  if [ -z "${TENANT_STAGE_ID}" -o "${STAGE_ID}" == "null" ]; then
    echo "ERROR: TENANT_STAGE_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_STAGE_ID=${TENANT_STAGE_ID}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  TENANT_STAGE_SECRET=$(cat ${RESPONSE_FILE} | jq -r .tenant_stage_secret)
  if [ -z "${TENANT_STAGE_SECRET}" -o "${TENANT_STAGE_SECRET}" == "null" ]; then
    echo "ERROR: TENANT_STAGE_SECRET was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_STAGE_SECRET=${TENANT_STAGE_SECRET}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was created, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}, TENANT_STAGE_ID=${TENANT_STAGE_ID}, TENANT_STAGE_SECRET=${TENANT_STAGE_SECRET}"
}

support_deleteTenantProject() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_PROJECT_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${TENANT_PROJECT_ID}" ]; then
    help "support deleteTenantProject"
    exit 1
  fi

  SUPPORT_TOKEN=${OMGSERVERSCTL_SUPPORT_TOKEN}

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-tenant-project"
  REQUEST="{\"tenant_id\": \"${TENANT_ID}\", \"tenant_project_id\": ${TENANT_PROJECT_ID}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant-project_${TENANT_ID}_${TENANT_PROJECT_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was deleted, TENANT_ID=${TENANT_ID}, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was not deleted, TENANT_ID=${TENANT_ID}, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}"
  fi
}

support_createDeveloper() {
  internal_useEnvironment

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-developer"
  REQUEST="{}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  USER_ID=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT_ID}.json | jq -r .user_id)
  if [ -z "$USER_ID" -o "$USER_ID" == "null" ]; then
    echo "ERROR: USER_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_USER_ID=$USER_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  PASSWORD=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT_ID}.json | jq -r .password)
  if [ -z "$PASSWORD" -o "$PASSWORD" == "null" ]; then
    echo "ERROR: PASSWORD was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_PASSWORD=$PASSWORD" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Developer was created, DEVELOPER_USER_ID=${USER_ID}, DEVELOPER_PASSWORD=${PASSWORD}"

  internal_useEnvironment
  developer_createToken ${USER_ID} ${PASSWORD}
}

support_createTenantPermission() {
  internal_useEnvironment

  TENANT_ID=$1
  USER_ID=$2
  TENANT_PERMISSION=$3

  if [ -z "${TENANT_ID}" -o -z "${USER_ID}" -o -z "${TENANT_PERMISSION}" ]; then
    help "support createTenantPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER_ID=${USER_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant permission, TENANT_PERMISSION=${TENANT_PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-tenant-permissions"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"user_id\": ${USER_ID}, \"permissions_to_create\": [\"${TENANT_PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .created_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Created permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

support_deleteTenantPermission() {
  internal_useEnvironment

  TENANT_ID=$1
  USER_ID=$2
  TENANT_PERMISSION=$3

  if [ -z "${TENANT_ID}" -o -z "${USER_ID}" -o -z "${TENANT_PERMISSION}" ]; then
    help "support deleteTenantPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER_ID=${USER_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant permission, TENANT_PERMISSION=${TENANT_PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-tenant-permissions"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"user_id\": ${USER_ID}, \"permissions_to_delete\": [\"${TENANT_PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -r .deleted_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deleted permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

support_createTenantProjectPermission() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_PROJECT_ID=$2
  USER_ID=$3
  TENANT_PROJECT_PERMISSION=$4

  if [ -z "${TENANT_ID}" -o -z "${TENANT_PROJECT_ID}" -o -z "${USER_ID}" -o -z "${TENANT_PROJECT_PERMISSION}" ]; then
    help "support createTenantProjectPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant project, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER_ID=${USER_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant project permission, TENANT_PROJECT_PERMISSION=${TENANT_PROJECT_PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-tenant-project-permissions"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"tenant_project_id\": ${TENANT_PROJECT_ID}, \"user_id\": ${USER_ID}, \"permissions_to_create\": [\"${TENANT_PROJECT_PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant-project-permissions_${TENANT_ID}_${TENANT_PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PROJECT_PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .created_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Created tenant project permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

support_deleteTenantProjectPermission() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_PROJECT_ID=$2
  USER_ID=$3
  TENANT_PROJECT_PERMISSION=$4

  if [ -z "${TENANT_ID}" -o -z "${TENANT_PROJECT_ID}" -o -z "${USER_ID}" -o -z "${TENANT_PROJECT_PERMISSION}" ]; then
    help "support deleteTenantProjectPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant project, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER_ID=${USER_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant project permission, TENANT_PROJECT_PERMISSION=${TENANT_PROJECT_PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-tenant-project-permissions"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"tenant_project_id\": ${TENANT_PROJECT_ID}, \"user_id\": ${USER_ID}, \"permissions_to_delete\": [\"${TENANT_PROJECT_PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant-project-permissions_${TENANT_ID}_${TENANT_PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PROJECT_PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -r .deleted_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deleted tenant project permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

support_createTenantStagePermission() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_STAGE_ID=$2
  USER_ID=$3
  TENANT_STAGE_PERMISSION=$4

  if [ -z "${TENANT_ID}" -o -z "${TENANT_STAGE_ID}" -o -z "${USER_ID}" -o -z "${TENANT_STAGE_PERMISSION}" ]; then
    help "support createTenantStagePermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant stage, TENANT_STAGE_ID=${TENANT_STAGE_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER_ID=${USER_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant stage permission, TENANT_STAGE_PERMISSION=${TENANT_STAGE_PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-tenant-stage-permissions"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"tenant_stage_id\": ${TENANT_STAGE_ID}, \"user_id\": ${USER_ID}, \"permissions_to_create\": [\"${TENANT_STAGE_PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-stage-permissions_${TENANT_ID}_${TENANT_STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_STAGE_PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .created_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Created tenant stage permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

support_deleteTenantStagePermission() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_STAGE_ID=$2
  USER_ID=$3
  TENANT_STAGE_PERMISSION=$4

  if [ -z "${TENANT_ID}" -o -z "${TENANT_STAGE_ID}" -o -z "${USER_ID}" -o -z "${TENANT_STAGE_PERMISSION}" ]; then
    help "support deleteTenantStagePermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant stage, TENANT_STAGE_ID=${TENANT_STAGE_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER_ID=${USER_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant project permission, TENANT_STAGE_PERMISSION=${TENANT_STAGE_PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-tenant-stage-permissions"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"tenant_stage_id\": ${STAGE_ID}, \"user_id\": ${USER_ID}, \"permissions_to_delete\": [\"${TENANT_STAGE_PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-stage-permissions_${TENANT_ID}_${TENANT_STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_STAGE_PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -r .deleted_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deleted tenant stage permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

# DEVELOPER

developer_useCredentials() {
  internal_useEnvironment

  DEVELOPER_USER_ID=$1
  DEVELOPER_PASSWORD=$2

  if [ -z "${DEVELOPER_USER_ID}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    help "developer useCredentials"
    exit 1
  fi

  echo "export OMGSERVERSCTL_DEVELOPER_USER_ID=$DEVELOPER_USER_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_DEVELOPER_PASSWORD=$DEVELOPER_PASSWORD" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Developer credentials were set, DEVELOPER_USER_ID=$DEVELOPER_USER_ID"

  internal_useEnvironment
  developer_createToken ${DEVELOPER_USER_ID} ${DEVELOPER_PASSWORD}
}


developer_printCurrent() {
  internal_useEnvironment

  DEVELOPER_USER_ID=${OMGSERVERSCTL_DEVELOPER_USER_ID}
  DEVELOPER_PASSWORD=${OMGSERVERSCTL_DEVELOPER_PASSWORD}
  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_USER_ID}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer was not found"
    exit 1
  fi

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current developer was found, DEVELOPER_USER_ID=${DEVELOPER_USER_ID}, (without token)"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current developer was found, DEVELOPER_USER_ID=${DEVELOPER_USER_ID}, (token exists)"
  fi
}

developer_createToken() {
  internal_useEnvironment

  DEVELOPER_USER_ID=${OMGSERVERSCTL_DEVELOPER_USER_ID}
  DEVELOPER_PASSWORD=${OMGSERVERSCTL_DEVELOPER_PASSWORD}

  if [ -z "${DEVELOPER_USER_ID}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer credentials were not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/create-token"
  REQUEST="{\"user_id\": \"${DEVELOPER_USER_ID}\", \"password\": \"${DEVELOPER_PASSWORD}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-token_${DEVELOPER_USER_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-token_${DEVELOPER_USER_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  RAW_TOKEN=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-token_${DEVELOPER_USER_ID}.json | jq -r .raw_token)
  if [ -z "$RAW_TOKEN" -o "$RAW_TOKEN" == "null" ]; then
    echo "ERROR: RAW_TOKEN was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_TOKEN=$RAW_TOKEN" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Developer token was created"
}

developer_getTenantDashboard() {
  internal_useEnvironment

  TENANT_ID=$1

  if [ -z "${TENANT_ID}" ]; then
    help "developer getTenantDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=$TENANT_ID"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-tenant-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT_ID}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-dashboard_${TENANT_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-dashboard_${TENANT_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-dashboard_${TENANT_ID}.json || cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-dashboard_${TENANT_ID}.json | jq
}

developer_createTenantProject() {
  internal_useEnvironment

  TENANT_ID=$1

  if [ -z "${TENANT_ID}" ]; then
    help "developer createTenantProject"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=$TENANT_ID"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/create-tenant-project"
  REQUEST="{\"tenant_id\": ${TENANT_ID}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-tenant-project_${TENANT_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-tenant-project_${TENANT_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  TENANT_PROJECT_ID=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-tenant-project_${TENANT_ID}.json | jq -r .tenant_project_id)
  if [ -z "$TENANT_PROJECT_ID" -o "$TENANT_PROJECT_ID" == "null" ]; then
    echo "ERROR: TENANT_PROJECT_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_PROJECT_ID=$TENANT_PROJECT_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  TENANT_STAGE_ID=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-tenant-project_${TENANT_ID}.json | jq -r .tenant_stage_id)
  if [ -z "$TENANT_STAGE_ID" -o "$TENANT_STAGE_ID" == "null" ]; then
    echo "ERROR: TENANT_STAGE_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_STAGE_ID=$TENANT_STAGE_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  TENANT_STAGE_SECRET=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-tenant-project_${TENANT_ID}.json | jq -r .tenant_stage_secret)
  if [ -z "$TENANT_STAGE_SECRET" -o "$TENANT_STAGE_SECRET" == "null" ]; then
    echo "ERROR: TENANT_STAGE_SECRET was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_STAGE_SECRET=${TENANT_STAGE_SECRET}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was created, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}, TENANT_STAGE_ID=${TENANT_STAGE_ID}, TENANT_STAGE_SECRET=${TENANT_STAGE_SECRET}"
}

developer_getTenantProjectDashboard() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_PROJECT_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${TENANT_PROJECT_ID}" ]; then
    help "developer getTenantProjectDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant project, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-tenant-project-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"tenant_project_id\": ${TENANT_PROJECT_ID}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-project-dashboard_${TENANT_ID}_${TENANT_PROJECT_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-project-dashboard_${TENANT_ID}_${TENANT_PROJECT_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-project-dashboard_${TENANT_ID}_${TENANT_PROJECT_ID}.json || cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-project-dashboard_${TENANT_ID}_${TENANT_PROJECT_ID}.json | jq
}

developer_deleteTenantProject() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_PROJECT_ID=$1

  if [ -z "${TENANT_ID}" -o -z "${TENANT_PROJECT_ID}" ]; then
    help "developer deleteTenantProject"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant project, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/developer/request/delete-tenant-project"
  REQUEST="{\"tenant_id\": \"${TENANT_ID}\", \"tenant_project_id\": \"${TENANT_PROJECT_ID}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-tenant-project_${TENANT_ID}_${TENANT_PROJECT_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was deleted, TENANT_ID=${TENANT_ID}, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was not deleted, TENANT_ID=${TENANT_ID}, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}"
  fi
}

developer_createTenantStage() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_PROJECT_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${TENANT_PROJECT_ID}" ]; then
    help "developer createTenantStage"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=$TENANT_ID"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant project, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/create-tenant-stage"
  REQUEST="{\"tenant_id\": ${TENANT_ID}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-tenant-stage_${TENANT_ID}_${TENANT_PROJECT_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  TENANT_STAGE_ID=$(cat ${RESPONSE_FILE} | jq -r .tenant_stage_id)
  if [ -z "$TENANT_STAGE_ID" -o "$TENANT_STAGE_ID" == "null" ]; then
    echo "ERROR: TENANT_STAGE_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_STAGE_ID=$TENANT_STAGE_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  TENANT_STAGE_SECRET=$(cat ${RESPONSE_FILE} | jq -r .tenant_stage_secret)
  if [ -z "$TENANT_STAGE_SECRET" -o "$TENANT_STAGE_SECRET" == "null" ]; then
    echo "ERROR: TENANT_STAGE_SECRET was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_STAGE_SECRET=$TENANT_STAGE_SECRET" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant stage was created, TENANT_STAGE_ID=${TENANT_STAGE_ID}, TENANT_STAGE_SECRET=${TENANT_STAGE_SECRET}"
}

developer_getTenantStageDashboard() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_STAGE_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${TENANT_STAGE_ID}" ]; then
    help "developer getTenantStageDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant stage, TENANT_STAGE_ID=${TENANT_STAGE_ID}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-tenant-stage-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"tenant_stage_id\": ${TENANT_STAGE_ID}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-stage-dashboard_${TENANT_ID}_${TENANT_STAGE_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${RESPONSE_FILE} || cat ${RESPONSE_FILE} | jq
}

developer_deleteTenantStage() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_STAGE_ID=$1

  if [ -z "${TENANT_ID}" -o -z "${TENANT_STAGE_ID}" ]; then
    help "developer deleteTenantStage"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant stage, TENANT_STAGE_ID=${TENANT_STAGE_ID}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/developer/request/delete-tenant-stage"
  REQUEST="{\"tenant_id\": \"${TENANT_ID}\", \"tenant_stage_id\": \"${TENANT_STAGE_ID}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-tenant-stage_${TENANT_ID}_${TENANT_STAGE_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant stage was deleted, TENANT_ID=${TENANT_ID}, TENANT_STAGE_ID=${TENANT_STAGE_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant stage was not deleted, TENANT_ID=${TENANT_ID}, TENANT_STAGE_ID=${TENANT_STAGE_ID}"
  fi
}

developer_createTenantVersion() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_PROJECT_ID=$2
  CONFIG_PATH=$3

  if [ -z "${TENANT_ID}" -o -z "${TENANT_PROJECT_ID}" -o -z "${CONFIG_PATH}" ]; then
    help "developer createTenantVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant project, TENANT_PROJECT_ID=${TENANT_PROJECT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using config path, CONFIG_PATH=${CONFIG_PATH}"

  if [ ! -f ${CONFIG_PATH} ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Config file was not found, CONFIG_PATH=${CONFIG_PATH}"
    exit 1
  fi

  TENANT_VERSION_CONFIG=$(cat ${CONFIG_PATH} | jq -c -r)

  if [ -z "${TENANT_VERSION_CONFIG}" -o "${TENANT_VERSION_CONFIG}" == "null" ]; then
    echo "ERROR: Tenant version config is empty"
    exit 1
  fi

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/create-tenant-version"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"tenant_project_id\": ${TENANT_PROJECT_ID}, \"tenant_version_config\": ${TENANT_VERSION_CONFIG}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-tenant-version_${TENANT_ID}_${TENANT_STAGE_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  TENANT_VERSION_ID=$(cat ${RESPONSE_FILE} | jq -r .tenant_version_id)
  if [ -z "$TENANT_VERSION_ID" -o "$TENANT_VERSION_ID" == "null" ]; then
    echo "ERROR: TENANT_VERSION_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_VERSION_ID=${TENANT_VERSION_ID}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant version was created, TENANT_VERSION_ID=${TENANT_VERSION_ID}"
}

developer_uploadFilesArchive() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_VERSION_ID=$2
  FILES_DIRECTORY_PATH=$3

  if [ -z "${TENANT_ID}" -o -z "${TENANT_VERSION_ID}" -o -z "${FILES_DIRECTORY_PATH}" ]; then
    help "developer uploadFilesArchive"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant version, TENANT_VERSION_ID=${TENANT_VERSION_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using filed directory path, FILES_DIRECTORY_PATH=${FILES_DIRECTORY_PATH}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ARCHIVE_PATH=$(eval echo ${OMGSERVERSCTL_DIRECTORY}/versions/version_${TENANT_ID}_${TENANT_VERSION_ID}.zip)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using archive path, ARCHIVE_PATH=${ARCHIVE_PATH}"

  pushd ${FILES_DIRECTORY_PATH}

  find . -type f -name "*.lua" | zip -@ ${ARCHIVE_PATH}

  popd >> /dev/null

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/upload-files-archive"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-upload-files-archive_${TENANT_ID}_${TENANT_VERSION_ID}.json"

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: multipart/form-data" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -F "tenantId=${TENANT_ID}" \
    -F "tenantVersionId=${TENANT_VERSION_ID}" \
    -F "version.zip=@${ARCHIVE_PATH}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  TENANT_FILES_ARCHIVE_ID=$(cat ${RESPONSE_FILE} | jq -r .tenant_files_archive_id)
  if [ -z "${TENANT_FILES_ARCHIVE_ID}" -o "${TENANT_FILES_ARCHIVE_ID}" == "null" ]; then
    echo "ERROR: TENANT_FILES_ARCHIVE_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_FILES_ARCHIVE_ID=${TENANT_FILES_ARCHIVE_ID}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant files archive was uploaded, TENANT_FILES_ARCHIVE_ID=${TENANT_FILES_ARCHIVE_ID}"
}

developer_getTenantVersionDashboard() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_VERSION_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${TENANT_VERSION_ID}" ]; then
    help "developer getTenantVersionDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant version, TENANT_VERSION_ID=${TENANT_VERSION_ID}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-tenant-version-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"tenant_version_id\": ${TENANT_VERSION_ID}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-version-dashboard_${TENANT_ID}_${TENANT_VERSION_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${RESPONSE_FILE} || cat ${RESPONSE_FILE} | jq
}

developer_deleteTenantVersion() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_VERSION_ID=$1

  if [ -z "${TENANT_ID}" -o -z "${TENANT_VERSION_ID}" ]; then
    help "developer deleteTenantVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant version, TENANT_VERSION_ID=${TENANT_VERSION_ID}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/developer/request/delete-tenant-version"
  REQUEST="{\"tenant_id\": \"${TENANT_ID}\", \"tenant_version_id\": \"${TENANT_VERSION_ID}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-tenant-version_${TENANT_ID}_${TENANT_VERSION_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant version was deleted, TENANT_ID=${TENANT_ID}, TENANT_VERSION_ID=${TENANT_VERSION_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant version was not deleted, TENANT_ID=${TENANT_ID}, TENANT_VERSION_ID=${TENANT_VERSION_ID}"
  fi
}

developer_deployTenantVersion() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_STAGE_ID=$2
  TENANT_VERSION_ID=$3

  if [ -z "${TENANT_ID}" -o -z "${TENANT_STAGE_ID}" -o -z "${TENANT_VERSION_ID}" ]; then
    help "developer deployTenantVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant stage, TENANT_STAGE_ID=${TENANT_STAGE_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant version, TENANT_VERSION_ID=${TENANT_VERSION_ID}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/deploy-tenant-version"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"tenant_stage_id\": ${TENANT_STAGE_ID}, \"tenant_version_id\": ${TENANT_VERSION_ID}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-deploy-tenant-version_${TENANT_ID}_${TENANT_STAGE_ID}_${TENANT_VERSION_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  TENANT_DEPLOYMENT_ID=$(cat ${RESPONSE_FILE} | jq -r .tenant_deployment_id)
  if [ -z "${TENANT_DEPLOYMENT_ID}" -o "${TENANT_DEPLOYMENT_ID}" == "null" ]; then
    echo "ERROR: TENANT_DEPLOYMENT_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_DEPLOYMENT_ID=${TENANT_DEPLOYMENT_ID}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant deployment was created, TENANT_DEPLOYMENT_ID=${TENANT_DEPLOYMENT_ID}"
}

developer_getTenantDeploymentDashboard() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_DEPLOYMENT_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${TENANT_DEPLOYMENT_ID}" ]; then
    help "developer getTenantDeploymentDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant deployment, TENANT_DEPLOYMENT_ID=${TENANT_DEPLOYMENT_ID}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-tenant-deployment-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"tenant_deployment_id\": ${TENANT_DEPLOYMENT_ID}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-deployment-dashboard_${TENANT_ID}_${TENANT_DEPLOYMENT_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${RESPONSE_FILE} || cat ${RESPONSE_FILE} | jq
}

developer_deleteTenantDeployment() {
  internal_useEnvironment

  TENANT_ID=$1
  TENANT_DEPLOYMENT_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${TENANT_DEPLOYMENT_ID}" ]; then
    help "developer deleteTenantDeployment"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant deployment, TENANT_DEPLOYMENT_ID=${TENANT_DEPLOYMENT_ID}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/developer/request/delete-tenant-deployment"
  REQUEST="{\"tenant_id\": \"${TENANT_ID}\", \"id\": \"${TENANT_DEPLOYMENT_ID}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-tenant-deployment_${TENANT_ID}_${TENANT_DEPLOYMENT_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant deployment was deleted, TENANT_ID=${TENANT_ID}, TENANT_DEPLOYMENT_ID=${TENANT_DEPLOYMENT_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant deployment was not deleted, TENANT_ID=${TENANT_ID}, TENANT_DEPLOYMENT_ID=${TENANT_DEPLOYMENT_ID}"
  fi
}

developer_deleteLobby() {
  internal_useEnvironment

  LOBBY_ID=$1

  if [ -z "${LOBBY_ID}" ]; then
    help "developer deleteLobby"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using lobby, LOBBY_ID=${LOBBY_ID}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/developer/request/delete-lobby"
  REQUEST="{\"lobby_id\": \"${LOBBY_ID}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-lobby_${LOBBY_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Lobby was deleted, LOBBY_ID=${LOBBY_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Lobby was not deleted, LOBBY_ID=${LOBBY_ID}"
  fi
}

developer_deleteMatchmaker() {
  internal_useEnvironment

  MATCHMAKER_ID=$1

  if [ -z "${MATCHMAKER_ID}" ]; then
    help "developer deleteMatchmaker"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using matchmaker, MATCHMAKER_ID=${MATCHMAKER_ID}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/developer/request/delete-matchmaker"
  REQUEST="{\"matchmaker_id\": \"${MATCHMAKER_ID}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-matchmaker_${MATCHMAKER_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Matchmaker was deleted, MATCHMAKER_ID=${MATCHMAKER_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Matchmaker was not deleted, MATCHMAKER_ID=${MATCHMAKER_ID}"
  fi
}

# INTERNAL

internal_useEnvironment() {
  source ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ -z "${OMGSERVERSCTL_ENVIRONMENT_NAME}" ]; then
    echo "$(date) $(echo unknown) ERROR: Environment was not configured"
    exit 1
  else
    source ${OMGSERVERSCTL_DIRECTORY}/environment
  fi
}

# MAIN

OMGSERVERSCTL_DIRECTORY=${OMGSERVERSCTL_DIRECTORY:-.omgserversctl}
if [ ! -d "${OMGSERVERSCTL_DIRECTORY}" ]; then
  mkdir -p ${OMGSERVERSCTL_DIRECTORY}
fi
if [ ! -d "${OMGSERVERSCTL_DIRECTORY}/temp" ]; then
  mkdir -p ${OMGSERVERSCTL_DIRECTORY}/temp
fi
if [ ! -d "${OMGSERVERSCTL_DIRECTORY}/versions" ]; then
  mkdir -p ${OMGSERVERSCTL_DIRECTORY}/versions
fi
if [ ! -f "${OMGSERVERSCTL_DIRECTORY}/environment" ]; then
  touch ${OMGSERVERSCTL_DIRECTORY}/environment
fi
if [ ! -f "${OMGSERVERSCTL_DIRECTORY}/logs" ]; then
  touch ${OMGSERVERSCTL_DIRECTORY}/logs
fi

if [ -z "$1" ]; then
  help
  exit 0
fi

if [ "$1" = "help" ]; then
  shift
  help "$*"
  exit 0
fi

if [ "$1" = "logs" ]; then
  logs
  exit 0
fi

# Env
if [ "$1" = "environment" ]; then
  if [ "$2" = "printCurrent" ]; then
    environment_printCurrent
  elif [ "$2" = "printVariable" ]; then
    environment_printVariable $3
  elif [ "$2" = "useEnvironment" ]; then
    environment_useEnvironment $3 $4 $5
  elif [ "$2" = "usePublic" ]; then
    environment_usePublic
  elif [ "$2" = "useLocal" ]; then
    environment_useLocal
  else
    help "environment"
  fi
  exit 0
fi

# Admin
if [ "$1" = "admin" ]; then
  if [ "$2" = "useCredentials" ]; then
    admin_useCredentials $3 $4
  elif [ "$2" = "printCurrent" ]; then
    admin_printCurrent
  elif [ "$2" = "createToken" ]; then
    admin_createToken
  else
    help "admin"
  fi
# Support
elif [ "$1" = "support" ]; then
  if [ "$2" = "useCredentials" ]; then
    support_useCredentials $3 $4
  elif [ "$2" = "printCurrent" ]; then
    support_printCurrent
  elif [ "$2" = "createToken" ]; then
    support_createToken
  elif [ "$2" = "createTenant" ]; then
    support_createTenant
  elif [ "$2" = "deleteTenant" ]; then
    support_deleteTenant $3
  elif [ "$2" = "createTenantProject" ]; then
    support_createTenantProject $3
  elif [ "$2" = "deleteTenantProject" ]; then
    support_deleteTenantProject $3 $4
  elif [ "$2" = "createDeveloper" ]; then
    support_createDeveloper $3
  elif [ "$2" = "createTenantPermission" ]; then
    support_createTenantPermission $3 $4 $5
  elif [ "$2" = "deleteTenantPermission" ]; then
    support_deleteTenantPermission $3 $4 $5
  elif [ "$2" = "createTenantProjectPermission" ]; then
    support_createTenantProjectPermission $3 $4 $5 $6
  elif [ "$2" = "deleteTenantProjectPermission" ]; then
    support_deleteTenantProjectPermission $3 $4 $5 $6
  elif [ "$2" = "createTenantStagePermission" ]; then
    support_createTenantStagePermission $3 $4 $5 $6
  elif [ "$2" = "deleteTenantStagePermission" ]; then
    support_deleteTenantStagePermission $3 $4 $5 $6
  else
    help "support"
  fi
# Developer
elif [ "$1" = "developer" ]; then
  if [ "$2" = "useCredentials" ]; then
    developer_useCredentials $3 $4
  elif [ "$2" = "printCurrent" ]; then
      developer_printCurrent
  elif [ "$2" = "createToken" ]; then
    developer_createToken
  elif [ "$2" = "getTenantDashboard" ]; then
    developer_getTenantDashboard $3
  elif [ "$2" = "createTenantProject" ]; then
    developer_createTenantProject $3
  elif [ "$2" = "getTenantProjectDashboard" ]; then
    developer_getTenantProjectDashboard $3 $4
  elif [ "$2" = "deleteTenantProject" ]; then
    developer_deleteTenantProject $3 $4
  elif [ "$2" = "createTenantStage" ]; then
    developer_createTenantStage $3 $4
  elif [ "$2" = "getTenantStageDashboard" ]; then
    developer_getTenantStageDashboard $3 $4
  elif [ "$2" = "deleteTenantStage" ]; then
    developer_deleteTenantStage $3 $4
  elif [ "$2" = "createTenantVersion" ]; then
    developer_createTenantVersion $3 $4 $5
  elif [ "$2" = "uploadFilesArchive" ]; then
    developer_uploadFilesArchive $3 $4 $5
  elif [ "$2" = "getTenantVersionDashboard" ]; then
    developer_getTenantVersionDashboard $3 $4
  elif [ "$2" = "deleteTenantVersion" ]; then
    developer_deleteTenantVersion $3 $4
  elif [ "$2" = "deployTenantVersion" ]; then
    developer_deployTenantVersion $3 $4 $5
  elif [ "$2" = "getTenantDeploymentDashboard" ]; then
    developer_getTenantDeploymentDashboard $3 $4
  elif [ "$2" = "deleteTenantDeployment" ]; then
    developer_deleteTenantDeployment $3 $4
  elif [ "$2" = "deleteLobby" ]; then
    developer_deleteLobby $3
  elif [ "$2" = "deleteMatchmaker" ]; then
    developer_deleteMatchmaker $3
  else
    help "developer"
  fi
else
  help
fi
