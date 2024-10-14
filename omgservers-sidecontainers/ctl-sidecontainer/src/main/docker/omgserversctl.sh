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
    echo " omgserversctl environment printVariable <variable>"
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
    echo " omgserversctl admin useCredentials <user> <password>"
    if [ "$1" = "admin useCredentials" ]; then
      echo "   produces:"
      echo "     - ADMIN_USER"
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
    echo " omgserversctl support useCredentials <user> <password>"
    if [ "$1" = "support useCredentials" ]; then
      echo "   produces:"
      echo "     - SUPPORT_USER"
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
      echo "     - TENANT"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenant" ]; then
    echo " omgserversctl support deleteTenant <tenant>"
    if [ "$1" = "support deleteTenant" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createProject" ]; then
    echo " omgserversctl support createProject <tenant>"
    if [ "$1" = "support createProject" ]; then
      echo "   produces:"
      echo "     - PROJECT"
      echo "     - STAGE"
      echo "     - SECRET"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteProject" ]; then
    echo " omgserversctl support deleteProject <tenant> <project>"
    if [ "$1" = "support deleteProject" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createDeveloper" ]; then
    echo " omgserversctl support createDeveloper"
    if [ "$1" = "support createDeveloper" ]; then
      echo "   produces:"
      echo "     - DEVELOPER_USER"
      echo "     - DEVELOPER_PASSWORD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenantPermission" ]; then
    echo " omgserversctl support createTenantPermission <tenant> <user> <permission>"
    if [ "$1" = "support createTenantPermission" ]; then
      echo "   tenant_permission:"
      echo "     - PROJECT_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenantPermission" ]; then
    echo " omgserversctl support deleteTenantPermission <tenant> <user> <permission>"
    if [ "$1" = "support deleteTenantPermission" ]; then
      echo "   tenant_permission:"
      echo "     - PROJECT_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createProjectPermission" ]; then
    echo " omgserversctl support createProjectPermission <tenant> <project> <user> <permission>"
    if [ "$1" = "support createProjectPermission" ]; then
      echo "   tenant_project_permission:"
      echo "     - STAGE_MANAGEMENT"
      echo "     - VERSION_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteProjectPermission" ]; then
    echo " omgserversctl support deleteProjectPermission <tenant> <project> <user> <permission>"
    if [ "$1" = "support deleteProjectPermission" ]; then
      echo "   tenant_project_permission:"
      echo "     - STAGE_MANAGEMENT"
      echo "     - VERSION_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createStagePermission" ]; then
    echo " omgserversctl support createStagePermission <tenant> <stage> <user> <permission>"
    if [ "$1" = "support createStagePermission" ]; then
      echo "   tenant_stage_permission:"
      echo "     - DEPLOYMENT_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteStagePermission" ]; then
    echo " omgserversctl support deleteStagePermission <tenant> <stage> <user> <permission>"
    if [ "$1" = "support deleteStagePermission" ]; then
      echo "   tenant_stage_permission:"
      echo "     - DEPLOYMENT_MANAGEMENT"
      echo "     - GETTING_DASHBOARD"
    fi
  fi
  # Developer
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer useCredentials" ]; then
    echo " omgserversctl developer useCredentials <user> <password>"
    if [ "$1" = "developer useCredentials" ]; then
      echo "   produces:"
      echo "     - DEVELOPER_USER"
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
    echo " omgserversctl developer getTenantDashboard <tenant>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createProject" ]; then
    echo " omgserversctl developer createProject <tenant>"
    if [ "$1" = "developer createProject" ]; then
      echo "   produces:"
      echo "     - PROJECT"
      echo "     - STAGE"
      echo "     - SECRET"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getProjectDashboard" ]; then
    echo " omgserversctl developer getProjectDashboard <tenant> <project>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteProject" ]; then
      echo " omgserversctl developer deleteProject <tenant> <project>"
      if [ "$1" = "developer deleteProject" ]; then
        echo "   produces:"
        echo "     - DELETED"
      fi
    fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createStage" ]; then
    echo " omgserversctl developer createStage <tenant> <project>"
    if [ "$1" = "developer createStage" ]; then
      echo "   produces:"
      echo "     - STAGE"
      echo "     - SECRET"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getStageDashboard" ]; then
    echo " omgserversctl developer getStageDashboard <tenant> <stage>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteStage" ]; then
    echo " omgserversctl developer deleteStage <tenant> <stage>"
    if [ "$1" = "developer deleteStage" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createVersion" ]; then
    echo " omgserversctl developer createVersion <tenant> <project> <config_path>"
    if [ "$1" = "developer createVersion" ]; then
      echo "   produces:"
      echo "     - VERSION"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer uploadFilesArchive" ]; then
    echo " omgserversctl developer uploadFilesArchive <tenant> <version> <files_directory_path>"
    if [ "$1" = "developer uploadFilesArchive" ]; then
      echo "   produces:"
      echo "     - FILES_ARCHIVE"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getVersionDashboard" ]; then
    echo " omgserversctl developer getVersionDashboard <tenant> <version>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteVersion" ]; then
    echo " omgserversctl developer deleteVersion <tenant> <version>"
    if [ "$1" = "developer deleteVersion" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deployVersion" ]; then
    echo " omgserversctl developer deployVersion <tenant> <stage> <version>"
    if [ "$1" = "developer deployVersion" ]; then
      echo "   produces:"
      echo "     - DEPLOYMENT"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getDeploymentDashboard" ]; then
    echo " omgserversctl developer getDeploymentDashboard <tenant> <deployment>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteDeployment" ]; then
    echo " omgserversctl developer deleteDeployment <tenant> <deployment>"
    if [ "$1" = "developer deleteDeployment" ]; then
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

  ADMIN_USER=$1
  ADMIN_PASSWORD=$2

  if [ -z "${ADMIN_USER}" -o -z "${ADMIN_PASSWORD}" ]; then
    help "admin useCredentials"
    exit 1
  fi

  echo "export OMGSERVERSCTL_ADMIN_USER=${ADMIN_USER}" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_ADMIN_PASSWORD=${ADMIN_PASSWORD}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Admin credentials were set, ADMIN_USER=${ADMIN_USER}"

  internal_useEnvironment
  admin_createToken
}

admin_printCurrent() {
  internal_useEnvironment

  ADMIN_USER=${OMGSERVERSCTL_ADMIN_USER}
  ADMIN_PASSWORD=${OMGSERVERSCTL_ADMIN_PASSWORD}
  ADMIN_TOKEN=${OMGSERVERSCTL_ADMIN_TOKEN}

  if [ -z "${ADMIN_USER}" -o -z "${ADMIN_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current admin was not set"
    exit 1
  fi

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current admin was found, ADMIN_USER=${ADMIN_USER}, (without token)"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current admin was found, ADMIN_USER=${ADMIN_USER}, (token exists)"
  fi
}

admin_createToken() {
  internal_useEnvironment

  ADMIN_USER=${OMGSERVERSCTL_ADMIN_USER}
  ADMIN_PASSWORD=${OMGSERVERSCTL_ADMIN_PASSWORD}

  if [ -z "${ADMIN_USER}" -o -z "${ADMIN_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current admin credentials were not found"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using admin, ADMIN_USER=${ADMIN_USER}"

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/admin/request/create-token"
  REQUEST="{\"user_id\": \"${ADMIN_USER}\", \"password\": \"${ADMIN_PASSWORD}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/admin-create-token_${ADMIN_USER}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-create-token_${ADMIN_USER}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  ADMIN_TOKEN=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-create-token_${ADMIN_USER}.json | jq -r .raw_token)
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

  SUPPORT_USER=$1
  SUPPORT_PASSWORD=$2

  if [ -z "${SUPPORT_USER}" -o -z "${SUPPORT_PASSWORD}" ]; then
    help "support useCredentials"
    exit 1
  fi

  echo "export OMGSERVERSCTL_SUPPORT_USER=${SUPPORT_USER}" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_SUPPORT_PASSWORD=${SUPPORT_PASSWORD}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Support credentials were set, SUPPORT_USER=${SUPPORT_USER}"

  internal_useEnvironment
  support_createToken
}

support_printCurrent() {
  internal_useEnvironment

  SUPPORT_USER=${OMGSERVERSCTL_SUPPORT_USER}
  SUPPORT_PASSWORD=${OMGSERVERSCTL_SUPPORT_PASSWORD}
  SUPPORT_TOKEN=${OMGSERVERSCTL_SUPPORT_TOKEN}

  if [ -z "${SUPPORT_USER}" -o -z "${SUPPORT_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support was not found"
    exit 1
  fi

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current support was found, SUPPORT_USER=$SUPPORT_USER, (without token)"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current support was found, SUPPORT_USER=$SUPPORT_USER, (token exists)"
  fi
}

support_createToken() {
  internal_useEnvironment

  SUPPORT_USER=$OMGSERVERSCTL_SUPPORT_USER
  SUPPORT_PASSWORD=$OMGSERVERSCTL_SUPPORT_PASSWORD

  if [ -z "${SUPPORT_USER}" -o -z "${SUPPORT_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support credentials were not found"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using support, SUPPORT_USER=$SUPPORT_USER"

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-token"
  REQUEST="{\"user_id\": \"${SUPPORT_USER}\", \"password\": \"${SUPPORT_PASSWORD}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-token_${SUPPORT_USER}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-token_${SUPPORT_USER}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  RAW_TOKEN=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-token_${SUPPORT_USER}.json | jq -r .raw_token)
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

  TENANT=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant.json | jq -r .id)
  if [ -z "$TENANT" -o "$TENANT" == "null" ]; then
    echo "ERROR: TENANT was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT=$TENANT" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant was created, TENANT=$TENANT"
}

support_deleteTenant() {
  internal_useEnvironment

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "support deleteTenant"
    exit 1
  fi

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-tenant"
  REQUEST="{\"tenant_id\": \"${TENANT}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant_${TENANT}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant_${TENANT}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant_${TENANT}.json | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant was deleted, TENANT=${TENANT}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant was not deleted, TENANT=${TENANT}"
  fi
}

support_createProject() {
  internal_useEnvironment

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "support createProject"
    exit 1
  fi

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-project"
  REQUEST="{\"tenant_id\": ${TENANT}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project_${TENANT}.json"

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

  PROJECT=$(cat ${RESPONSE_FILE} | jq -r .tenant_project_id)
  if [ -z "$PROJECT" -o "$PROJECT" == "null" ]; then
    echo "ERROR: PROJECT was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_PROJECT=$PROJECT" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  STAGE=$(cat ${RESPONSE_FILE} | jq -r .tenant_stage_id)
  if [ -z "${STAGE}" -o "${STAGE}" == "null" ]; then
    echo "ERROR: STAGE was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE=${STAGE}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  SECRET=$(cat ${RESPONSE_FILE} | jq -r .tenant_stage_secret)
  if [ -z "${SECRET}" -o "${SECRET}" == "null" ]; then
    echo "ERROR: SECRET was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_SECRET=${SECRET}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was created, PROJECT=${PROJECT}, STAGE=${STAGE}, SECRET=${SECRET}"
}

support_deleteProject() {
  internal_useEnvironment

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "support deleteProject"
    exit 1
  fi

  SUPPORT_TOKEN=${OMGSERVERSCTL_SUPPORT_TOKEN}

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-project"
  REQUEST="{\"tenant_id\": \"${TENANT}\", \"tenant_project_id\": ${PROJECT}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project_${TENANT}_${PROJECT}.json"

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
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was not deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
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
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  USER=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT}.json | jq -r .user_id)
  if [ -z "$USER" -o "$USER" == "null" ]; then
    echo "ERROR: USER was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_USER=$USER" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  PASSWORD=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT}.json | jq -r .password)
  if [ -z "$PASSWORD" -o "$PASSWORD" == "null" ]; then
    echo "ERROR: PASSWORD was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_PASSWORD=$PASSWORD" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Developer was created, DEVELOPER_USER=${USER}, DEVELOPER_PASSWORD=${PASSWORD}"

  internal_useEnvironment
  developer_createToken ${USER} ${PASSWORD}
}

support_createTenantPermission() {
  internal_useEnvironment

  TENANT=$1
  USER=$2
  PERMISSION=$3

  if [ -z "${TENANT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support createTenantPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=${USER}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=${PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-tenant-permissions"
  REQUEST="{\"tenant_id\": ${TENANT}, \"user_id\": ${USER}, \"permissions_to_create\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant-permissions_${TENANT}_${DEVELOPER_USER}_${PERMISSION}.json"

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

  TENANT=$1
  USER=$2
  PERMISSION=$3

  if [ -z "${TENANT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support deleteTenantPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=${USER}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=${PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-tenant-permissions"
  REQUEST="{\"tenant_id\": ${TENANT}, \"user_id\": ${USER}, \"permissions_to_delete\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant-permissions_${TENANT}_${DEVELOPER_USER}_${PERMISSION}.json"

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

support_createProjectPermission() {
  internal_useEnvironment

  TENANT=$1
  PROJECT=$2
  USER=$3
  PERMISSION=$4

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support createProjectPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=${PROJECT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=${USER}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=${PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-project-permissions"
  REQUEST="{\"tenant_id\": ${TENANT}, \"tenant_project_id\": ${PROJECT}, \"user_id\": ${USER}, \"permissions_to_create\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project-permissions_${TENANT}_${PROJECT}_${DEVELOPER_USER}_${PERMISSION}.json"

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

support_deleteProjectPermission() {
  internal_useEnvironment

  TENANT=$1
  PROJECT=$2
  USER=$3
  PERMISSION=$4

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support deleteProjectPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=${PROJECT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=${USER}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=${PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-project-permissions"
  REQUEST="{\"tenant_id\": ${TENANT}, \"tenant_project_id\": ${PROJECT}, \"user_id\": ${USER}, \"permissions_to_delete\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project-permissions_${TENANT}_${PROJECT}_${DEVELOPER_USER}_${PERMISSION}.json"

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

support_createStagePermission() {
  internal_useEnvironment

  TENANT=$1
  STAGE=$2
  USER=$3
  PERMISSION=$4

  if [ -z "${TENANT}" -o -z "${STAGE}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support createStagePermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE=${STAGE}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=${USER}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=${PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/create-stage-permissions"
  REQUEST="{\"tenant_id\": ${TENANT}, \"tenant_stage_id\": ${STAGE}, \"user_id\": ${USER}, \"permissions_to_create\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-stage-permissions_${TENANT}_${STAGE}_${DEVELOPER_USER}_${PERMISSION}.json"

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

support_deleteStagePermission() {
  internal_useEnvironment

  TENANT=$1
  STAGE=$2
  USER=$3
  PERMISSION=$4

  if [ -z "${TENANT}" -o -z "${STAGE}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support deleteStagePermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE=${STAGE}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=${USER}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=${PERMISSION}"

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/support/request/delete-stage-permissions"
  REQUEST="{\"tenant_id\": ${TENANT}, \"tenant_stage_id\": ${STAGE}, \"user_id\": ${USER}, \"permissions_to_delete\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-stage-permissions_${TENANT}_${STAGE}_${DEVELOPER_USER}_${PERMISSION}.json"

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

  DEVELOPER_USER=$1
  DEVELOPER_PASSWORD=$2

  if [ -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    help "developer useCredentials"
    exit 1
  fi

  echo "export OMGSERVERSCTL_DEVELOPER_USER=$DEVELOPER_USER" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_DEVELOPER_PASSWORD=$DEVELOPER_PASSWORD" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Developer credentials were set, DEVELOPER_USER=$DEVELOPER_USER"

  internal_useEnvironment
  developer_createToken ${DEVELOPER_USER} ${DEVELOPER_PASSWORD}
}


developer_printCurrent() {
  internal_useEnvironment

  DEVELOPER_USER=${OMGSERVERSCTL_DEVELOPER_USER}
  DEVELOPER_PASSWORD=${OMGSERVERSCTL_DEVELOPER_PASSWORD}
  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer was not found"
    exit 1
  fi

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current developer was found, DEVELOPER_USER=${DEVELOPER_USER}, (without token)"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current developer was found, DEVELOPER_USER=${DEVELOPER_USER}, (token exists)"
  fi
}

developer_createToken() {
  internal_useEnvironment

  DEVELOPER_USER=${OMGSERVERSCTL_DEVELOPER_USER}
  DEVELOPER_PASSWORD=${OMGSERVERSCTL_DEVELOPER_PASSWORD}

  if [ -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer credentials were not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/create-token"
  REQUEST="{\"user_id\": \"${DEVELOPER_USER}\", \"password\": \"${DEVELOPER_PASSWORD}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-token_${DEVELOPER_USER}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  RAW_TOKEN=$(cat ${RESPONSE_FILE} | jq -r .raw_token)
  if [ -z "$RAW_TOKEN" -o "$RAW_TOKEN" == "null" ]; then
    echo "ERROR: RAW_TOKEN was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_TOKEN=$RAW_TOKEN" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Developer token was created"
}

developer_getTenantDashboard() {
  internal_useEnvironment

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "developer getTenantDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=$TENANT"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-tenant-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-dashboard_${TENANT}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-dashboard_${TENANT}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-dashboard_${TENANT}.json || cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-dashboard_${TENANT}.json | jq
}

developer_createProject() {
  internal_useEnvironment

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "developer createProject"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=$TENANT"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/create-project"
  REQUEST="{\"tenant_id\": ${TENANT}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  PROJECT=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT}.json | jq -r .tenant_project_id)
  if [ -z "$PROJECT" -o "$PROJECT" == "null" ]; then
    echo "ERROR: PROJECT was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_PROJECT=$PROJECT" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  STAGE=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT}.json | jq -r .tenant_stage_id)
  if [ -z "$STAGE" -o "$STAGE" == "null" ]; then
    echo "ERROR: STAGE was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE=$STAGE" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  SECRET=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT}.json | jq -r .tenant_stage_secret)
  if [ -z "$SECRET" -o "$SECRET" == "null" ]; then
    echo "ERROR: SECRET was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_SECRET=${SECRET}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was created, PROJECT=${PROJECT}, STAGE=${STAGE}, SECRET=${SECRET}"
}

developer_getProjectDashboard() {
  internal_useEnvironment

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "developer getProjectDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=${PROJECT}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-project-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT}, \"tenant_project_id\": ${PROJECT}}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-project-dashboard_${TENANT}_${PROJECT}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-project-dashboard_${TENANT}_${PROJECT}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-project-dashboard_${TENANT}_${PROJECT}.json || cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-project-dashboard_${TENANT}_${PROJECT}.json | jq
}

developer_deleteProject() {
  internal_useEnvironment

  TENANT=$1
  PROJECT=$1

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "developer deleteProject"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=${PROJECT}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/developer/request/delete-project"
  REQUEST="{\"tenant_id\": \"${TENANT}\", \"tenant_project_id\": \"${PROJECT}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-project_${TENANT}_${PROJECT}.json"

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
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant project was not deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  fi
}

developer_createStage() {
  internal_useEnvironment

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "developer createStage"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=$TENANT"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=${PROJECT}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/create-stage"
  REQUEST="{\"tenant_id\": ${TENANT}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-stage_${TENANT}_${PROJECT}.json"

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

  STAGE=$(cat ${RESPONSE_FILE} | jq -r .tenant_stage_id)
  if [ -z "$STAGE" -o "$STAGE" == "null" ]; then
    echo "ERROR: STAGE was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE=$STAGE" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  SECRET=$(cat ${RESPONSE_FILE} | jq -r .tenant_stage_secret)
  if [ -z "$SECRET" -o "$SECRET" == "null" ]; then
    echo "ERROR: SECRET was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_SECRET=$SECRET" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant stage was created, STAGE=${STAGE}, SECRET=${SECRET}"
}

developer_getStageDashboard() {
  internal_useEnvironment

  TENANT=$1
  STAGE=$2

  if [ -z "${TENANT}" -o -z "${STAGE}" ]; then
    help "developer getStageDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE=${STAGE}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-stage-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT}, \"tenant_stage_id\": ${STAGE}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-stage-dashboard_${TENANT}_${STAGE}.json"

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

developer_deleteStage() {
  internal_useEnvironment

  TENANT=$1
  STAGE=$1

  if [ -z "${TENANT}" -o -z "${STAGE}" ]; then
    help "developer deleteStage"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE=${STAGE}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/developer/request/delete-stage"
  REQUEST="{\"tenant_id\": \"${TENANT}\", \"tenant_stage_id\": \"${STAGE}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-stage_${TENANT}_${STAGE}.json"

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
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant stage was deleted, TENANT=${TENANT}, STAGE=${STAGE}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant stage was not deleted, TENANT=${TENANT}, STAGE=${STAGE}"
  fi
}

developer_createVersion() {
  internal_useEnvironment

  TENANT=$1
  PROJECT=$2
  CONFIG_PATH=$3

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${CONFIG_PATH}" ]; then
    help "developer createVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=${PROJECT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using config path, CONFIG_PATH=${CONFIG_PATH}"

  if [ ! -f ${CONFIG_PATH} ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Config file was not found, CONFIG_PATH=${CONFIG_PATH}"
    exit 1
  fi

  CONFIG=$(cat ${CONFIG_PATH} | jq -c -r)

  if [ -z "${CONFIG}" -o "${CONFIG}" == "null" ]; then
    echo "ERROR: Tenant version config is empty"
    exit 1
  fi

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/create-version"
  REQUEST="{\"tenant_id\": ${TENANT}, \"tenant_project_id\": ${PROJECT}, \"tenant_version_config\": ${CONFIG}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-version_${TENANT}_${STAGE}.json"

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

  VERSION=$(cat ${RESPONSE_FILE} | jq -r .tenant_version_id)
  if [ -z "$VERSION" -o "$VERSION" == "null" ]; then
    echo "ERROR: VERSION was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_VERSION=${VERSION}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant version was created, VERSION=${VERSION}"
}

developer_uploadFilesArchive() {
  internal_useEnvironment

  TENANT=$1
  VERSION=$2
  FILES_DIRECTORY_PATH=$3

  if [ -z "${TENANT}" -o -z "${VERSION}" -o -z "${FILES_DIRECTORY_PATH}" ]; then
    help "developer uploadFilesArchive"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using version, VERSION=${VERSION}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using filed directory path, FILES_DIRECTORY_PATH=${FILES_DIRECTORY_PATH}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ARCHIVE_PATH=$(eval echo ${OMGSERVERSCTL_DIRECTORY}/versions/version_${TENANT}_${VERSION}.zip)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using archive path, ARCHIVE_PATH=${ARCHIVE_PATH}"

  pushd ${FILES_DIRECTORY_PATH}

  find . -type f -name "*.lua" | zip -@ ${ARCHIVE_PATH}

  popd >> /dev/null

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/upload-files-archive"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-upload-files-archive_${TENANT}_${VERSION}.json"

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: multipart/form-data" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -F "tenantId=${TENANT}" \
    -F "tenantVersionId=${VERSION}" \
    -F "version.zip=@${ARCHIVE_PATH}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  FILES_ARCHIVE=$(cat ${RESPONSE_FILE} | jq -r .tenant_files_archive_id)
  if [ -z "${FILES_ARCHIVE}" -o "${FILES_ARCHIVE}" == "null" ]; then
    echo "ERROR: FILES_ARCHIVE was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_FILES_ARCHIVE=${FILES_ARCHIVE}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant files archive was uploaded, FILES_ARCHIVE=${FILES_ARCHIVE}"
}

developer_getVersionDashboard() {
  internal_useEnvironment

  TENANT=$1
  VERSION=$2

  if [ -z "${TENANT}" -o -z "${VERSION}" ]; then
    help "developer getVersionDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using version, VERSION=${VERSION}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-version-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT}, \"tenant_version_id\": ${VERSION}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-version-dashboard_${TENANT}_${VERSION}.json"

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

developer_deleteVersion() {
  internal_useEnvironment

  TENANT=$1
  VERSION=$1

  if [ -z "${TENANT}" -o -z "${VERSION}" ]; then
    help "developer deleteVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using version, VERSION=${VERSION}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/developer/request/delete-version"
  REQUEST="{\"tenant_id\": \"${TENANT}\", \"tenant_version_id\": \"${VERSION}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-tenant-version_${TENANT}_${VERSION}.json"

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
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant version was deleted, TENANT=${TENANT}, VERSION=${VERSION}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant version was not deleted, TENANT=${TENANT}, VERSION=${VERSION}"
  fi
}

developer_deployVersion() {
  internal_useEnvironment

  TENANT=$1
  STAGE=$2
  VERSION=$3

  if [ -z "${TENANT}" -o -z "${STAGE}" -o -z "${VERSION}" ]; then
    help "developer deployVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE=${STAGE}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using version, VERSION=${VERSION}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/deploy-version"
  REQUEST="{\"tenant_id\": ${TENANT}, \"tenant_stage_id\": ${STAGE}, \"tenant_version_id\": ${VERSION}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-deploy-version_${TENANT}_${STAGE}_${VERSION}.json"

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

  DEPLOYMENT=$(cat ${RESPONSE_FILE} | jq -r .tenant_deployment_id)
  if [ -z "${DEPLOYMENT}" -o "${DEPLOYMENT}" == "null" ]; then
    echo "ERROR: DEPLOYMENT was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEPLOYMENT=${DEPLOYMENT}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant deployment was created, DEPLOYMENT=${DEPLOYMENT}"
}

developer_getDeploymentDashboard() {
  internal_useEnvironment

  TENANT=$1
  DEPLOYMENT=$2

  if [ -z "${TENANT}" -o -z "${DEPLOYMENT}" ]; then
    help "developer getDeploymentDashboard"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using deployment, DEPLOYMENT=${DEPLOYMENT}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_EXTERNAL_URL}/omgservers/v1/entrypoint/developer/request/get-deployment-dashboard"
  REQUEST="{\"tenant_id\": ${TENANT}, \"tenant_deployment_id\": ${DEPLOYMENT}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-deployment-dashboard_${TENANT}_${DEPLOYMENT}.json"

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

developer_deleteDeployment() {
  internal_useEnvironment

  TENANT=$1
  DEPLOYMENT=$2

  if [ -z "${TENANT}" -o -z "${DEPLOYMENT}" ]; then
    help "developer deleteDeployment"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=${TENANT}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using deployment, DEPLOYMENT=${DEPLOYMENT}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_INTERNAL_URL}/omgservers/v1/entrypoint/developer/request/delete-deployment"
  REQUEST="{\"tenant_id\": \"${TENANT}\", \"id\": \"${DEPLOYMENT}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-deployment_${TENANT}_${DEPLOYMENT}.json"

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
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant deployment was deleted, TENANT=${TENANT}, DEPLOYMENT=${DEPLOYMENT}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant deployment was not deleted, TENANT=${TENANT}, DEPLOYMENT=${DEPLOYMENT}"
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
  elif [ "$2" = "getTenantDashboard" ]; then
    developer_getTenantDashboard $3
  elif [ "$2" = "createProject" ]; then
    developer_createProject $3
  elif [ "$2" = "getProjectDashboard" ]; then
    developer_getProjectDashboard $3 $4
  elif [ "$2" = "deleteProject" ]; then
    developer_deleteProject $3 $4
  elif [ "$2" = "createStage" ]; then
    developer_createStage $3 $4
  elif [ "$2" = "getStageDashboard" ]; then
    developer_getStageDashboard $3 $4
  elif [ "$2" = "deleteStage" ]; then
    developer_deleteStage $3 $4
  elif [ "$2" = "createVersion" ]; then
    developer_createVersion $3 $4 $5
  elif [ "$2" = "uploadFilesArchive" ]; then
    developer_uploadFilesArchive $3 $4 $5
  elif [ "$2" = "getVersionDashboard" ]; then
    developer_getVersionDashboard $3 $4
  elif [ "$2" = "deleteVersion" ]; then
    developer_deleteVersion $3 $4
  elif [ "$2" = "deployVersion" ]; then
    developer_deployVersion $3 $4 $5
  elif [ "$2" = "getDeploymentDashboard" ]; then
    developer_getDeploymentDashboard $3 $4
  elif [ "$2" = "deleteDeployment" ]; then
    developer_deleteDeployment $3 $4
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
