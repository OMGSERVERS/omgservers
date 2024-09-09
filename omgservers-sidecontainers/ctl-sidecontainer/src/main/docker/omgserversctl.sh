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
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createProject" ]; then
    echo " omgserversctl support createProject <tenant_id>"
    if [ "$1" = "support createProject" ]; then
      echo "   produces:"
      echo "     - PROJECT_ID"
      echo "     - STAGE_ID"
      echo "     - STAGE_SECRET"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteProject" ]; then
    echo " omgserversctl support deleteProject <tenant_id> <project_id>"
    if [ "$1" = "support deleteProject" ]; then
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
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createProjectPermission" ]; then
    echo " omgserversctl support createProjectPermission <tenant_id> <project_id> <user_id> <project_permission>"
    if [ "$1" = "support createProjectPermission" ]; then
      echo "   project_permission:"
      echo "     - STAGE_MANAGEMENT"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteProjectPermission" ]; then
    echo " omgserversctl support deleteProjectPermission <tenant_id> <project_id> <user_id> <project_permission>"
    if [ "$1" = "support deleteProjectPermission" ]; then
      echo "   project_permission:"
      echo "     - STAGE_MANAGEMENT"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createStagePermission" ]; then
    echo " omgserversctl support createStagePermission <tenant_id> <stage_id> <user_id> <stage_permission>"
    if [ "$1" = "support createStagePermission" ]; then
      echo "   stage_permission:"
      echo "     - VERSION_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteStagePermission" ]; then
    echo " omgserversctl support deleteStagePermission <tenant_id> <stage_id> <user_id> <stage_permission>"
    if [ "$1" = "support deleteStagePermission" ]; then
      echo "   stage_permission:"
      echo "     - VERSION_MANAGEMENT"
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
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createProject" ]; then
    echo " omgserversctl developer createProject <tenant_id>"
    if [ "$1" = "developer createProject" ]; then
      echo "   produces:"
      echo "     - PROJECT_ID"
      echo "     - STAGE_ID"
      echo "     - STAGE_SECRET"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createVersion" ]; then
    echo " omgserversctl developer createVersion <tenant_id> <stage_id> <config_path>"
    if [ "$1" = "developer createVersion" ]; then
      echo "   produces:"
      echo "     - VERSION_ID"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getTenantDashboard" ]; then
    echo " omgserversctl developer getTenantDashboard <tenant_id>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getStageDashboard" ]; then
    echo " omgserversctl developer getStageDashboard <tenant_id> <stage_id>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getVersionDashboard" ]; then
    echo " omgserversctl developer getVersionDashboard <tenant_id> <version_id>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer buildVersion" ]; then
    echo " omgserversctl developer buildVersion <tenant_id> <stage_id> <project_path>"
    if [ "$1" = "developer buildVersion" ]; then
      echo "   produces:"
      echo "     - VERSION_ID"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deployVersion" ]; then
    echo " omgserversctl developer deployVersion <tenant_id> <versionId>"
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

support_createProject() {
  internal_useEnvironment

  TENANT_ID=$1

  if [ -z "${TENANT_ID}" ]; then
    help "support createProject"
    exit 1
  fi

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-project"
  REQUEST="{\"tenant_id\": ${TENANT_ID}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project_${TENANT_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project_${TENANT_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  PROJECT_ID=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project_${TENANT_ID}.json | jq -r .project_id)
  if [ -z "$PROJECT_ID" -o "$PROJECT_ID" == "null" ]; then
    echo "ERROR: PROJECT_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_PROJECT_ID=$PROJECT_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  STAGE_ID=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project_${TENANT_ID}.json | jq -r .stage_id)
  if [ -z "${STAGE_ID}" -o "${STAGE_ID}" == "null" ]; then
    echo "ERROR: STAGE_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE_ID=${STAGE_ID}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  STAGE_SECRET=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project_${TENANT_ID}.json | jq -r .stage_secret)
  if [ -z "${STAGE_SECRET}" -o "${STAGE_SECRET}" == "null" ]; then
    echo "ERROR: STAGE_SECRET was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE_SECRET=${STAGE_SECRET}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was created, PROJECT_ID=${PROJECT_ID}, STAGE_ID=${STAGE_ID}, STAGE_SECRET=${STAGE_SECRET}"
}

support_deleteProject() {
  internal_useEnvironment

  TENANT_ID=$1
  PROJECT_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${PROJECT_ID}" ]; then
    help "support deleteProject"
    exit 1
  fi

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-project"
  REQUEST="{\"tenant_id\": \"${TENANT_ID}\", \"project_id\": ${PROJECT_ID} }"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project_${TENANT_ID}_${PROJECT_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project_${TENANT_ID}_${PROJECT_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project_${TENANT_ID}_${PROJECT_ID}.json | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was deleted, TENANT_ID=${TENANT_ID}, PROJECT_ID=${PROJECT_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was not deleted, TENANT_ID=${TENANT_ID}, PROJECT_ID=${PROJECT_ID}"
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

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -c -r .created_permissions)
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

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -r .deleted_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deleted permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

support_createProjectPermission() {
  internal_useEnvironment

  TENANT_ID=$1
  PROJECT_ID=$2
  USER_ID=$3
  PROJECT_PERMISSION=$4

  if [ -z "${TENANT_ID}" -o -z "${PROJECT_ID}" -o -z "${USER_ID}" -o -z "${PROJECT_PERMISSION}" ]; then
    help "support createProjectPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT_ID=${PROJECT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER_ID=${USER_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project permission, PROJECT_PERMISSION=${PROJECT_PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-project-permissions"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"project_id\": ${PROJECT_ID}, \"user_id\": ${USER_ID}, \"permissions_to_create\": [\"${PROJECT_PERMISSION}\"]}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -c -r .created_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Created permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

support_deleteProjectPermission() {
  internal_useEnvironment

  TENANT_ID=$1
  PROJECT_ID=$2
  USER_ID=$3
  PROJECT_PERMISSION=$4

  if [ -z "${TENANT_ID}" -o -z "${PROJECT_ID}" -o -z "${USER_ID}" -o -z "${PROJECT_PERMISSION}" ]; then
    help "support createProjectPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT_ID=${PROJECT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER_ID=${USER_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project permission, PROJECT_PERMISSION=${PROJECT_PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-project-permissions"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"project_id\": ${PROJECT_ID}, \"user_id\": ${USER_ID}, \"permissions_to_delete\": [\"${PROJECT_PERMISSION}\"]}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -r .deleted_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deleted permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

support_createStagePermission() {
  internal_useEnvironment

  TENANT_ID=$1
  STAGE_ID=$2
  USER_ID=$3
  STAGE_PERMISSION=$4

  if [ -z "${TENANT_ID}" -o -z "${STAGE_ID}" -o -z "${USER_ID}" -o -z "${STAGE_PERMISSION}" ]; then
    help "support createStagePermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE_ID=${STAGE_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER_ID=${USER_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project permission, STAGE_PERMISSION=${STAGE_PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-stage-permissions"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"stage_id\": ${STAGE_ID}, \"user_id\": ${USER_ID}, \"permissions_to_create\": [\"${STAGE_PERMISSION}\"]}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -c -r .created_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Created permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

support_deleteStagePermission() {
  internal_useEnvironment

  TENANT_ID=$1
  STAGE_ID=$2
  USER_ID=$3
  STAGE_PERMISSION=$4

  if [ -z "${TENANT_ID}" -o -z "${STAGE_ID}" -o -z "${USER_ID}" -o -z "${STAGE_PERMISSION}" ]; then
    help "support deleteStagePermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE_ID=${STAGE_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER_ID=${USER_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project permission, STAGE_PERMISSION=${STAGE_PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-stage-permissions"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"stage_id\": ${STAGE_ID}, \"user_id\": ${USER_ID}, \"permissions_to_delete\": [\"${STAGE_PERMISSION}\"]}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -r .deleted_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deleted permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

# DEVELOPER

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

developer_createProject() {
  internal_useEnvironment

  TENANT_ID=$1

  if [ -z "${TENANT_ID}" ]; then
    help "developer createProject"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=$TENANT_ID"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/create-project"
  REQUEST="{\"tenant_id\": ${TENANT_ID}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  PROJECT_ID=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT_ID}.json | jq -r .project_id)
  if [ -z "$PROJECT_ID" -o "$PROJECT_ID" == "null" ]; then
    echo "ERROR: PROJECT_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_PROJECT_ID=$PROJECT_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  STAGE_ID=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT_ID}.json | jq -r .stage_id)
  if [ -z "$STAGE_ID" -o "$STAGE_ID" == "null" ]; then
    echo "ERROR: STAGE_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE_ID=$STAGE_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  STAGE_SECRET=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT_ID}.json | jq -r .secret)
  if [ -z "$STAGE_SECRET" -o "$STAGE_SECRET" == "null" ]; then
    echo "ERROR: STAGE_SECRET was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE_SECRET=$STAGE_SECRET" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was created, PROJECT_ID=${PROJECT_ID}, STAGE_ID=${STAGE_ID}, STAGE_SECRET=${STAGE_SECRET}"
}

developer_createVersion() {
  internal_useEnvironment

  TENANT_ID=$1
  STAGE_ID=$2
  CONFIG_PATH=$3

  if [ -z "${TENANT_ID}" -o -z "${STAGE_ID}" -o -z "${CONFIG_PATH}" ]; then
    help "developer createVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE_ID=${STAGE_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using config path, CONFIG_PATH=${CONFIG_PATH}"

  if [ ! -f ${CONFIG_PATH} ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Config file was not found, CONFIG_PATH=${CONFIG_PATH}"
    exit 1
  fi

  VERSION_CONFIG=$(cat ${CONFIG_PATH} | jq -c -r)

  if [ -z "${VERSION_CONFIG}" -o "${VERSION_CONFIG}" == "null" ]; then
    echo "ERROR: Version config is empty"
    exit 1
  fi

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/create-version"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"stage_id\": ${STAGE_ID}, \"version_config\": ${VERSION_CONFIG}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-version_${TENANT_ID}_${STAGE_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-version_${TENANT_ID}_${STAGE_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  VERSION_ID=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-version_${TENANT_ID}_${STAGE_ID}.json | jq -r .id)
  if [ -z "$VERSION_ID" -o "$VERSION_ID" == "null" ]; then
    echo "ERROR: VERSION_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_VERSION_ID=$VERSION_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Version was created, VERSION_ID=${VERSION_ID}"
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

developer_getStageDashboard() {
  internal_useEnvironment

  TENANT_ID=$1
  STAGE_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${STAGE_ID}" ]; then
    help "developer getStageDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=$TENANT_ID"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE_ID=$STAGE_ID"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-stage-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"stage_id\": ${STAGE_ID}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-stage-dashboard_${TENANT_ID}_${STAGE_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-stage-dashboard_${TENANT_ID}_${STAGE_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-stage-dashboard_${TENANT_ID}_${STAGE_ID}.json || cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-stage-dashboard_${TENANT_ID}_${STAGE_ID}.json | jq
}

developer_getVersionDashboard() {
  internal_useEnvironment

  TENANT_ID=$1
  VERSION_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${VERSION_ID}" ]; then
    help "developer getVersionDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=$TENANT_ID"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using version, VERSION_ID=$VERSION_ID"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-version-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"version_id\": ${VERSION_ID}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-version-dashboard_${TENANT_ID}_${VERSION_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-version-dashboard_${TENANT_ID}_${VERSION_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-version-dashboard_${TENANT_ID}_${VERSION_ID}.json || cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-version-dashboard_${TENANT_ID}_${VERSION_ID}.json | jq
}

developer_buildVersion() {
  internal_useEnvironment

  TENANT_ID=$1
  STAGE_ID=$2
  PROJECT_PATH=$3

  if [ -z "${TENANT_ID}" -o -z "${STAGE_ID}" -o -z "${PROJECT_PATH}" ]; then
    help "developer buildVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE_ID=${STAGE_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project path, PROJECT_PATH=${PROJECT_PATH}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ARCHIVE_PATH=$(eval echo ${OMGSERVERSCTL_DIRECTORY}/versions/version_${TENANT_ID}_${STAGE_ID}.zip)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using archive path, ARCHIVE_PATH=${ARCHIVE_PATH}"

  pushd ${PROJECT_PATH}

  if [ ! -f "config.json" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Version config.json was not found, PROJECT_PATH=${PROJECT_PATH}"
    exit 1
  fi

  if [ ! -f "main.lua" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Version main.lua was not found, PROJECT_PATH=${PROJECT_PATH}"
    exit 1
  fi

  find . -type f -name "*.lua" | zip -@ ${ARCHIVE_PATH}

  popd >> /dev/null

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/build-version"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: multipart/form-data" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -F "tenantId=${TENANT_ID}" \
    -F "stageId=${STAGE_ID}" \
    -F "config.json=@${PROJECT_PATH}/config.json" \
    -F "version.zip=@${ARCHIVE_PATH}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-build-version_${TENANT_ID}_${STAGE_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-build-version_${TENANT_ID}_${STAGE_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  VERSION_ID=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-build-version_${TENANT_ID}_${STAGE_ID}.json | jq -r .id)
  if [ -z "$VERSION_ID" -o "$VERSION_ID" == "null" ]; then
    echo "ERROR: VERSION_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_VERSION_ID=$VERSION_ID" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Version was built, VERSION_ID=${VERSION_ID}"
}

developer_deployVersion() {
  internal_useEnvironment

  TENANT_ID=$1
  VERSION_ID=$2

  if [ -z "${TENANT_ID}" -o -z "${VERSION_ID}" ]; then
    help "developer deployVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT_ID=${TENANT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using version, VERSION_ID=${VERSION_ID}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/deploy-version"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"version_id\": ${VERSION_ID}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-deploy-version_${TENANT_ID}_${VERSION_ID}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-deploy-version_${TENANT_ID}_${VERSION_ID}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deploy was requested"
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
  elif [ "$2" = "createProject" ]; then
    support_createProject $3
  elif [ "$2" = "deleteProject" ]; then
    support_deleteProject $3 $4
  elif [ "$2" = "createDeveloper" ]; then
    support_createDeveloper $3
  elif [ "$2" = "createTenantPermission" ]; then
    support_createTenantPermission $3 $4 $5
  elif [ "$2" = "deleteTenantPermission" ]; then
    support_deleteTenantPermission $3 $4 $5
  elif [ "$2" = "createProjectPermission" ]; then
    support_createProjectPermission $3 $4 $5 $6
  elif [ "$2" = "deleteProjectPermission" ]; then
    support_deleteProjectPermission $3 $4 $5 $6
  elif [ "$2" = "createStagePermission" ]; then
    support_createStagePermission $3 $4 $5 $6
  elif [ "$2" = "deleteStagePermission" ]; then
    support_deleteStagePermission $3 $4 $5 $6
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
  elif [ "$2" = "createProject" ]; then
    developer_createProject $3
  elif [ "$2" = "createVersion" ]; then
    developer_createVersion $3 $4 $5
  elif [ "$2" = "getTenantDashboard" ]; then
    developer_getTenantDashboard $3
  elif [ "$2" = "getStageDashboard" ]; then
    developer_getStageDashboard $3 $4
  elif [ "$2" = "getVersionDashboard" ]; then
    developer_getVersionDashboard $3 $4
  elif [ "$2" = "buildVersion" ]; then
    developer_buildVersion $3 $4 $5
  elif [ "$2" = "deployVersion" ]; then
    developer_deployVersion $3 $4
  else
    help "developer"
  fi
else
  help
fi
