#!/bin/bash
set -e

internal_useEnvironment() {
  source ${OMGSERVERSCTL_DIRECTORY}/environment
}

internal_ensureEnvironment() {
  internal_useEnvironment
  if [ -z "${OMGSERVERSCTL_SERVICE_URL}" ]; then
    echo "$(date) unknown ERROR: Environment was not set" >&2
    exit 1
  fi
}

internal_print_command() {
  printf "  %-85s %s\n" "$1" "$2"
}

# HELP

help() {
  echo "OMGSERVERS ctl, v1.0.0"
  echo "Usage:"
  if [ -z "$1" -o "$1" = "help" ]; then
    internal_print_command " $0 help" "Display this help message."
  fi
  if [ -z "$1" -o "$1" = "logs" ]; then
    internal_print_command " $0 logs" "Show ctl logs"
  fi
  # Environment
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment reset" ]; then
    internal_print_command " $0 environment reset" "Reset the current environment."
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment printCurrent" ]; then
    internal_print_command " $0 environment printCurrent" "Print all environment variables."
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment printVariable" ]; then
    internal_print_command " $0 environment printVariable <variable>" "Print the value of a specific variable."
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment useEnvironment" ]; then
    internal_print_command " $0 environment useEnvironment <name> <service_url>" "Set up an environment with the specified URL."
    if [ "$1" = "environment useEnvironment" ]; then
      echo "   produces:"
      echo "     - ENVIRONMENT_NAME"
      echo "     - SERVICE_URL"
    fi
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment useDemoServer" ]; then
    internal_print_command " $0 environment useDemoServer" "Use the demo server environment."
    if [ "$1" = "environment useDemoServer" ]; then
      echo "   produces:"
      echo "     - ENVIRONMENT_NAME"
      echo "     - SERVICE_URL"
    fi
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment useLocalServer" ]; then
    internal_print_command " $0 environment useLocalServer" "Use the local server environment."
    if [ "$1" = "environment useLocalServer" ]; then
      echo "   produces:"
      echo "     - ENVIRONMENT_NAME"
      echo "     - SERVICE_URL"
    fi
  fi
  # Admin
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin printCurrent" ]; then
    internal_print_command " $0 admin printCurrent" "Print the current admin user."
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin createToken" ]; then
    internal_print_command " $0 admin createToken <user> <password>" "Authenticate as an admin using credentials."
    if [ "$1" = "admin createToken" ]; then
      echo "   produces:"
      echo "     - ADMIN_TOKEN"
    fi
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin calculateShard" ]; then
    internal_print_command " $0 admin calculateShard <shard_key>" "Calculate the shard based on the provided shard key."
    if [ "$1" = "admin calculateShard" ]; then
      echo "   produces:"
      echo "     - SHARD_INDEX"
      echo "     - SERVER_URI"
    fi
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin generateId" ]; then
    internal_print_command " $0 admin generateId" "Generate a unique identifier."
    if [ "$1" = "admin generateId" ]; then
      echo "   produces:"
      echo "     - GENERATED_ID"
    fi
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin bcryptHash" ]; then
    internal_print_command " $0 admin bcryptHash <value>" "Generate a bcrypt hash of the given value."
    if [ "$1" = "admin bcryptHash" ]; then
      echo "   produces:"
      echo "     - BCRYPTED_HASH"
    fi
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin pingDockerHost" ]; then
    internal_print_command " $0 admin pingDockerHost <docker_daemon_uri>" "Check the status of the specified Docker daemon."
    if [ "$1" = "admin pingDockerHost" ]; then
      echo "   produces:"
      echo "     - SUCCESSFUL"
    fi
  fi
  # Support
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support printCurrent" ]; then
    internal_print_command " $0 support printCurrent" "Print the current support user."
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createToken" ]; then
    internal_print_command " $0 support createToken <user> <password>" "Authenticate as a support user using credentials."
    if [ "$1" = "support createToken" ]; then
      echo "   produces:"
      echo "     - SUPPORT_TOKEN"
    fi
  fi
  # Tenant
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenant" ]; then
    internal_print_command " $0 support createTenant" "Create a new tenant."
    if [ "$1" = "support createTenant" ]; then
      echo "   produces:"
      echo "     - TENANT"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenantAlias" ]; then
    internal_print_command " $0 support createTenantAlias <tenant_id> <alias>" "Assign an alias to a tenant."
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenant" ]; then
    internal_print_command " $0 support deleteTenant <tenant>" "Delete the specified tenant."
    if [ "$1" = "support deleteTenant" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  # Project
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createProject" ]; then
    internal_print_command " $0 support createProject <tenant>" "Create a new project under the specified tenant."
    if [ "$1" = "support createProject" ]; then
      echo "   produces:"
      echo "     - PROJECT"
      echo "     - STAGE"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createProjectAlias" ]; then
    internal_print_command " $0 support createProjectAlias <tenant> <project_id> <alias>" "Assign an alias to a project."
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteProject" ]; then
    internal_print_command " $0 support deleteProject <tenant> <project>" "Delete the specified project."
    if [ "$1" = "support deleteProject" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  # Stage
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createStage" ]; then
    internal_print_command " $0 support createStage <tenant> <project>" "Create a new stage for a project."
    if [ "$1" = "support createStage" ]; then
      echo "   produces:"
      echo "     - STAGE"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createStageAlias" ]; then
    internal_print_command " $0 support createStageAlias <tenant> <stage_id> <alias>" "Assign an alias to a stage."
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteStage" ]; then
    internal_print_command " $0 support deleteStage <tenant> <project> <stage>" "Delete the specified stage."
    if [ "$1" = "support deleteStage" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  # Developer
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createDeveloper" ]; then
    internal_print_command " $0 support createDeveloper" "Create a new developer account."
    if [ "$1" = "support createDeveloper" ]; then
      echo "   produces:"
      echo "     - DEVELOPER_USER"
      echo "     - DEVELOPER_PASSWORD"
    fi
  fi
  # Permission
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenantPermission" ]; then
    internal_print_command " $0 support createTenantPermission <tenant> <user> <permission>" "Grant a user permission for a tenant."
    if [ "$1" = "support createTenantPermission" ]; then
      echo "   permission:"
      echo "     - PROJECT_MANAGER"
      echo "     - TENANT_VIEWER"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenantPermission" ]; then
    internal_print_command " $0 support deleteTenantPermission <tenant> <user> <permission>" "Remove a user's tenant permission."
    if [ "$1" = "support deleteTenantPermission" ]; then
      echo "   permission:"
      echo "     - PROJECT_MANAGER"
      echo "     - TENANT_VIEWER"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createProjectPermission" ]; then
    internal_print_command " $0 support createProjectPermission <tenant> <project> <user> <permission>" "Grant a user permission for a project."
    if [ "$1" = "support createProjectPermission" ]; then
      echo "   permission:"
      echo "     - STAGE_MANAGER"
      echo "     - VERSION_MANAGER"
      echo "     - PROJECT_VIEWER"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteProjectPermission" ]; then
    internal_print_command " $0 support deleteProjectPermission <tenant> <project> <user> <permission>" "Remove a user's project permission."
    if [ "$1" = "support deleteProjectPermission" ]; then
      echo "   permission:"
      echo "     - STAGE_MANAGER"
      echo "     - VERSION_MANAGER"
      echo "     - PROJECT_VIEWER"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createStagePermission" ]; then
    internal_print_command " $0 support createStagePermission <tenant> <project> <stage> <user> <permission>" "Grant a user permission for a stage."
    if [ "$1" = "support createStagePermission" ]; then
      echo "   permission:"
      echo "     - DEPLOYMENT_MANAGER"
      echo "     - STAGE_VIEWER"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteStagePermission" ]; then
    internal_print_command " $0 support deleteStagePermission <tenant> <project> <stage> <user> <permission>" "Remove a user's stage permission."
    if [ "$1" = "support deleteStagePermission" ]; then
      echo "   permission:"
      echo "     - DEPLOYMENT_MANAGER"
      echo "     - STAGE_VIEWER"
    fi
  fi
  # Developer
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer printCurrent" ]; then
    internal_print_command " $0 developer printCurrent" "Print the current developer user."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createToken" ]; then
    internal_print_command " $0 developer createToken <user> <password>" "Authenticate as a developer using credentials."
    if [ "$1" = "developer createToken" ]; then
      echo "   produces:"
      echo "     - DEVELOPER_TOKEN"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getTenantDetails" ]; then
    internal_print_command " $0 developer getTenantDetails <tenant>" "Retrieve details of the specified tenant."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createProject" ]; then
    internal_print_command " $0 developer createProject <tenant>" "Create a new project under the specified tenant."
    if [ "$1" = "developer createProject" ]; then
      echo "   produces:"
      echo "     - PROJECT"
      echo "     - STAGE"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createProjectAlias" ]; then
    internal_print_command " $0 developer createProjectAlias <tenant> <project_id> <alias>" "Assign an alias to a project."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getProjectDetails" ]; then
    internal_print_command " $0 developer getProjectDetails <tenant> <project>" "Retrieve details of the specified project."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteProject" ]; then
      internal_print_command " $0 developer deleteProject <tenant> <project>" "Delete the specified project."
      if [ "$1" = "developer deleteProject" ]; then
        echo "   produces:"
        echo "     - DELETED"
      fi
    fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createStage" ]; then
    internal_print_command " $0 developer createStage <tenant> <project>" "Create a new stage for a project."
    if [ "$1" = "developer createStage" ]; then
      echo "   produces:"
      echo "     - STAGE"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createStageAlias" ]; then
    internal_print_command " $0 developer createStageAlias <tenant> <stage_id> <alias>" "Assign an alias to a stage."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getStageDetails" ]; then
    internal_print_command " $0 developer getStageDetails <tenant> <project> <stage>" "Retrieve details of the specified stage."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteStage" ]; then
    internal_print_command " $0 developer deleteStage <tenant> <project> <stage>" "Delete the specified stage."
    if [ "$1" = "developer deleteStage" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createVersion" ]; then
    internal_print_command " $0 developer createVersion <tenant> <project> <config_path>" "Create a new version using the specified configuration."
    if [ "$1" = "developer createVersion" ]; then
      echo "   produces:"
      echo "     - VERSION"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer uploadFilesArchive" ]; then
    internal_print_command " $0 developer uploadFilesArchive <tenant> <version> <files_directory_path>" "Upload an archive of files for a version."
    if [ "$1" = "developer uploadFilesArchive" ]; then
      echo "   produces:"
      echo "     - FILES_ARCHIVE"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getVersionDetails" ]; then
    internal_print_command " $0 developer getVersionDetails <tenant> <version>" "Retrieve details of the specified version."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteVersion" ]; then
    internal_print_command " $0 developer deleteVersion <tenant> <version>" "Delete the specified version."
    if [ "$1" = "developer deleteVersion" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deployVersion" ]; then
    internal_print_command " $0 developer deployVersion <tenant> <project> <stage> <version>" "Deploy a version to the specified stage."
    if [ "$1" = "developer deployVersion" ]; then
      echo "   produces:"
      echo "     - DEPLOYMENT"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getDeploymentDetails" ]; then
    internal_print_command " $0 developer getDeploymentDetails <tenant> <deployment>" "Retrieve details of the specified deployment."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteDeployment" ]; then
    internal_print_command " $0 developer deleteDeployment <tenant> <deployment>" "Delete the specified deployment."
    if [ "$1" = "developer deleteDeployment" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createLobbyRequest" ]; then
    internal_print_command " $0 developer createLobbyRequest <tenant> <deployment>" "Create a lobby request for the specified deployment."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteLobby" ]; then
    internal_print_command " $0 developer deleteLobby <lobby>" "Delete the specified lobby."
    if [ "$1" = "developer deleteLobby" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createMatchmakerRequest" ]; then
    internal_print_command " $0 developer createMatchmakerRequest <tenant> <deployment>" "Create a matchmaker request for the specified deployment."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteMatchmaker" ]; then
    internal_print_command " $0 developer deleteMatchmaker <matchmaker>" "Delete the specified matchmaker."
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

environment_reset() {
  rm -rf ${OMGSERVERSCTL_DIRECTORY}/environment
}

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
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Variable was not found, VARIABLE_NAME=${VARIABLE_NAME}" >&2
    exit 1
  else
    echo -n ${!VARIABLE_NAME}
  fi
}

environment_useEnvironment() {
  ENVIRONMENT_NAME=$1
  SERVICE_URL=$2

  if [ -z "${ENVIRONMENT_NAME}" -o -z "${SERVICE_URL}" ]; then
    help "environment useEnvironment"
    exit 1
  fi

  echo "export OMGSERVERSCTL_ENVIRONMENT_NAME=${ENVIRONMENT_NAME}" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_SERVICE_URL=${SERVICE_URL}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $ENVIRONMENT_NAME) Environment was set, NAME=\"${ENVIRONMENT_NAME}\", SERVICE_URL=\"${SERVICE_URL}\""
}

environment_useDemoServer() {
  environment_useEnvironment demo https://demoserver.omgservers.com
}

environment_useLocalServer() {
  environment_useEnvironment local http://localhost:8080
  admin_createToken admin admin
  support_createToken support support
}

# ADMIN

admin_printCurrent() {
  internal_useEnvironment

  ADMIN_USER=${OMGSERVERSCTL_ADMIN_USER}
  ADMIN_TOKEN=${OMGSERVERSCTL_ADMIN_TOKEN}

  if [ -z "${ADMIN_USER}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current admin was not found" >&2
    exit 1
  fi

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current admin was found, ADMIN_USER=${ADMIN_USER}, (without token)"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current admin was found, ADMIN_USER=${ADMIN_USER}, (token exists)"
  fi
}

admin_createToken() {
  internal_ensureEnvironment

  ADMIN_USER=$1
  ADMIN_PASSWORD=$2

  if [ -z "${ADMIN_USER}" -o -z "${ADMIN_PASSWORD}" ]; then
    help "admin createToken"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using admin, ADMIN_USER=\"${ADMIN_USER}\""

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/admin/request/create-token"
  REQUEST="{\"user\": \"${ADMIN_USER}\", \"password\": \"${ADMIN_PASSWORD}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/admin-create-token_${ADMIN_USER}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-create-token_${ADMIN_USER}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  ADMIN_TOKEN=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-create-token_${ADMIN_USER}.json | jq -r .raw_token)
  if [ -z "$ADMIN_TOKEN" -o "$ADMIN_TOKEN" == "null" ]; then
    echo "ERROR: ADMIN_TOKEN was not received" >&2
    exit 1
  fi

  echo "export OMGSERVERSCTL_ADMIN_USER=$ADMIN_USER" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_ADMIN_TOKEN=$ADMIN_TOKEN" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Admin token was created"
}

admin_calculateShard() {
  internal_ensureEnvironment

  SHARD_KEY=$1

  if [ -z "${SHARD_KEY}" ]; then
    help "admin calculateShard"
    exit 1
  fi

  ADMIN_TOKEN=$OMGSERVERSCTL_ADMIN_TOKEN

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current admin token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/admin/request/calculate-shard"
  REQUEST="{\"shard_key\": \"${SHARD_KEY}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${ADMIN_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/admin-calculate_shard_${SHARD_KEY}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-calculate_shard_${SHARD_KEY}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  SHARD_INDEX=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-calculate_shard_${SHARD_KEY}.json | jq -r .shard_index)
  if [ -z "${SHARD_INDEX}" -o "${SHARD_INDEX}" == "null" ]; then
    echo "ERROR: SHARD_INDEX was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_SHARD_INDEX=${SHARD_INDEX}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  SERVER_URI=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-calculate_shard_${SHARD_KEY}.json | jq -r .server_uri)
  if [ -z "${SERVER_URI}" -o "${SERVER_URI}" == "null" ]; then
    echo "ERROR: SERVER_URI was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_SERVER_URI=${SERVER_URI}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Shard was calculated, SHARD_INDEX=${SHARD_INDEX}, SERVER_URI=${SERVER_URI}"
}

admin_generateId() {
  internal_ensureEnvironment

  ADMIN_TOKEN=$OMGSERVERSCTL_ADMIN_TOKEN

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current admin token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/admin/request/generate-id"
  REQUEST="{}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/admin-generate-id.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${ADMIN_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  GENERATED_ID=$(cat ${RESPONSE_FILE} | jq -r .id)
  if [ -z "${GENERATED_ID}" -o "${GENERATED_ID}" == "null" ]; then
    echo "ERROR: GENERATED_ID was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_GENERATED_ID=${GENERATED_ID}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Id was generated, GENERATED_ID=${GENERATED_ID}"
}

admin_bcryptHash() {
  internal_ensureEnvironment

  VALUE=$1

  if [ -z "${VALUE}" ]; then
    help "admin bcryptHash"
    exit 1
  fi

  ADMIN_TOKEN=$OMGSERVERSCTL_ADMIN_TOKEN

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current admin token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/admin/request/bcrypt-hash"
  REQUEST="{ \"value\": \"${VALUE}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/admin-bcrypt-hash.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${ADMIN_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  BCRYPTED_HASH=$(cat ${RESPONSE_FILE} | jq -r .hash)
  if [ -z "${BCRYPTED_HASH}" -o "${BCRYPTED_HASH}" == "null" ]; then
    echo "ERROR: BCRYPTED_HASH was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_BCRYPTED_HASH=${BCRYPTED_HASH}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Value was bcrypted, BCRYPTED_HASH=${BCRYPTED_HASH}"
}

admin_pingDockerHost() {
  internal_ensureEnvironment

  DOCKER_DAEMON_URI=$1

  if [ -z "${DOCKER_DAEMON_URI}" ]; then
    help "admin pingDockerHost"
    exit 1
  fi

  ADMIN_TOKEN=$OMGSERVERSCTL_ADMIN_TOKEN

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current admin token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/admin/request/ping-docker-host"
  REQUEST="{\"docker_daemon_uri\": \"${DOCKER_DAEMON_URI}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${ADMIN_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/admin-ping-docker-host.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-ping-docker-host.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  SUCCESSFUL=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-ping-docker-host.json | jq -r .successful)
  if [ -z "${SUCCESSFUL}" -o "${SUCCESSFUL}" == "null" ]; then
    echo "ERROR: SUCCESSFUL was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_SUCCESSFUL=${SUCCESSFUL}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  FROM_SERVER=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/admin-ping-docker-host.json | jq -r .from_server)
  if [ -z "${FROM_SERVER}" -o "${FROM_SERVER}" == "null" ]; then
    echo "ERROR: FROM_SERVER was not received" >&2
    exit 1
  fi

  if [ "${SUCCESSFUL}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Docker host was pinged, FROM_SERVER=${FROM_SERVER}, DOCKER_DAEMON_URI=${DOCKER_DAEMON_URI}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Docker host was not pinged, FROM_SERVER=${FROM_SERVER}, DOCKER_DAEMON_URI=${DOCKER_DAEMON_URI}"
  fi
}

# SUPPORT

support_printCurrent() {
  internal_useEnvironment

  SUPPORT_USER=${OMGSERVERSCTL_SUPPORT_USER}
  SUPPORT_TOKEN=${OMGSERVERSCTL_SUPPORT_TOKEN}

  if [ -z "${SUPPORT_USER}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support was not found" >&2
    exit 1
  fi

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current support was found, SUPPORT_USER=$SUPPORT_USER, (without token)"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current support was found, SUPPORT_USER=$SUPPORT_USER, (token exists)"
  fi
}

support_createToken() {
  internal_ensureEnvironment

  SUPPORT_USER=$1
  SUPPORT_PASSWORD=$2

  if [ -z "${SUPPORT_USER}" -o -z "${SUPPORT_PASSWORD}" ]; then
    help "support createToken"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using support, SUPPORT_USER=\"$SUPPORT_USER\""

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-token"
  REQUEST="{\"user\": \"${SUPPORT_USER}\", \"password\": \"${SUPPORT_PASSWORD}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-token_${SUPPORT_USER}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-token_${SUPPORT_USER}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  RAW_TOKEN=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-token_${SUPPORT_USER}.json | jq -r .raw_token)
  if [ -z "$RAW_TOKEN" -o "$RAW_TOKEN" == "null" ]; then
    echo "ERROR: RAW_TOKEN was not received" >&2
    exit 1
  fi

  echo "export OMGSERVERSCTL_SUPPORT_USER=$SUPPORT_USER" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_SUPPORT_TOKEN=$RAW_TOKEN" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Support token was created"
}

support_createTenant() {
  internal_ensureEnvironment

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-tenant"
  REQUEST="{}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  TENANT=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant.json | jq -r .id)
  if [ -z "$TENANT" -o "$TENANT" == "null" ]; then
    echo "ERROR: TENANT was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_TENANT=$TENANT" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant was created, TENANT=\"$TENANT\""
}

support_createTenantAlias() {
  internal_ensureEnvironment

  TENANT_ID=$1
  ALIAS=$2

  if [ -z "${TENANT_ID}" -o -z "${ALIAS}" ]; then
    help "support createTenantAlias"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant id, TENANT_ID=\"${TENANT_ID}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using alias, ALIAS=\"${ALIAS}\""

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-tenant-alias"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"alias\": \"${ALIAS}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant-alias_${TENANT_ID}_${ALIAS}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant alias was created"
}

support_deleteTenant() {
  internal_ensureEnvironment

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "support deleteTenant"
    exit 1
  fi

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/delete-tenant"
  REQUEST="{\"tenant\": \"${TENANT}\"}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant_${TENANT}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant_${TENANT}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant_${TENANT}.json | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant was deleted, TENANT=${TENANT}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Tenant was not deleted, TENANT=${TENANT}"
  fi
}

# Project

support_createProject() {
  internal_ensureEnvironment

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "support createProject"
    exit 1
  fi

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-project"
  REQUEST="{\"tenant\": \"${TENANT}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project_${TENANT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  PROJECT=$(cat ${RESPONSE_FILE} | jq -r .project_id)
  if [ -z "$PROJECT" -o "$PROJECT" == "null" ]; then
    echo "ERROR: PROJECT was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_PROJECT=$PROJECT" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  STAGE=$(cat ${RESPONSE_FILE} | jq -r .stage_id)
  if [ -z "${STAGE}" -o "${STAGE}" == "null" ]; then
    echo "ERROR: STAGE was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE=${STAGE}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was created, PROJECT=\"${PROJECT}\", STAGE=\"${STAGE}\""
}

support_createProjectAlias() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT_ID=$2
  ALIAS=$3

  if [ -z "${TENANT}" -o -z "${PROJECT_ID}" -o -z "${ALIAS}" ]; then
    help "support createProjectAlias"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project id, PROJECT_ID=\"${PROJECT_ID}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using alias, ALIAS=\"${ALIAS}\""

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-project-alias"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project_id\": \"${PROJECT_ID}\", \"alias\": \"${ALIAS}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project-alias_${TENANT}_${PROJECT_ID}_${ALIAS}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project alias was created"
}

support_deleteProject() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "support deleteProject"
    exit 1
  fi

  SUPPORT_TOKEN=${OMGSERVERSCTL_SUPPORT_TOKEN}

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/delete-project"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project_${TENANT}_${PROJECT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was not deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  fi
}

# Stage

support_createStage() {
  internal_ensureEnvironment

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "support createStage"
    exit 1
  fi

  PROJECT=$2

  if [ -z "${PROJECT}" ]; then
    help "support createStage"
    exit 1
  fi

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-stage"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-stage_${TENANT}_${PROJECT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  STAGE=$(cat ${RESPONSE_FILE} | jq -r .stage_id)
  if [ -z "${STAGE}" -o "${STAGE}" == "null" ]; then
    echo "ERROR: STAGE was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE=${STAGE}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Stage was created, STAGE=\"${STAGE}\""
}

support_createStageAlias() {
  internal_ensureEnvironment

  TENANT=$1
  STAGE_ID=$2
  ALIAS=$3

  if [ -z "${TENANT}" -o -z "${STAGE_ID}" -o -z "${ALIAS}" ]; then
    help "support createStageAlias"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage id, STAGE_ID=\"${STAGE_ID}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using alias, ALIAS=\"${ALIAS}\""

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-stage-alias"
  REQUEST="{\"tenant\": \"${TENANT}\", \"stage_id\": \"${STAGE_ID}\", \"alias\": \"${ALIAS}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-stage-alias_${TENANT}_${STAGE_ID}_${ALIAS}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Stage alias was created"
}

support_deleteStage() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2
  STAGE=$3

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" ]; then
    help "support deleteStage"
    exit 1
  fi

  SUPPORT_TOKEN=${OMGSERVERSCTL_SUPPORT_TOKEN}

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/delete-stage"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project_${TENANT}_${PROJECT}_${STAGE}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Stage was deleted"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Stage was not deleted"
  fi
}

# Developer

support_createDeveloper() {
  internal_ensureEnvironment

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-developer"
  REQUEST="{}"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT}.json)

  cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT}.json >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  USER=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT}.json | jq -r .user_id)
  if [ -z "$USER" -o "$USER" == "null" ]; then
    echo "ERROR: USER was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_USER=$USER" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  PASSWORD=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/support-create-developer_${TENANT}.json | jq -r .password)
  if [ -z "$PASSWORD" -o "$PASSWORD" == "null" ]; then
    echo "ERROR: PASSWORD was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_PASSWORD='$PASSWORD'" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Developer was created, DEVELOPER_USER=\"${USER}\", DEVELOPER_PASSWORD=\"${PASSWORD}\""

  developer_createToken ${USER} ${PASSWORD}
}

support_createTenantPermission() {
  internal_ensureEnvironment

  TENANT=$1
  USER=$2
  PERMISSION=$3

  if [ -z "${TENANT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support createTenantPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=\"${USER}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=\"${PERMISSION}\""

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-tenant-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"user_id\": \"${USER}\", \"permissions_to_create\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-tenant-permissions_${TENANT}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .created_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Created permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

support_deleteTenantPermission() {
  internal_ensureEnvironment

  TENANT=$1
  USER=$2
  PERMISSION=$3

  if [ -z "${TENANT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support deleteTenantPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=\"${USER}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=\"${PERMISSION}\""

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/delete-tenant-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"user_id\": \"${USER}\", \"permissions_to_delete\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-tenant-permissions_${TENANT}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .deleted_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deleted permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

support_createProjectPermission() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2
  USER=$3
  PERMISSION=$4

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support createProjectPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=\"${PROJECT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=\"${USER}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=\"${PERMISSION}\""

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-project-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"user_id\": \"${USER}\", \"permissions_to_create\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-project-permissions_${TENANT}_${PROJECT}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .created_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Created tenant project permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

support_deleteProjectPermission() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2
  USER=$3
  PERMISSION=$4

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support deleteProjectPermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=\"${PROJECT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=\"${USER}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=\"${PERMISSION}\""

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/delete-project-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"user_id\": \"${USER}\", \"permissions_to_delete\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-project-permissions_${TENANT}_${PROJECT}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .deleted_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deleted tenant project permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

support_createStagePermission() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2
  STAGE=$3
  USER=$4
  PERMISSION=$5

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support createStagePermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=\"${PROJECT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE=\"${STAGE}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=\"${USER}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=\"${PERMISSION}\""

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/create-stage-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\", \"user_id\": \"${USER}\", \"permissions_to_create\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-create-stage-permissions_${TENANT}_${PROJECT}_${STAGE}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .created_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Created tenant stage permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

support_deleteStagePermission() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2
  STAGE=$3
  USER=$4
  PERMISSION=$5

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support deleteStagePermission"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=\"${PROJECT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE=\"${STAGE}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using user, USER=\"${USER}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using permission, PERMISSION=\"${PERMISSION}\""

  SUPPORT_TOKEN=$OMGSERVERSCTL_SUPPORT_TOKEN

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current support token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/support/request/delete-stage-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\", \"user_id\": ${USER}, \"permissions_to_delete\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/support-delete-stage-permissions_${TENANT}_${PROJECT}_${STAGE}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .deleted_permissions)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deleted tenant stage permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

# DEVELOPER

developer_printCurrent() {
  internal_useEnvironment

  DEVELOPER_USER=${OMGSERVERSCTL_DEVELOPER_USER}
  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_USER}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer was not found" >&2
    exit 1
  fi

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current developer was found, DEVELOPER_USER=${DEVELOPER_USER}, (without token)"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Current developer was found, DEVELOPER_USER=${DEVELOPER_USER}, (token exists)"
  fi
}

developer_createToken() {
  internal_ensureEnvironment

  DEVELOPER_USER=$1
  DEVELOPER_PASSWORD=$2

  if [ -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    help "developer createToken"
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/create-token"
  REQUEST="{\"user_id\": \"${DEVELOPER_USER}\", \"password\": \"${DEVELOPER_PASSWORD}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-token_${DEVELOPER_USER}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  RAW_TOKEN=$(cat ${RESPONSE_FILE} | jq -r .raw_token)
  if [ -z "$RAW_TOKEN" -o "$RAW_TOKEN" == "null" ]; then
    echo "ERROR: RAW_TOKEN was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEVELOPER_USER=$DEVELOPER_USER" >> ${OMGSERVERSCTL_DIRECTORY}/environment
  echo "export OMGSERVERSCTL_DEVELOPER_TOKEN=$RAW_TOKEN" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Developer token was created"
}

developer_getTenantDetails() {
  internal_ensureEnvironment

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "developer getTenantDetails"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"$TENANT\""

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/get-tenant-details"
  REQUEST="{\"tenant\": \"${TENANT}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-details_${TENANT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-details_${TENANT}.json || cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-tenant-details_${TENANT}.json | jq
}

developer_createProject() {
  internal_ensureEnvironment

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "developer createProject"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"$TENANT\""

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/create-project"
  REQUEST="{\"tenant\": \"${TENANT}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  PROJECT=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT}.json | jq -r .project_id)
  if [ -z "$PROJECT" -o "$PROJECT" == "null" ]; then
    echo "ERROR: PROJECT was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_PROJECT=$PROJECT" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  STAGE=$(cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project_${TENANT}.json | jq -r .stage_id)
  if [ -z "$STAGE" -o "$STAGE" == "null" ]; then
    echo "ERROR: STAGE was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE=$STAGE" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was created, PROJECT=\"${PROJECT}\", STAGE=\"${STAGE}\""
}

developer_createProjectAlias() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT_ID=$2
  ALIAS=$3

  if [ -z "${TENANT}" -o -z "${PROJECT_ID}" -o -z "${ALIAS}" ]; then
    help "developer createProjectAlias"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project id, PROJECT_ID=\"${PROJECT_ID}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using alias, ALIAS=\"${ALIAS}\""

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/create-project-alias"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project_id\": ${PROJECT_ID}, \"alias\": \"${ALIAS}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-project-alias_${TENANT}_${PROJECT_ID}_${ALIAS}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project alias was created"
}

developer_getProjectDetails() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "developer getProjectDetails"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=\"${PROJECT}\""

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/get-project-details"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-project-details_${TENANT}_${PROJECT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-project-details_${TENANT}_${PROJECT}.json || cat ${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-project-details_${TENANT}_${PROJECT}.json | jq
}

developer_deleteProject() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "developer deleteProject"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=\"${PROJECT}\""

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/delete-project"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-project_${TENANT}_${PROJECT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Project was not deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  fi
}

developer_createStage() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "developer createStage"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"$TENANT\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=\"${PROJECT}\""

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/create-stage"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-stage_${TENANT}_${PROJECT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  STAGE=$(cat ${RESPONSE_FILE} | jq -r .stage_id)
  if [ -z "$STAGE" -o "$STAGE" == "null" ]; then
    echo "ERROR: STAGE was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_STAGE=$STAGE" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Stage was created, STAGE=\"${STAGE}\""
}

developer_createStageAlias() {
  internal_ensureEnvironment

  TENANT=$1
  STAGE_ID=$2
  ALIAS=$3

  if [ -z "${TENANT}" -o -z "${STAGE_ID}" -o -z "${ALIAS}" ]; then
    help "developer createStageAlias"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"$TENANT\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage id, STAGE_ID=\"${STAGE_ID}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using alias, ALIAS=\"${ALIAS}\""

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/create-stage-alias"
  REQUEST="{\"tenant\": \"${TENANT}\", \"stage_id\": \"${STAGE_ID}\", \"alias\": \"${ALIAS}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-stage_${TENANT}_${STAGE_ID}_${ALIAS}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Stage alias was created"
}

developer_getStageDetails() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2
  STAGE=$3

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" ]; then
    help "developer getStageDetails"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE=\"${STAGE}\""

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/get-stage-details"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-stage-details_${TENANT}_${PROJECT}_${STAGE}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${RESPONSE_FILE} || cat ${RESPONSE_FILE} | jq
}

developer_deleteStage() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2
  STAGE=$3

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" ]; then
    help "developer deleteStage"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE=\"${STAGE}\""

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/delete-stage"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-stage_${TENANT}_${PROJECT}_${STAGE}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Stage was deleted, TENANT=${TENANT}, STAGE=${STAGE}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Stage was not deleted, TENANT=${TENANT}, STAGE=${STAGE}"
  fi
}

developer_createVersion() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2
  CONFIG_PATH=$3

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${CONFIG_PATH}" ]; then
    help "developer createVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=\"${PROJECT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using config path, CONFIG_PATH=\"${CONFIG_PATH}\""

  if [ ! -f ${CONFIG_PATH} ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Config file was not found, CONFIG_PATH=\"${CONFIG_PATH}\"" >&2
    exit 1
  fi

  CONFIG=$(cat ${CONFIG_PATH} | jq -c -r)

  if [ -z "${CONFIG}" -o "${CONFIG}" == "null" ]; then
    echo "ERROR: Version config is empty" >&2
    exit 1
  fi

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/create-version"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"config\": ${CONFIG}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-create-version_${TENANT}_${PROJECT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  VERSION=$(cat ${RESPONSE_FILE} | jq -r .version_id)
  if [ -z "$VERSION" -o "$VERSION" == "null" ]; then
    echo "ERROR: VERSION was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_VERSION=${VERSION}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Version was created, VERSION=\"${VERSION}\""
}

developer_uploadFilesArchive() {
  internal_ensureEnvironment

  TENANT=$1
  VERSION=$2
  FILES_DIRECTORY_PATH=$3

  if [ -z "${TENANT}" -o -z "${VERSION}" -o -z "${FILES_DIRECTORY_PATH}" ]; then
    help "developer uploadFilesArchive"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using version, VERSION=\"${VERSION}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using files directory path, FILES_DIRECTORY_PATH=\"${FILES_DIRECTORY_PATH}\""

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ARCHIVE_PATH=$(eval echo ${OMGSERVERSCTL_DIRECTORY}/versions/version_${TENANT}_${VERSION}.zip)
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using archive path, ARCHIVE_PATH=\"${ARCHIVE_PATH}\""

  pushd ${FILES_DIRECTORY_PATH}

  find . -type f -name "*.lua" | zip -@ ${ARCHIVE_PATH}

  popd >> /dev/null

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/upload-files-archive"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-upload-files-archive_${TENANT}_${VERSION}.json"

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: multipart/form-data" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -F "tenant=${TENANT}" \
    -F "tenantVersionId=${VERSION}" \
    -F "version.zip=@${ARCHIVE_PATH}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  FILES_ARCHIVE=$(cat ${RESPONSE_FILE} | jq -r .files_archive_id)
  if [ -z "${FILES_ARCHIVE}" -o "${FILES_ARCHIVE}" == "null" ]; then
    echo "ERROR: FILES_ARCHIVE was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_FILES_ARCHIVE=${FILES_ARCHIVE}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Files archive was uploaded, FILES_ARCHIVE=${FILES_ARCHIVE}"
}

developer_getVersionDetails() {
  internal_ensureEnvironment

  TENANT=$1
  VERSION=$2

  if [ -z "${TENANT}" -o -z "${VERSION}" ]; then
    help "developer getVersionDetails"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using version, VERSION=\"${VERSION}\""

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/get-version-details"
  REQUEST="{\"tenant\": \"${TENANT}\", \"version_id\": ${VERSION}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-version-details_${TENANT}_${VERSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${RESPONSE_FILE} || cat ${RESPONSE_FILE} | jq
}

developer_deleteVersion() {
  internal_ensureEnvironment

  TENANT=$1
  VERSION=$2

  if [ -z "${TENANT}" -o -z "${VERSION}" ]; then
    help "developer deleteVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using version, VERSION=\"${VERSION}\""

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/delete-version"
  REQUEST="{\"tenant\": \"${TENANT}\", \"version_id\": \"${VERSION}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-tenant-version_${TENANT}_${VERSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Version was deleted, TENANT=${TENANT}, VERSION=${VERSION}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Version was not deleted, TENANT=${TENANT}, VERSION=${VERSION}"
  fi
}

developer_deployVersion() {
  internal_ensureEnvironment

  TENANT=$1
  PROJECT=$2
  STAGE=$3
  VERSION=$4

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" -o -z "${VERSION}" ]; then
    help "developer deployVersion"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using project, PROJECT=\"${PROJECT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using stage, STAGE=\"${STAGE}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using version, VERSION=\"${VERSION}\""

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/deploy-version"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\", \"version_id\": ${VERSION}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-deploy-version_${TENANT}_${PROJECT}_${STAGE}_${VERSION}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DEPLOYMENT=$(cat ${RESPONSE_FILE} | jq -r .deployment_id)
  if [ -z "${DEPLOYMENT}" -o "${DEPLOYMENT}" == "null" ]; then
    echo "ERROR: DEPLOYMENT was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DEPLOYMENT=${DEPLOYMENT}" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deployment was created, DEPLOYMENT=\"${DEPLOYMENT}\""
}

developer_getDeploymentDetails() {
  internal_ensureEnvironment

  TENANT=$1
  DEPLOYMENT=$2

  if [ -z "${TENANT}" -o -z "${DEPLOYMENT}" ]; then
    help "developer getDeploymentDetails"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using deployment, DEPLOYMENT=${DEPLOYMENT}"

  DEVELOPER_TOKEN=$OMGSERVERSCTL_DEVELOPER_TOKEN

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/get-deployment-details"
  REQUEST="{\"tenant\": \"${TENANT}\", \"deployment_id\": ${DEPLOYMENT}}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-get-deployment-details_${TENANT}_${DEPLOYMENT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  type open 2> /dev/null && open ${RESPONSE_FILE} || cat ${RESPONSE_FILE} | jq
}

developer_deleteDeployment() {
  internal_ensureEnvironment

  TENANT=$1
  DEPLOYMENT=$2

  if [ -z "${TENANT}" -o -z "${DEPLOYMENT}" ]; then
    help "developer deleteDeployment"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using tenant, TENANT=\"${TENANT}\""
  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using deployment, DEPLOYMENT=${DEPLOYMENT}"

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/delete-deployment"
  REQUEST="{\"tenant\": \"${TENANT}\", \"id\": \"${DEPLOYMENT}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-deployment_${TENANT}_${DEPLOYMENT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deployment was deleted, TENANT=${TENANT}, DEPLOYMENT=${DEPLOYMENT}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Deployment was not deleted, TENANT=${TENANT}, DEPLOYMENT=${DEPLOYMENT}"
  fi
}

developer_createLobbyRequest() {
  internal_ensureEnvironment

  TENANT=$1
  DEPLOYMENT=$2

  if [ -z "${TENANT}" -o -z "${DEPLOYMENT}" ]; then
    help "developer createLobbyRequest"
    exit 1
  fi

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}
  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/create-lobby-request"
  REQUEST="{\"tenant\": \"${TENANT}\", \"deployment_id\": \"${DEPLOYMENT}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/create-lobby-request_${TENANT}_${DEPLOYMENT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  echo "Lobby request created successfully."
}

developer_deleteLobby() {
  internal_ensureEnvironment

  LOBBY_ID=$1

  if [ -z "${LOBBY_ID}" ]; then
    help "developer deleteLobby"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using lobby, LOBBY_ID=\"${LOBBY_ID}\""

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/delete-lobby"
  REQUEST="{\"lobby_id\": \"${LOBBY_ID}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-lobby_${LOBBY_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Lobby was deleted, LOBBY_ID=${LOBBY_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Lobby was not deleted, LOBBY_ID=${LOBBY_ID}"
  fi
}

developer_createMatchmakerRequest() {
  internal_ensureEnvironment

  TENANT=$1
  DEPLOYMENT=$2

  if [ -z "${TENANT}" -o -z "${DEPLOYMENT}" ]; then
    help "developer createMatchmakerRequest"
    exit 1
  fi

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}
  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/create-matchmaker-request"
  REQUEST="{\"tenant\": \"${TENANT}\", \"deployment_id\": \"${DEPLOYMENT}\"}"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/create-matchmaker-request_${TENANT}_${DEPLOYMENT}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  echo "Matchmaker request created successfully."
}

developer_deleteMatchmaker() {
  internal_ensureEnvironment

  MATCHMAKER_ID=$1

  if [ -z "${MATCHMAKER_ID}" ]; then
    help "developer deleteMatchmaker"
    exit 1
  fi

  echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Using matchmaker, MATCHMAKER_ID=\"${MATCHMAKER_ID}\""

  DEVELOPER_TOKEN=${OMGSERVERSCTL_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) $(echo ${OMGSERVERSCTL_ENVIRONMENT_NAME}) ERROR: Current developer token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMGSERVERSCTL_SERVICE_URL}/service/v1/entrypoint/developer/request/delete-matchmaker"
  REQUEST="{\"matchmaker_id\": \"${MATCHMAKER_ID}\" }"
  RESPONSE_FILE="${OMGSERVERSCTL_DIRECTORY}/temp/developer-delete-matchmaker_${MATCHMAKER_ID}.json"

  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo $REQUEST >> ${OMGSERVERSCTL_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMGSERVERSCTL_DIRECTORY}/logs
  echo >> ${OMGSERVERSCTL_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMGSERVERSCTL_DIRECTORY}/logs
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMGSERVERSCTL_DELETED=$DELETED" >> ${OMGSERVERSCTL_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Matchmaker was deleted, MATCHMAKER_ID=${MATCHMAKER_ID}"
  else
    echo "$(date) $(echo $OMGSERVERSCTL_ENVIRONMENT_NAME) Matchmaker was not deleted, MATCHMAKER_ID=${MATCHMAKER_ID}"
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
  if [ "$2" = "reset" ]; then
    environment_reset
  elif [ "$2" = "printCurrent" ]; then
    environment_printCurrent
  elif [ "$2" = "printVariable" ]; then
    environment_printVariable $3
  elif [ "$2" = "useEnvironment" ]; then
    environment_useEnvironment $3 $4 $5
  elif [ "$2" = "useDemoServer" ]; then
    environment_useDemoServer
  elif [ "$2" = "useLocalServer" ]; then
    environment_useLocalServer
  else
    help "environment"
  fi
  exit 0
fi

# Admin
if [ "$1" = "admin" ]; then
  shift
  if [ "$1" = "printCurrent" ]; then
    admin_printCurrent
  elif [ "$1" = "createToken" ]; then
    shift
    admin_createToken $@
  elif [ "$1" = "calculateShard" ]; then
    shift
    admin_calculateShard $@
  elif [ "$1" = "generateId" ]; then
    admin_generateId
  elif [ "$1" = "bcryptHash" ]; then
    shift
    admin_bcryptHash $@
  elif [ "$1" = "pingDockerHost" ]; then
    shift
    admin_pingDockerHost $@
  else
    help "admin"
  fi
# Support
elif [ "$1" = "support" ]; then
  if [ "$2" = "printCurrent" ]; then
    support_printCurrent
  elif [ "$2" = "createToken" ]; then
    support_createToken
  elif [ "$2" = "createTenant" ]; then
    support_createTenant
  elif [ "$2" = "createTenantAlias" ]; then
    support_createTenantAlias $3 $4
  elif [ "$2" = "deleteTenant" ]; then
    support_deleteTenant $3
  # Project
  elif [ "$2" = "createProject" ]; then
    support_createProject $3
  elif [ "$2" = "createProjectAlias" ]; then
    support_createProjectAlias $3 $4 $5
  elif [ "$2" = "deleteProject" ]; then
    support_deleteProject $3 $4
  # Stage
  elif [ "$2" = "createStage" ]; then
    support_createStage $3 $4
  elif [ "$2" = "createStageAlias" ]; then
    support_createStageAlias $3 $4 $5
  elif [ "$2" = "deleteStage" ]; then
    support_deleteStage $3 $4 $5
  # Developer
  elif [ "$2" = "createDeveloper" ]; then
    support_createDeveloper $3
  # Permissions
  elif [ "$2" = "createTenantPermission" ]; then
    support_createTenantPermission $3 $4 $5
  elif [ "$2" = "deleteTenantPermission" ]; then
    support_deleteTenantPermission $3 $4 $5
  elif [ "$2" = "createProjectPermission" ]; then
    support_createProjectPermission $3 $4 $5 $6
  elif [ "$2" = "deleteProjectPermission" ]; then
    support_deleteProjectPermission $3 $4 $5 $6
  elif [ "$2" = "createStagePermission" ]; then
    support_createStagePermission $3 $4 $5 $6 $7
  elif [ "$2" = "deleteStagePermission" ]; then
    support_deleteStagePermission $3 $4 $5 $6 $7
  else
    help "support"
  fi
# Developer
elif [ "$1" = "developer" ]; then
  if [ "$2" = "printCurrent" ]; then
      developer_printCurrent
  elif [ "$2" = "createToken" ]; then
    developer_createToken
  elif [ "$2" = "getTenantDetails" ]; then
    developer_getTenantDetails $3
  elif [ "$2" = "createProject" ]; then
    developer_createProject $3
  elif [ "$2" = "createProjectAlias" ]; then
    developer_createProjectAlias $3 $4 $5
  elif [ "$2" = "getProjectDetails" ]; then
    developer_getProjectDetails $3 $4
  elif [ "$2" = "deleteProject" ]; then
    developer_deleteProject $3 $4
  elif [ "$2" = "createStage" ]; then
    developer_createStage $3 $4
  elif [ "$2" = "createStageAlias" ]; then
    developer_createStageAlias $3 $4 $5
  elif [ "$2" = "getStageDetails" ]; then
    developer_getStageDetails $3 $4 $5
  elif [ "$2" = "deleteStage" ]; then
    developer_deleteStage $3 $4 $5
  elif [ "$2" = "createVersion" ]; then
    developer_createVersion $3 $4 $5
  elif [ "$2" = "uploadFilesArchive" ]; then
    developer_uploadFilesArchive $3 $4 $5
  elif [ "$2" = "getVersionDetails" ]; then
    developer_getVersionDetails $3 $4
  elif [ "$2" = "deleteVersion" ]; then
    developer_deleteVersion $3 $4
  elif [ "$2" = "deployVersion" ]; then
    developer_deployVersion $3 $4 $5 $6
  elif [ "$2" = "getDeploymentDetails" ]; then
    developer_getDeploymentDetails $3 $4
  elif [ "$2" = "deleteDeployment" ]; then
    developer_deleteDeployment $3 $4
  elif [ "$2" = "createLobbyRequest" ]; then
      developer_createLobbyRequest $3 $4
  elif [ "$2" = "deleteLobby" ]; then
    developer_deleteLobby $3
  elif [ "$2" = "createMatchmakerRequest" ]; then
    developer_createMatchmakerRequest $3 $4
  elif [ "$2" = "deleteMatchmaker" ]; then
    developer_deleteMatchmaker $3
  else
    help "developer"
  fi
else
  help
fi
