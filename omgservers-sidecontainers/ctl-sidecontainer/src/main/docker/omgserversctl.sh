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
  # Localtesting
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting up" ]; then
    echo " omgserversctl localtesting up"
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting down" ]; then
    echo " omgserversctl localtesting down"
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting ps" ]; then
    echo " omgserversctl localtesting ps"
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting reset" ]; then
    echo " omgserversctl localtesting reset"
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting logs" ]; then
    echo " omgserversctl localtesting logs"
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting database" ]; then
    echo " omgserversctl localtesting database"
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting ctl" ]; then
    echo " omgserversctl localtesting ctl"
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting createProject" ]; then
    echo " omgserversctl localtesting createProject"
    if [ "$1" = "localtesting createProject" ]; then
      echo "   produces:"
      echo "     - LOCALTESTING_TENANT_ID"
      echo "     - LOCALTESTING_PROJECT_ID"
      echo "     - LOCALTESTING_STAGE_ID"
      echo "     - LOCALTESTING_STAGE_SECRET"
      echo "     - LOCALTESTING_DEVELOPER_USER_ID"
      echo "     - LOCALTESTING_DEVELOPER_PASSWORD"
    fi
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting printProject" ]; then
    echo " omgserversctl localtesting printProject"
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting buildVersion" ]; then
    echo " omgserversctl localtesting buildVersion <project_path>"
    if [ "$1" = "localtesting buildVersion" ]; then
      echo "   produces:"
      echo "     - LOCALTESTING_VERSION_ID"
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
      echo "     - GET_DASHBOARD"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenantPermission" ]; then
    echo " omgserversctl support deleteTenantPermission <tenant_id> <user_id> <tenant_permission>"
    if [ "$1" = "support deleteTenantPermission" ]; then
      echo "   tenant_permission:"
      echo "     - PROJECT_MANAGEMENT"
      echo "     - GET_DASHBOARD"
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
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteStagePermission" ]; then
    echo " omgserversctl support deleteStagePermission <tenant_id> <stage_id> <user_id> <stage_permission>"
    if [ "$1" = "support deleteStagePermission" ]; then
      echo "   stage_permission:"
      echo "     - VERSION_MANAGEMENT"
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
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getTenantDashboard" ]; then
    echo " omgserversctl developer getTenantDashboard <tenant_id>"
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer buildVersion" ]; then
    echo " omgserversctl developer buildVersion <tenant_id> <stage_id> <project_path>"
    if [ "$1" = "developer buildVersion" ]; then
      echo "   produces:"
      echo "     - VERSION_ID"
    fi
  fi
}

logs() {
  cat .omgserversctl/logs
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

  echo "export OMGSERVERSCTL_ENVIRONMENT_NAME=${ENVIRONMENT_NAME}" >> .omgserversctl/environment
  echo "export OMGSERVERSCTL_EXTERNAL_URL=${EXTERNAL_URL}" >> .omgserversctl/environment
  echo "export OMGSERVERSCTL_INTERNAL_URL=${INTERNAL_URL}" >> .omgserversctl/environment

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

# LOCALTESTING

localtesting_up() {
  if [ ! -d .omgserversctl/localtesting ]; then
    mkdir -p .omgserversctl/localtesting
    curl -o .omgserversctl/localtesting/compose.yaml https://raw.githubusercontent.com/OMGSERVERS/omgservers/main/omgservers-environments/development-environment/src/compose.yaml
    curl -o .omgserversctl/localtesting/.env https://raw.githubusercontent.com/OMGSERVERS/omgservers/main/omgservers-environments/development-environment/src/.env
  fi

  docker compose -p omgservers -f .omgserversctl/localtesting/compose.yaml up --remove-orphans -d
  docker compose -p omgservers ps
}

localtesting_down() {
  read -p "Continue (y/n)? : " ANSWER
  if [ "${ANSWER}" == "y" ]; then
    docker compose -p omgservers down -v
    : > .omgserversctl/environment
  else
    echo "Operation was cancelled"
  fi
}

localtesting_ps() {
  docker compose -p omgservers ps
}

localtesting_reset() {
  read -p 'Continue (y/n)? : ' ANSWER
  if [ "${ANSWER}" == "y" ]; then
    docker compose -p omgservers down -v
    rm -rf .omgserversctl/localtesting

    localtesting_up
  else
    echo "Operation was cancelled"
  fi
}

localtesting_logs() {
  docker compose -p omgservers logs $@
}

localtesting_database() {
  docker compose -p omgservers exec database psql
}

localtesting_ctl() {
  docker compose -p omgservers exec ctl /bin/bash
}

localtesting_createProject() {
  internal_useEnvironment

  support_createToken
  support_createTenant

  TENANT_ID=$(environment_printVariable TENANT_ID)
  if [ -z "${TENANT_ID}" ]; then
    echo "TENANT_ID was not found"
    exit 1
  fi

  support_createProject ${TENANT_ID}

  PROJECT_ID=$(environment_printVariable PROJECT_ID)
  if [ -z "${PROJECT_ID}" ]; then
    echo "PROJECT_ID was not found"
    exit 1
  fi

  STAGE_ID=$(environment_printVariable STAGE_ID)
  if [ -z "${STAGE_ID}" ]; then
    echo "STAGE_ID was not found"
    exit 1
  fi

  STAGE_SECRET=$(environment_printVariable STAGE_SECRET)
  if [ -z "${STAGE_SECRET}" ]; then
    echo "STAGE_SECRET was not found"
    exit 1
  fi

  support_createDeveloper

  DEVELOPER_USER_ID=$(environment_printVariable DEVELOPER_USER_ID)
  if [ -z "${DEVELOPER_USER_ID}" ]; then
    echo "DEVELOPER_USER_ID was not found"
    exit 1
  fi

  DEVELOPER_PASSWORD=$(environment_printVariable DEVELOPER_PASSWORD)
  if [ -z "${DEVELOPER_PASSWORD}" ]; then
    echo "DEVELOPER_PASSWORD was not found"
    exit 1
  fi

  support_createTenantPermission ${TENANT_ID} ${DEVELOPER_USER_ID} GET_DASHBOARD
  support_createStagePermission ${TENANT_ID} ${STAGE_ID} ${DEVELOPER_USER_ID} VERSION_MANAGEMENT

  developer_useCredentials ${DEVELOPER_USER_ID} ${DEVELOPER_PASSWORD}

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was created:"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) LOCALTESTING_TENANT_ID=${TENANT_ID}, LOCALTESTING_PROJECT_ID=${PROJECT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) LOCALTESTING_STAGE_ID=${STAGE_ID}, LOCALTESTING_STAGE_SECRET=${STAGE_SECRET}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) LOCALTESTING_DEVELOPER_USER_ID=${DEVELOPER_USER_ID}, LOCALTESTING_DEVELOPER_PASSWORD=${DEVELOPER_PASSWORD}"

  echo "export OMGSERVERSCTL_LOCALTESTING_TENANT_ID=${TENANT_ID}" >> .omgserversctl/environment
  echo "export OMGSERVERSCTL_LOCALTESTING_PROJECT_ID=${PROJECT_ID}" >> .omgserversctl/environment
  echo "export OMGSERVERSCTL_LOCALTESTING_STAGE_ID=${STAGE_ID}" >> .omgserversctl/environment
  echo "export OMGSERVERSCTL_LOCALTESTING_STAGE_SECRET=${STAGE_SECRET}" >> .omgserversctl/environment
  echo "export OMGSERVERSCTL_LOCALTESTING_DEVELOPER_USER_ID=${DEVELOPER_USER_ID}" >> .omgserversctl/environment
  echo "export OMGSERVERSCTL_LOCALTESTING_DEVELOPER_PASSWORD=${DEVELOPER_PASSWORD}" >> .omgserversctl/environment
}

localtesting_printProject() {
  internal_useEnvironment

  LOCALTESTING_TENANT_ID=$(environment_printVariable LOCALTESTING_TENANT_ID)
  LOCALTESTING_PROJECT_ID=$(environment_printVariable LOCALTESTING_PROJECT_ID)
  LOCALTESTING_STAGE_ID=$(environment_printVariable LOCALTESTING_STAGE_ID)
  LOCALTESTING_STAGE_SECRET=$(environment_printVariable LOCALTESTING_STAGE_SECRET)
  LOCALTESTING_DEVELOPER_USER_ID=$(environment_printVariable LOCALTESTING_DEVELOPER_USER_ID)
  LOCALTESTING_DEVELOPER_PASSWORD=$(environment_printVariable LOCALTESTING_DEVELOPER_PASSWORD)

  if [ -z "${LOCALTESTING_TENANT_ID}" -o \
       -z "${LOCALTESTING_PROJECT_ID}" -o \
       -z "${LOCALTESTING_STAGE_ID}" -o \
       -z "${LOCALTESTING_STAGE_SECRET}" -o \
       -z "${LOCALTESTING_DEVELOPER_USER_ID}" -o \
       -z "${LOCALTESTING_DEVELOPER_PASSWORD}" ]; then
    echo "Project was not found"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was found:"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) LOCALTESTING_TENANT_ID=${LOCALTESTING_TENANT_ID}, LOCALTESTING_PROJECT_ID=${LOCALTESTING_PROJECT_ID}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) LOCALTESTING_STAGE_ID=${LOCALTESTING_STAGE_ID}, LOCALTESTING_STAGE_SECRET=${LOCALTESTING_STAGE_SECRET}"
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) LOCALTESTING_DEVELOPER_USER_ID=${LOCALTESTING_DEVELOPER_USER_ID}, LOCALTESTING_DEVELOPER_PASSWORD=${LOCALTESTING_DEVELOPER_PASSWORD}"
}

localtesting_buildVersion() {
  internal_useEnvironment

  PROJECT_PATH=$1

  if [ -z "${PROJECT_PATH}" ]; then
    help "localtesting buildVersion"
    exit 1
  fi

  LOCALTESTING_TENANT_ID=$(environment_printVariable LOCALTESTING_TENANT_ID)
  if [ -z "${LOCALTESTING_TENANT_ID}" ]; then
    echo "LOCALTESTING_TENANT_ID was not found"
    exit 1
  fi

  LOCALTESTING_PROJECT_ID=$(environment_printVariable LOCALTESTING_PROJECT_ID)
  if [ -z "${LOCALTESTING_PROJECT_ID}" ]; then
    echo "LOCALTESTING_PROJECT_ID was not found"
    exit 1
  fi

  LOCALTESTING_STAGE_ID=$(environment_printVariable LOCALTESTING_STAGE_ID)
  if [ -z "${LOCALTESTING_STAGE_ID}" ]; then
    echo "LOCALTESTING_STAGE_ID was not found"
    exit 1
  fi

  LOCALTESTING_DEVELOPER_USER_ID=$(environment_printVariable LOCALTESTING_DEVELOPER_USER_ID)
  if [ -z "${LOCALTESTING_DEVELOPER_USER_ID}" ]; then
    echo "LOCALTESTING_DEVELOPER_USER_ID was not found"
    exit 1
  fi

  LOCALTESTING_DEVELOPER_PASSWORD=$(environment_printVariable LOCALTESTING_DEVELOPER_PASSWORD)
  if [ -z "${LOCALTESTING_DEVELOPER_PASSWORD}" ]; then
    echo "LOCALTESTING_DEVELOPER_PASSWORD was not found"
    exit 1
  fi

  developer_useCredentials ${LOCALTESTING_DEVELOPER_USER_ID} ${LOCALTESTING_DEVELOPER_PASSWORD}
  developer_buildVersion ${LOCALTESTING_TENANT_ID} ${LOCALTESTING_STAGE_ID} ${PROJECT_PATH}

  LOCALTESTING_VERSION_ID=$(environment_printVariable VERSION_ID)
  if [ -z "${LOCALTESTING_VERSION_ID}" ]; then
    echo "LOCALTESTING_VERSION_ID was not found"
    exit 1
  fi
  echo "export OMGSERVERSCTL_LOCALTESTING_VERSION_ID=$LOCALTESTING_VERSION_ID" >> .omgserversctl/environment
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

  echo "export OMGSERVERSCTL_ADMIN_USER_ID=${ADMIN_USER_ID}" >> .omgserversctl/environment
  echo "export OMGSERVERSCTL_ADMIN_PASSWORD=${ADMIN_PASSWORD}" >> .omgserversctl/environment

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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/admin-create-token_${ADMIN_USER_ID}.json)

  cat .omgserversctl/temp/admin-create-token_${ADMIN_USER_ID}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  ADMIN_TOKEN=$(cat .omgserversctl/temp/admin-create-token_${ADMIN_USER_ID}.json | jq -r .raw_token)
  if [ -z "$ADMIN_TOKEN" -o "$ADMIN_TOKEN" == "null" ]; then
    echo "ERROR: ADMIN_TOKEN was not received"
    exit 1
  fi

  echo "export OMGSERVERSCTL_ADMIN_TOKEN=$ADMIN_TOKEN" >> .omgserversctl/environment

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

  echo "export OMGSERVERSCTL_SUPPORT_USER_ID=${SUPPORT_USER_ID}" >> .omgserversctl/environment
  echo "export OMGSERVERSCTL_SUPPORT_PASSWORD=${SUPPORT_PASSWORD}" >> .omgserversctl/environment

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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-create-token_${SUPPORT_USER_ID}.json)

  cat .omgserversctl/temp/support-create-token_${SUPPORT_USER_ID}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  RAW_TOKEN=$(cat .omgserversctl/temp/support-create-token_${SUPPORT_USER_ID}.json | jq -r .raw_token)
  if [ -z "$RAW_TOKEN" -o "$RAW_TOKEN" == "null" ]; then
    echo "ERROR: RAW_TOKEN was not received"
    exit 1
  fi

  echo "export OMGSERVERSCTL_SUPPORT_TOKEN=$RAW_TOKEN" >> .omgserversctl/environment

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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-create-tenant.json)

  cat .omgserversctl/temp/support-create-tenant.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  TENANT_ID=$(cat .omgserversctl/temp/support-create-tenant.json | jq -r .id)
  if [ -z "$TENANT_ID" -o "$TENANT_ID" == "null" ]; then
    echo "ERROR: TENANT_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT_ID=$TENANT_ID" >> .omgserversctl/environment

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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-delete-tenant_${TENANT_ID}.json)

  cat .omgserversctl/temp/support-delete-tenant_${TENANT_ID}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  DELETED=$(cat .omgserversctl/temp/support-delete-tenant_${TENANT_ID}.json | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> .omgserversctl/environment

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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-create-project_${TENANT_ID}.json)

  cat .omgserversctl/temp/support-create-project_${TENANT_ID}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  PROJECT_ID=$(cat .omgserversctl/temp/support-create-project_${TENANT_ID}.json | jq -r .project_id)
  if [ -z "$PROJECT_ID" -o "$PROJECT_ID" == "null" ]; then
    echo "ERROR: PROJECT_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_PROJECT_ID=$PROJECT_ID" >> .omgserversctl/environment

  STAGE_ID=$(cat .omgserversctl/temp/support-create-project_${TENANT_ID}.json | jq -r .stage_id)
  if [ -z "${STAGE_ID}" -o "${STAGE_ID}" == "null" ]; then
    echo "ERROR: STAGE_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE_ID=${STAGE_ID}" >> .omgserversctl/environment

  STAGE_SECRET=$(cat .omgserversctl/temp/support-create-project_${TENANT_ID}.json | jq -r .stage_secret)
  if [ -z "${STAGE_SECRET}" -o "${STAGE_SECRET}" == "null" ]; then
    echo "ERROR: STAGE_SECRET was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE_SECRET=${STAGE_SECRET}" >> .omgserversctl/environment

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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-delete-project_${TENANT_ID}_${PROJECT_ID}.json)

  cat .omgserversctl/temp/support-delete-project_${TENANT_ID}_${PROJECT_ID}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  DELETED=$(cat .omgserversctl/temp/support-delete-project_${TENANT_ID}_${PROJECT_ID}.json | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> .omgserversctl/environment

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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-create-developer_${TENANT_ID}.json)

  cat .omgserversctl/temp/support-create-developer_${TENANT_ID}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  USER_ID=$(cat .omgserversctl/temp/support-create-developer_${TENANT_ID}.json | jq -r .user_id)
  if [ -z "$USER_ID" -o "$USER_ID" == "null" ]; then
    echo "ERROR: USER_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_USER_ID=$USER_ID" >> .omgserversctl/environment

  PASSWORD=$(cat .omgserversctl/temp/support-create-developer_${TENANT_ID}.json | jq -r .password)
  if [ -z "$PASSWORD" -o "$PASSWORD" == "null" ]; then
    echo "ERROR: PASSWORD was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_PASSWORD=$PASSWORD" >> .omgserversctl/environment

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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-create-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat .omgserversctl/temp/support-create-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat .omgserversctl/temp/support-create-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -c -r .created_permissions)
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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-delete-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat .omgserversctl/temp/support-delete-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat .omgserversctl/temp/support-delete-tenant-permissions_${TENANT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -r .deleted_permissions)
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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-create-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat .omgserversctl/temp/support-create-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat .omgserversctl/temp/support-create-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -c -r .created_permissions)
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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-delete-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat .omgserversctl/temp/support-delete-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat .omgserversctl/temp/support-delete-project-permissions_${TENANT_ID}_${PROJECT_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -r .deleted_permissions)
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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-create-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat .omgserversctl/temp/support-create-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat .omgserversctl/temp/support-create-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -c -r .created_permissions)
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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/support-delete-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json)

  cat .omgserversctl/temp/support-delete-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat .omgserversctl/temp/support-delete-stage-permissions_${TENANT_ID}_${STAGE_ID}_${DEVELOPER_USER_ID}_${TENANT_PERMISSION}.json | jq -r .deleted_permissions)
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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/developer-create-token_${DEVELOPER_USER_ID}.json)

  cat .omgserversctl/temp/developer-create-token_${DEVELOPER_USER_ID}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  RAW_TOKEN=$(cat .omgserversctl/temp/developer-create-token_${DEVELOPER_USER_ID}.json | jq -r .raw_token)
  if [ -z "$RAW_TOKEN" -o "$RAW_TOKEN" == "null" ]; then
    echo "ERROR: RAW_TOKEN was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_TOKEN=$RAW_TOKEN" >> .omgserversctl/environment

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

  echo "export OMGSERVERSCTL_DEVELOPER_USER_ID=$DEVELOPER_USER_ID" >> .omgserversctl/environment
  echo "export OMGSERVERSCTL_DEVELOPER_PASSWORD=$DEVELOPER_PASSWORD" >> .omgserversctl/environment

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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/developer-create-project_${TENANT_ID}.json)

  cat .omgserversctl/temp/developer-create-project_${TENANT_ID}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  PROJECT_ID=$(cat .omgserversctl/temp/developer-create-project_${TENANT_ID}.json | jq -r .project_id)
  if [ -z "$PROJECT_ID" -o "$PROJECT_ID" == "null" ]; then
    echo "ERROR: PROJECT_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_PROJECT_ID=$PROJECT_ID" >> .omgserversctl/environment

  STAGE_ID=$(cat .omgserversctl/temp/developer-create-project_${TENANT_ID}.json | jq -r .stage_id)
  if [ -z "$STAGE_ID" -o "$STAGE_ID" == "null" ]; then
    echo "ERROR: STAGE_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE_ID=$STAGE_ID" >> .omgserversctl/environment

  STAGE_SECRET=$(cat .omgserversctl/temp/developer-create-project_${TENANT_ID}.json | jq -r .secret)
  if [ -z "$STAGE_SECRET" -o "$STAGE_SECRET" == "null" ]; then
    echo "ERROR: STAGE_SECRET was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE_SECRET=$STAGE_SECRET" >> .omgserversctl/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was created, PROJECT_ID=${PROJECT_ID}, STAGE_ID=${STAGE_ID}, STAGE_SECRET=${STAGE_SECRET}"
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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs
  echo $REQUEST >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o .omgserversctl/temp/developer-get-tenant-dashboard_${TENANT_ID}.json)

  cat .omgserversctl/temp/developer-get-tenant-dashboard_${TENANT_ID}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  type open > /dev/null && open .omgserversctl/temp/developer-get-tenant-dashboard_${TENANT_ID}.json || cat .omgserversctl/temp/developer-get-tenant-dashboard_${TENANT_ID}.json | jq
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

  ARCHIVE_PATH=$(eval echo .omgserversctl/versions/version_${TENANT_ID}_${STAGE_ID}.zip)
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

  echo >> .omgserversctl/logs
  echo $ENDPOINT >> .omgserversctl/logs

  HTTP_CODE=$(curl -s -S -X PUT -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: multipart/form-data" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -F "tenantId=${TENANT_ID}" \
    -F "stageId=${STAGE_ID}" \
    -F "config.json=@${PROJECT_PATH}/config.json" \
    -F "version.zip=@${ARCHIVE_PATH}" \
    -o .omgserversctl/temp/developer-build-version_${TENANT_ID}_${STAGE_ID}.json)

  cat .omgserversctl/temp/developer-build-version_${TENANT_ID}_${STAGE_ID}.json >> .omgserversctl/logs
  echo >> .omgserversctl/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=${HTTP_CODE}, ${ENDPOINT}"
    tail -2 .omgserversctl/logs
    exit 1
  fi

  VERSION_ID=$(cat .omgserversctl/temp/developer-build-version_${TENANT_ID}_${STAGE_ID}.json | jq -r .id)
  if [ -z "$VERSION_ID" -o "$VERSION_ID" == "null" ]; then
    echo "ERROR: VERSION_ID was not received"
    exit 1
  fi
  echo "export OMGSERVERSCTL_VERSION_ID=$VERSION_ID" >> .omgserversctl/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Version was built, VERSION_ID=${VERSION_ID}"
}

# INTERNAL

internal_useEnvironment() {
  source .omgserversctl/environment

  if [ -z "${OMGSERVERSCTL_ENVIRONMENT_NAME}" ]; then
    echo "$(date) $(echo unknown) ERROR: Environment was not configured"
    exit 1
  else
    source .omgserversctl/environment
  fi
}

# MAIN

if [ ! -d ".omgserversctl/temp" ]; then
  mkdir -p .omgserversctl/temp
fi
if [ ! -d ".omgserversctl/versions" ]; then
  mkdir -p .omgserversctl/versions
fi
if [ ! -f ".omgserversctl/environment" ]; then
  touch .omgserversctl/environment
fi
if [ ! -f ".omgserversctl/logs" ]; then
  touch .omgserversctl/logs
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

# Localtesting
if [ "$1" = "localtesting" ]; then
  if [ "$2" = "up" ]; then
    localtesting_up
  elif [ "$2" = "down" ]; then
    localtesting_down
  elif [ "$2" = "ps" ]; then
    localtesting_ps
  elif [ "$2" = "reset" ]; then
    localtesting_reset
  elif [ "$2" = "logs" ]; then
    localtesting_logs "${@:3}"
  elif [ "$2" = "database" ]; then
    localtesting_database
  elif [ "$2" = "ctl" ]; then
    localtesting_ctl
  elif [ "$2" = "createProject" ]; then
    localtesting_createProject
  elif [ "$2" = "printProject" ]; then
    localtesting_printProject
  elif [ "$2" = "buildVersion" ]; then
    localtesting_buildVersion $3
  else
    help "localtesting"
  fi
# Admin
elif [ "$1" = "admin" ]; then
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
  elif [ "$2" = "getTenantDashboard" ]; then
    developer_getTenantDashboard $3
  elif [ "$2" = "buildVersion" ]; then
    developer_buildVersion $3 $4 $5
  else
    help "developer"
  fi
else
  help
fi
