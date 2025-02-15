#!/bin/bash
set -e

# Environment Variables:
# OMG_LOCALTESTING_CONTAINER is "omgservers" by default
# OMG_CONTEXT_DIRECTORY is ".omgserversctl" by default
# OMG_DOCKER_IMAGE is "omgservers/localtesting:latest" by default
# OMG_LOCALTESTING_TENANT is "omgservers" by default
# OMG_LOCALTESTING_PROJECT is "localtesting" by default
# OMG_LOCALTESTING_STAGE is "default" by default

OMG_LOCALTESTING_CONTAINER=${OMG_LOCALTESTING_CONTAINER:-"omgservers"}
OMG_CONTEXT_DIRECTORY=${OMG_CONTEXT_DIRECTORY:-".omgserversctl"}
OMG_DOCKER_IMAGE=${OMG_DOCKER_IMAGE:-"omgservers/localtesting:latest"}
OMG_LOCALTESTING_TENANT=${OMG_LOCALTESTING_TENANT:-"omgservers"}
OMG_LOCALTESTING_PROJECT=${OMG_LOCALTESTING_PROJECT:-"localtesting"}
OMG_LOCALTESTING_STAGE=${OMG_LOCALTESTING_STAGE:-"default"}

if [ ! -d "${OMG_CONTEXT_DIRECTORY}" ]; then
  mkdir -p ${OMG_CONTEXT_DIRECTORY}
fi
if [ ! -d "${OMG_CONTEXT_DIRECTORY}/temp" ]; then
  mkdir -p ${OMG_CONTEXT_DIRECTORY}/temp
fi
if [ ! -d "${OMG_CONTEXT_DIRECTORY}/versions" ]; then
  mkdir -p ${OMG_CONTEXT_DIRECTORY}/versions
fi
if [ ! -f "${OMG_CONTEXT_DIRECTORY}/environment" ]; then
  touch ${OMG_CONTEXT_DIRECTORY}/environment
fi
if [ ! -f "${OMG_CONTEXT_DIRECTORY}/logs" ]; then
  touch ${OMG_CONTEXT_DIRECTORY}/logs
fi

# INTERNAL

internal_ensureEnvironment() {
  source ${OMG_CONTEXT_DIRECTORY}/environment
}

internal_ensureInstallationUrl() {
  if [ -z "${OMG_INSTALLATION_URL}" ]; then
    echo "$(date) [CTL/internalEnsureInstallationUrl] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Installation url was not set" >&2
    exit 1
  fi
}

internal_validateJwt() {
  [ "$(echo $1 | jq -R 'split(".") | .[1] | @base64d | fromjson | .exp')" -lt "$(date +%s)" ] && echo "expired" || echo "valid"
}

internal_ensureSupportToken() {
  if [ -z "${OMG_SUPPORT_TOKEN}" ]; then
    echo "$(date) [CTL/internalEnsureSupportToken] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Support token was not found" >&2
    exit 1
  fi

  if [ "$(internal_validateJwt ${OMG_SUPPORT_TOKEN})" = "expired" ]; then
    echo "$(date) [CTL/internalEnsureSupportToken] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Support token is expired" >&2
    exit 1
  fi
}

internal_ensureDeveloperToken() {
  if [ -z "${OMG_DEVELOPER_TOKEN}" ]; then
    echo "$(date) [CTL/internalEnsureDeveloperToken] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Developer token was not found" >&2
    exit 1
  fi

  if [ "$(internal_validateJwt ${OMG_DEVELOPER_TOKEN})" = "expired" ]; then
    echo "$(date) [CTL/internalEnsureDeveloperToken] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Developer token is expired" >&2
    exit 1
  fi
}

internal_print_command() {
  printf "  %-60s %s\n" "$1" "$2"
}

internal_format_file() {
  if [ -n "${FORMATTING}" ]; then
    cat $1 | jq .
  else
    cat $1
  fi
}

# HANDLERS

help() {
  internal_ensureEnvironment

  echo "OMGSERVERS ctl, v${OMGSERVERS_VERSION} (${OMG_INSTALLATION_NAME:-unknown})"
  echo "Usage: $0"
  if [ -z "$1" -o "$1" = "help" ]; then
    internal_print_command " help" "Display this help message."
  fi
  if [ -z "$1" -o "$1" = "logs" ]; then
    internal_print_command " logs" "Show ctl logs"
  fi
  # Environment
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment reset" ]; then
    internal_print_command " environment reset" "Reset the current environment."
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment printCurrent" ]; then
    internal_print_command " environment printCurrent" "Print all environment variables."
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment printVariable" ]; then
    internal_print_command " environment printVariable <variable>" "Print the value of a specific variable."
  fi
  # Localtesting
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting runServer" ]; then
    internal_print_command " localtesting runServer" "Start the server in a Docker container."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting stopServer" ]; then
    internal_print_command " localtesting stopServer" "Stop the running server container."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting resetServer" ]; then
    internal_print_command " localtesting resetServer" "Reset the server in a Docker container."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting initProject" ]; then
    internal_print_command " localtesting initProject" "Initialize a new server project and developer account."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting deployProject" ]; then
    internal_print_command " localtesting deployProject" "Deploy a new project version locally."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting cleanupProject" ]; then
    internal_print_command " localtesting cleanupProject" "Remove the server project and related resources."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting viewContainers" ]; then
    internal_print_command " localtesting viewContainers" "List all active Docker containers."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting viewDockerStats" ]; then
    internal_print_command " localtesting viewDockerStats" "Show real-time resource usage for Docker containers."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting viewDockerLogs" ]; then
    internal_print_command " localtesting viewDockerLogs [options]" "Display logs from Docker containers."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting viewLatestLogs" ]; then
    internal_print_command " localtesting viewLatestLogs [options]" "Display the latest container logs."
  fi
  # Installation
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation useCustomServer" ]; then
    internal_print_command " installation useCustomServer <name> <url>" "Set up an custom installation URL."
    if [ "$1" = "environment useEnvironment" ]; then
      echo "   produces:"
      echo "     - INSTALLATION_NAME"
      echo "     - INSTALLATION_URL"
    fi
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation useDemoServer" ]; then
    internal_print_command " installation useDemoServer" "Use the demo server installation."
    if [ "$1" = "installation useDemoServer" ]; then
      echo "   produces:"
      echo "     - INSTALLATION_NAME"
      echo "     - INSTALLATION_URL"
    fi
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation useLocalServer" ]; then
    internal_print_command " installation useLocalServer" "Use the local server installation."
    if [ "$1" = "installation useLocalServer" ]; then
      echo "   produces:"
      echo "     - INSTALLATION_NAME"
      echo "     - INSTALLATION_URL"
    fi
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation createTenant" ]; then
    internal_print_command " installation createTenant <alias>" "Create a new tenant."
    if [ "$1" = "installation createTenant" ]; then
      echo "   produces:"
      echo "     - TENANT"
    fi
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation createProject" ]; then
    internal_print_command " installation createProject <tenant> <project> <stage>" "Create a new project in the tenant."
    if [ "$1" = "installation createProject" ]; then
      echo "   produces:"
      echo "     - PROJECT"
      echo "     - STAGE"
      echo "     - DEVELOPER_USER"
      echo "     - DEVELOPER_PASSWORD"
    fi
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation deployVersion" ]; then
    internal_print_command " installation deployVersion" ""
    internal_print_command "   <tenant> <project> <stage>" ""
    internal_print_command "     <developer_user> <developer_password>" "Deploy a new version."
    if [ "$1" = "installation deployVersion" ]; then
      echo "   produces:"
      echo "     - VERSION"
      echo "     - DEPLOYMENT"
    fi
  fi
  # Admin
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin printCurrent" ]; then
    internal_print_command " admin printCurrent" "Print the current admin user."
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin createToken" ]; then
    internal_print_command " admin createToken <user> <password>" "Authenticate as an admin using credentials."
    if [ "$1" = "admin createToken" ]; then
      echo "   produces:"
      echo "     - ADMIN_TOKEN"
    fi
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin calculateShard" ]; then
    internal_print_command " admin calculateShard <shard_key>" "Calculate the shard based on the provided shard key."
    if [ "$1" = "admin calculateShard" ]; then
      echo "   produces:"
      echo "     - SHARD_INDEX"
      echo "     - SERVER_URI"
    fi
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin generateId" ]; then
    internal_print_command " admin generateId" "Generate a unique identifier."
    if [ "$1" = "admin generateId" ]; then
      echo "   produces:"
      echo "     - GENERATED_ID"
    fi
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin bcryptHash" ]; then
    internal_print_command " admin bcryptHash <value>" "Generate a bcrypt hash of the given value."
    if [ "$1" = "admin bcryptHash" ]; then
      echo "   produces:"
      echo "     - BCRYPTED_HASH"
    fi
  fi
  if [ -z "$1" -o "$1" = "admin" -o "$1" = "admin pingDockerHost" ]; then
    internal_print_command " admin pingDockerHost <docker_daemon_uri>" "Check the status of the specified Docker daemon."
    if [ "$1" = "admin pingDockerHost" ]; then
      echo "   produces:"
      echo "     - SUCCESSFUL"
    fi
  fi
  # Support
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support printCurrent" ]; then
    internal_print_command " support printCurrent" "Print the current support user."
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createToken" ]; then
    internal_print_command " support createToken <user> <password>" "Authenticate as a support user using credentials."
    if [ "$1" = "support createToken" ]; then
      echo "   produces:"
      echo "     - SUPPORT_TOKEN"
    fi
  fi
  # Tenant
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenant" ]; then
    internal_print_command " support createTenant" "Create a new tenant."
    if [ "$1" = "support createTenant" ]; then
      echo "   produces:"
      echo "     - TENANT"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenantAlias" ]; then
    internal_print_command " support createTenantAlias <tenant_id> <alias>" "Assign an alias to a tenant."
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenant" ]; then
    internal_print_command " support deleteTenant <tenant>" "Delete the specified tenant."
    if [ "$1" = "support deleteTenant" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  # Project
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createProject" ]; then
    internal_print_command " support createProject <tenant>" "Create a new project under the specified tenant."
    if [ "$1" = "support createProject" ]; then
      echo "   produces:"
      echo "     - PROJECT"
      echo "     - STAGE"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createProjectAlias" ]; then
    internal_print_command " support createProjectAlias <tenant> <project_id> <alias>" "Assign an alias to a project."
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteProject" ]; then
    internal_print_command " support deleteProject <tenant> <project>" "Delete the specified project."
    if [ "$1" = "support deleteProject" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  # Stage
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createStage" ]; then
    internal_print_command " support createStage <tenant> <project>" "Create a new stage for a project."
    if [ "$1" = "support createStage" ]; then
      echo "   produces:"
      echo "     - STAGE"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createStageAlias" ]; then
    internal_print_command " support createStageAlias <tenant> <stage_id> <alias>" "Assign an alias to a stage."
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteStage" ]; then
    internal_print_command " support deleteStage <tenant> <project> <stage>" "Delete the specified stage."
    if [ "$1" = "support deleteStage" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  # Developer
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createDeveloper" ]; then
    internal_print_command " support createDeveloper" "Create a new developer account."
    if [ "$1" = "support createDeveloper" ]; then
      echo "   produces:"
      echo "     - DEVELOPER_USER"
      echo "     - DEVELOPER_PASSWORD"
    fi
  fi
  # Permission
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createTenantPermission" ]; then
    internal_print_command " support createTenantPermission <tenant> <user> <permission>" "Grant a user permission for a tenant."
    if [ "$1" = "support createTenantPermission" ]; then
      echo "   permission:"
      echo "     - PROJECT_MANAGER"
      echo "     - TENANT_VIEWER"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteTenantPermission" ]; then
    internal_print_command " support deleteTenantPermission <tenant> <user> <permission>" "Remove a user's tenant permission."
    if [ "$1" = "support deleteTenantPermission" ]; then
      echo "   permission:"
      echo "     - PROJECT_MANAGER"
      echo "     - TENANT_VIEWER"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createProjectPermission" ]; then
    internal_print_command " support createProjectPermission" ""
    internal_print_command "   <tenant> <project> <user> <permission>" "Grant a user permission for a project."
    if [ "$1" = "support createProjectPermission" ]; then
      echo "   permission:"
      echo "     - STAGE_MANAGER"
      echo "     - VERSION_MANAGER"
      echo "     - PROJECT_VIEWER"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteProjectPermission" ]; then
    internal_print_command " support deleteProjectPermission" ""
    internal_print_command "   <tenant> <project> <user> <permission>" "Remove a user's project permission."
    if [ "$1" = "support deleteProjectPermission" ]; then
      echo "   permission:"
      echo "     - STAGE_MANAGER"
      echo "     - VERSION_MANAGER"
      echo "     - PROJECT_VIEWER"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support createStagePermission" ]; then
    internal_print_command " support createStagePermission" ""
    internal_print_command "   <tenant> <project> <stage> <user> <permission>" "Grant a user permission for a stage."
    if [ "$1" = "support createStagePermission" ]; then
      echo "   permission:"
      echo "     - DEPLOYMENT_MANAGER"
      echo "     - STAGE_VIEWER"
    fi
  fi
  if [ -z "$1" -o "$1" = "support" -o "$1" = "support deleteStagePermission" ]; then
    internal_print_command " support deleteStagePermission" ""
    internal_print_command "   <tenant> <project> <stage> <user> <permission>" "Remove a user's stage permission."
    if [ "$1" = "support deleteStagePermission" ]; then
      echo "   permission:"
      echo "     - DEPLOYMENT_MANAGER"
      echo "     - STAGE_VIEWER"
    fi
  fi
  # Developer
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer printCurrent" ]; then
    internal_print_command " developer printCurrent" "Print the current developer user."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createToken" ]; then
    internal_print_command " developer createToken <user> <password>" "Authenticate as a developer using credentials."
    if [ "$1" = "developer createToken" ]; then
      echo "   produces:"
      echo "     - DEVELOPER_TOKEN"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getTenantDetails" ]; then
    internal_print_command " developer getTenantDetails <tenant>" "Retrieve details of the specified tenant."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createProject" ]; then
    internal_print_command " developer createProject <tenant>" "Create a new project under the specified tenant."
    if [ "$1" = "developer createProject" ]; then
      echo "   produces:"
      echo "     - PROJECT"
      echo "     - STAGE"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createProjectAlias" ]; then
    internal_print_command " developer createProjectAlias <tenant> <project_id> <alias>" "Assign an alias to a project."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getProjectDetails" ]; then
    internal_print_command " developer getProjectDetails <tenant> <project>" "Retrieve details of the specified project."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteProject" ]; then
      internal_print_command " developer deleteProject <tenant> <project>" "Delete the specified project."
      if [ "$1" = "developer deleteProject" ]; then
        echo "   produces:"
        echo "     - DELETED"
      fi
    fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createStage" ]; then
    internal_print_command " developer createStage <tenant> <project>" "Create a new stage for a project."
    if [ "$1" = "developer createStage" ]; then
      echo "   produces:"
      echo "     - STAGE"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createStageAlias" ]; then
    internal_print_command " developer createStageAlias <tenant> <stage_id> <alias>" "Assign an alias to a stage."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getStageDetails" ]; then
    internal_print_command " developer getStageDetails <tenant> <project> <stage>" "Retrieve details of the specified stage."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteStage" ]; then
    internal_print_command " developer deleteStage <tenant> <project> <stage>" "Delete the specified stage."
    if [ "$1" = "developer deleteStage" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createVersion" ]; then
    internal_print_command " developer createVersion <tenant> <project> <config_path>" "Create a new version using the specified configuration."
    if [ "$1" = "developer createVersion" ]; then
      echo "   produces:"
      echo "     - VERSION"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer uploadFilesArchive" ]; then
    internal_print_command " developer uploadFilesArchive" ""
    internal_print_command "   <tenant> <version> <files_directory_path>" "Upload an archive of files for a version."
    if [ "$1" = "developer uploadFilesArchive" ]; then
      echo "   produces:"
      echo "     - FILES_ARCHIVE"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getVersionDetails" ]; then
    internal_print_command " developer getVersionDetails <tenant> <version>" "Retrieve details of the specified version."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteVersion" ]; then
    internal_print_command " developer deleteVersion <tenant> <version>" "Delete the specified version."
    if [ "$1" = "developer deleteVersion" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deployVersion" ]; then
    internal_print_command " developer deployVersion" ""
    internal_print_command "   <tenant> <project> <stage> <version>" "Deploy a version to the specified stage."
    if [ "$1" = "developer deployVersion" ]; then
      echo "   produces:"
      echo "     - DEPLOYMENT"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer getDeploymentDetails" ]; then
    internal_print_command " developer getDeploymentDetails <tenant> <deployment>" "Retrieve details of the specified deployment."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteDeployment" ]; then
    internal_print_command " developer deleteDeployment <tenant> <deployment>" "Delete the specified deployment."
    if [ "$1" = "developer deleteDeployment" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createLobbyRequest" ]; then
    internal_print_command " developer createLobbyRequest <tenant> <deployment>" "Create a lobby request for the specified deployment."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteLobby" ]; then
    internal_print_command " developer deleteLobby <lobby>" "Delete the specified lobby."
    if [ "$1" = "developer deleteLobby" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer createMatchmakerRequest" ]; then
    internal_print_command " developer createMatchmakerRequest <tenant> <deployment>" "Create a matchmaker request for the specified deployment."
  fi
  if [ -z "$1" -o "$1" = "developer" -o "$1" = "developer deleteMatchmaker" ]; then
    internal_print_command " developer deleteMatchmaker <matchmaker>" "Delete the specified matchmaker."
    if [ "$1" = "developer deleteMatchmaker" ]; then
      echo "   produces:"
      echo "     - DELETED"
    fi
  fi
}

logs() {
  internal_ensureEnvironment

  cat ${OMG_CONTEXT_DIRECTORY}/logs
}

# ENVIRONMENT

handler_environment_reset() {
  internal_ensureEnvironment

  cat /dev/null > ${OMG_CONTEXT_DIRECTORY}/environment
}

handler_environment_printCurrent() {
  internal_ensureEnvironment

  env | grep OMG_
}

handler_environment_printVariable() {
  internal_ensureEnvironment

  VARIABLE_NAME=$1

  if [ -z "${VARIABLE_NAME}" ]; then
    help "environment printVariable"
    exit 1
  fi

  VARIABLE_NAME="OMG_${VARIABLE_NAME}"

  if [ -z "${!VARIABLE_NAME}" ]; then
    echo "$(date) [CTL/environmentPrintVariable] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Variable was not found, VARIABLE_NAME=${VARIABLE_NAME}" >&2
    exit 1
  else
    echo -n ${!VARIABLE_NAME}
  fi
}

# Localtesting

handler_localtesting_runServer() {
  internal_ensureEnvironment

  docker run --privileged --rm --name "${OMG_LOCALTESTING_CONTAINER}" --tmpfs /tmp -p 8080:8080 -d "omgservers/dind:${OMGSERVERS_VERSION}"
}

handler_localtesting_stopServer() {
  internal_ensureEnvironment

  docker kill "${OMG_LOCALTESTING_CONTAINER}"
}

handler_localtesting_resetServer() {
  internal_ensureEnvironment

  handler_localtesting_stopServer || true
  handler_localtesting_runServer
}

handler_localtesting_initProject() {
  handler_installation_useLocalServer
  internal_ensureEnvironment

  echo "$(date) [CTL/localtestingInit] (${OMG_INSTALLATION_NAME:-unknown}) Using, LOCALTESTING_TENANT=\"${OMG_LOCALTESTING_TENANT}\"" >&2
  echo "$(date) [CTL/localtestingInit] (${OMG_INSTALLATION_NAME:-unknown}) Using, LOCALTESTING_PROJECT=\"${OMG_LOCALTESTING_PROJECT}\"" >&2
  echo "$(date) [CTL/localtestingInit] (${OMG_INSTALLATION_NAME:-unknown}) Using, LOCALTESTING_STAGE=\"${OMG_LOCALTESTING_STAGE}\"" >&2

  TENANT_ALIAS=${OMG_LOCALTESTING_TENANT}
  PROJECT_ALIAS=${OMG_LOCALTESTING_PROJECT}
  STAGE_ALIAS=${OMG_LOCALTESTING_STAGE}

  handler_support_createToken support support
  handler_installation_createTenant ${TENANT_ALIAS}
  handler_installation_createProject ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE_ALIAS}

  DEVELOPER_USER=$(handler_environment_printVariable DEVELOPER_USER)
  if [ -z "${DEVELOPER_USER}" ]; then
    echo "$(date) [CTL/localtestingInit] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DEVELOPER_USER was not found"
    exit 1
  fi

  DEVELOPER_PASSWORD=$(handler_environment_printVariable DEVELOPER_PASSWORD)
  if [ -z "${DEVELOPER_PASSWORD}" ]; then
    echo "$(date) [CTL/localtestingInit] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DEVELOPER_PASSWORD was not found"
    exit 1
  fi

  echo "export OMG_LOCALTESTING_DEVELOPER_USER=${DEVELOPER_USER}" >> ${OMG_CONTEXT_DIRECTORY}/environment
  echo "export OMG_LOCALTESTING_DEVELOPER_PASSWORD='${DEVELOPER_PASSWORD}'" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/localtestingInit] (${OMG_INSTALLATION_NAME:-unknown}) Localtesting project and developer account were initialized, DEVELOPER_USER=\"${DEVELOPER_USER}\"" >&2
}

handler_localtesting_deployProject() {
  handler_installation_useLocalServer
  internal_ensureEnvironment

  echo "$(date) [CTL/localtestingInstall] (${OMG_INSTALLATION_NAME:-unknown}) Using, LOCALTESTING_TENANT=\"${OMG_LOCALTESTING_TENANT}\"" >&2
  echo "$(date) [CTL/localtestingInstall] (${OMG_INSTALLATION_NAME:-unknown}) Using, LOCALTESTING_PROJECT=\"${OMG_LOCALTESTING_PROJECT}\"" >&2
  echo "$(date) [CTL/localtestingInstall] (${OMG_INSTALLATION_NAME:-unknown}) Using, LOCALTESTING_STAGE=\"${OMG_LOCALTESTING_STAGE}\"" >&2

  TENANT_ALIAS=${OMG_LOCALTESTING_TENANT}
  PROJECT_ALIAS=${OMG_LOCALTESTING_PROJECT}
  STAGE_ALIAS=${OMG_LOCALTESTING_STAGE}

  DEVELOPER_USER=${OMG_LOCALTESTING_DEVELOPER_USER}
  DEVELOPER_PASSWORD=${OMG_LOCALTESTING_DEVELOPER_PASSWORD}

  if [ -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    echo "$(date) [CTL/localtestingInstall] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Localtesting project was not initialized"
    exit 1
  fi

  handler_installation_deployVersion ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE_ALIAS} "${DEVELOPER_USER}" "${DEVELOPER_PASSWORD}"
}

handler_localtesting_cleanupProject() {
  handler_installation_useLocalServer
  internal_ensureEnvironment

  echo "$(date) [CTL/localtestingCleanup] (${OMG_INSTALLATION_NAME:-unknown}) Using, LOCALTESTING_TENANT=\"${OMG_LOCALTESTING_TENANT}\"" >&2

  TENANT_ALIAS=${OMG_LOCALTESTING_TENANT}

  handler_support_createToken support support
  handler_support_deleteTenant ${TENANT_ALIAS}

  echo "$(date) [CTL/localtestingCleanup] (${OMG_INSTALLATION_NAME:-unknown}) Localtesting project was deleted" >&2
}

handler_localtesting_viewContainers() {
  internal_ensureEnvironment

  docker exec "${OMG_LOCALTESTING_CONTAINER}" docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t{{.Command}}" $@
}

handler_localtesting_viewDockerStats() {
  internal_ensureEnvironment

  docker exec "${OMG_LOCALTESTING_CONTAINER}" docker stats
}

handler_localtesting_viewDockerLogs() {
  internal_ensureEnvironment

  if [ -z "$1" ] || [ "$1" = "-f" ]; then
    docker logs "${OMG_LOCALTESTING_CONTAINER}" $@
  else
    docker exec "${OMG_LOCALTESTING_CONTAINER}" docker logs $@
  fi
}

handler_localtesting_viewLatestLogs() {
  internal_ensureEnvironment

  docker exec "${OMG_LOCALTESTING_CONTAINER}" sh -c "docker logs \$(docker ps -q -l) $@"
}

# Installation

handler_installation_useCustomServer() {
  internal_ensureEnvironment

  INSTALLATION_NAME=$1
  INSTALLATION_URL=$2

  if [ -z "${INSTALLATION_NAME}" -o -z "${INSTALLATION_URL}" ]; then
    help "installation useCustomServer"
    exit 1
  fi

  echo "export OMG_INSTALLATION_NAME=${INSTALLATION_NAME}" >> ${OMG_CONTEXT_DIRECTORY}/environment
  echo "export OMG_INSTALLATION_URL=${INSTALLATION_URL}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  internal_ensureEnvironment
  echo "$(date) [CTL/installationUseCustomServer] (${OMG_INSTALLATION_NAME:-unknown}) Installation was set, INSTALLATION_URL=\"${INSTALLATION_URL}\"" >&2
}

handler_installation_useDemoServer() {
  handler_installation_useCustomServer demoserver https://demoserver.omgservers.dev
}

handler_installation_useLocalServer() {
  handler_installation_useCustomServer localserver http://localhost:8080
}

handler_installation_createTenant() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT_ALIAS=$1

  if [ -z "${TENANT_ALIAS}" ]; then
    help "installation createTenant"
    exit 1
  fi

  echo "$(date) [CTL/installationCreateTenant] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT_ALIAS=\"${TENANT_ALIAS}\"" >&2

  handler_support_createTenant
  TENANT=$(handler_environment_printVariable TENANT)
  if [ -z "${TENANT}" ]; then
    echo "$(date) [CTL/installationCreateTenant] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: TENANT was not found" >&2
    exit 1
  fi

  handler_support_createTenantAlias ${TENANT} ${TENANT_ALIAS}

  echo "$(date) [CTL/installationCreateTenant] (${OMG_INSTALLATION_NAME:-unknown}) Tenant was created, TENANT=${TENANT}"
}

handler_installation_createProject() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT_ALIAS=$1
  PROJECT_ALIAS=$2
  STAGE_ALIAS=$3

  if [ -z "${TENANT_ALIAS}" -o -z "${PROJECT_ALIAS}" -o -z "${STAGE_ALIAS}" ]; then
    help "installation createProject"
    exit 1
  fi

  echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT_ALIAS=\"${TENANT_ALIAS}\"" >&2
  echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT_ALIAS=\"${PROJECT_ALIAS}\"" >&2
  echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) Using, STAGE_ALIAS=\"${STAGE_ALIAS}\"" >&2

  handler_support_createProject ${TENANT_ALIAS}

  PROJECT=$(handler_environment_printVariable PROJECT)
  if [ -z "${PROJECT}" ]; then
    echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: PROJECT was not found" >&2
    exit 1
  fi

  STAGE=$(handler_environment_printVariable STAGE)
  if [ -z "${STAGE}" ]; then
    echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: STAGE was not found" >&2
    exit 1
  fi

  handler_support_createProjectAlias ${TENANT_ALIAS} ${PROJECT} ${PROJECT_ALIAS}
  handler_support_createStageAlias ${TENANT_ALIAS} ${STAGE} ${STAGE_ALIAS}

  echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) Create a new developer account" >&2

  handler_support_createDeveloper

  DEVELOPER_USER=$(handler_environment_printVariable DEVELOPER_USER)
  if [ -z "${DEVELOPER_USER}" ]; then
    echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DEVELOPER_USER was not found" >&2
    exit 1
  fi

  DEVELOPER_PASSWORD=$(handler_environment_printVariable DEVELOPER_PASSWORD)
  if [ -z "${DEVELOPER_PASSWORD}" ]; then
    echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DEVELOPER_PASSWORD was not found" >&2
    exit 1
  fi

  echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) Configure developer permissions" >&2

  handler_support_createTenantPermission ${TENANT_ALIAS} ${DEVELOPER_USER} TENANT_VIEWER
  handler_support_createProjectPermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${DEVELOPER_USER} VERSION_MANAGER
  handler_support_createProjectPermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${DEVELOPER_USER} PROJECT_VIEWER
  handler_support_createStagePermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE_ALIAS} ${DEVELOPER_USER} DEPLOYMENT_MANAGER
  handler_support_createStagePermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE_ALIAS} ${DEVELOPER_USER} STAGE_VIEWER

  echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) Login using developer account, DEVELOPER_USER=\"${DEVELOPER_USER}\"" >&2
  handler_developer_createToken ${DEVELOPER_USER} ${DEVELOPER_PASSWORD}

  echo "$(date) [CTL/installationCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) Project was created"
}

handler_installation_deployVersion() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl

  TENANT_ALIAS=$1
  PROJECT_ALIAS=$2
  STAGE_ALIAS=$3
  DEVELOPER_USER=$4
  DEVELOPER_PASSWORD=$5

  if [ -z "${TENANT_ALIAS}" -o -z "${PROJECT_ALIAS}" -o -z "${STAGE_ALIAS}" -o -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    help "installation deployVersion"
    exit 1
  fi

  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT_ALIAS=\"${TENANT_ALIAS}\"" >&2
  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT_ALIAS=\"${PROJECT_ALIAS}\"" >&2
  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, STAGE_ALIAS=\"${STAGE_ALIAS}\"" >&2
  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, DEVELOPER_USER=\"${DEVELOPER_USER}\"" >&2

  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Create a new version" >&2

  handler_developer_createToken "${DEVELOPER_USER}" "${DEVELOPER_PASSWORD}"
  handler_developer_createVersion ${TENANT_ALIAS} ${PROJECT_ALIAS} "./config.json"
  VERSION=$(handler_environment_printVariable VERSION)
  if [ -z "${VERSION}" -o "${VERSION}" = "null" ]; then
    echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: VERSION was not found" >&2
    exit 1
  fi

  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Push docker image" >&2

  REGISTRY_SERVER=$(echo ${OMG_INSTALLATION_URL} | sed 's/^https*:\/\///')
  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, REGISTRY_SERVER=\"${REGISTRY_SERVER}\"" >&2

  DOCKER_IMAGE=${OMG_DOCKER_IMAGE}
  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, DOCKER_IMAGE=\"${DOCKER_IMAGE}\"" >&2
  TARGET_IMAGE="${REGISTRY_SERVER}/omgservers/${TENANT_ALIAS}/${PROJECT_ALIAS}/universal:${VERSION}"
  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, TARGET_IMAGE=\"${TARGET_IMAGE}\"" >&2
  echo ${DEVELOPER_PASSWORD} | docker login -u ${DEVELOPER_USER} --password-stdin "${REGISTRY_SERVER}" >&2
  docker tag ${DOCKER_IMAGE} ${TARGET_IMAGE} >&2
  docker push ${TARGET_IMAGE} >&2

  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Waiting for the Docker image to propagate..." >&2

  while [ $(handler_developer_getVersionDetails ${TENANT_ALIAS} ${VERSION} | jq '.details.images | length') -eq 0 ]; do
    sleep 1
  done

  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Deploy a new version" >&2

  handler_developer_deployVersion ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE_ALIAS} ${VERSION}
  DEPLOYMENT=$(handler_environment_printVariable DEPLOYMENT)
  if [ -z "${DEPLOYMENT}" -o "${DEPLOYMENT}" = "null" ]; then
    echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DEPLOYMENT was not found" >&2
    exit 1
  fi

  echo "$(date) [CTL/installationDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Version was deployed, VERSION=${VERSION}, DEPLOYMENT=${DEPLOYMENT}"
}

# ADMIN

handler_admin_printCurrent() {
  internal_ensureEnvironment

  ADMIN_USER=${OMG_ADMIN_USER}
  ADMIN_TOKEN=${OMG_ADMIN_TOKEN}

  if [ -z "${ADMIN_USER}" ]; then
    echo "$(date) [CTL/adminPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Current admin was not found" >&2
    exit 1
  fi

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) [CTL/adminPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) Current admin was found, ADMIN_USER=${ADMIN_USER}, (without token)"
  else
    echo "$(date) [CTL/adminPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) Current admin was found, ADMIN_USER=${ADMIN_USER}, (token exists)"
  fi
}

handler_admin_createToken() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl

  ADMIN_USER=$1
  ADMIN_PASSWORD=$2

  if [ -z "${ADMIN_USER}" -o -z "${ADMIN_PASSWORD}" ]; then
    help "admin createToken"
    exit 1
  fi

  echo "$(date) [CTL/adminCreateToken] (${OMG_INSTALLATION_NAME:-unknown}) Using, ADMIN_USER=\"${ADMIN_USER}\"" >&2

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/admin/request/create-token"
  REQUEST="{\"user\": \"${ADMIN_USER}\", \"password\": \"${ADMIN_PASSWORD}\"}"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${OMG_CONTEXT_DIRECTORY}/temp/admin-create-token_${ADMIN_USER}.json)

  cat ${OMG_CONTEXT_DIRECTORY}/temp/admin-create-token_${ADMIN_USER}.json >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/adminCreateToken] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2 >&2
    exit 1
  fi

  ADMIN_TOKEN=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/admin-create-token_${ADMIN_USER}.json | jq -r .raw_token)
  if [ -z "$ADMIN_TOKEN" -o "$ADMIN_TOKEN" == "null" ]; then
    echo "$(date) [CTL/adminCreateToken] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: ADMIN_TOKEN was not received" >&2
    exit 1
  fi

  echo "export OMG_ADMIN_USER=$ADMIN_USER" >> ${OMG_CONTEXT_DIRECTORY}/environment
  echo "export OMG_ADMIN_TOKEN=$ADMIN_TOKEN" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/adminCreateToken] (${OMG_INSTALLATION_NAME:-unknown}) Admin token was created"
}

handler_admin_calculateShard() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl

  SHARD_KEY=$1

  if [ -z "${SHARD_KEY}" ]; then
    help "admin calculateShard"
    exit 1
  fi

  ADMIN_TOKEN=$OMG_ADMIN_TOKEN

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) [CTL/adminCalculateShard] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Current admin token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/admin/request/calculate-shard"
  REQUEST="{\"shard_key\": \"${SHARD_KEY}\"}"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${ADMIN_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMG_CONTEXT_DIRECTORY}/temp/admin-calculate_shard_${SHARD_KEY}.json)

  cat ${OMG_CONTEXT_DIRECTORY}/temp/admin-calculate_shard_${SHARD_KEY}.json >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/adminCalculateShard] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2 >&2
    exit 1
  fi

  SHARD_INDEX=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/admin-calculate_shard_${SHARD_KEY}.json | jq -r .shard_index)
  if [ -z "${SHARD_INDEX}" -o "${SHARD_INDEX}" == "null" ]; then
    echo "$(date) [CTL/adminCalculateShard] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: SHARD_INDEX was not received" >&2
    exit 1
  fi
  echo "export OMG_SHARD_INDEX=${SHARD_INDEX}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  SERVER_URI=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/admin-calculate_shard_${SHARD_KEY}.json | jq -r .server_uri)
  if [ -z "${SERVER_URI}" -o "${SERVER_URI}" == "null" ]; then
    echo "$(date) [CTL/adminCalculateShard] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: SERVER_URI was not received" >&2
    exit 1
  fi
  echo "export OMG_SERVER_URI=${SERVER_URI}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/adminCalculateShard] (${OMG_INSTALLATION_NAME:-unknown}) Shard was calculated, SHARD_INDEX=${SHARD_INDEX}, SERVER_URI=${SERVER_URI}"
}

handler_admin_generateId() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl

  ADMIN_TOKEN=$OMG_ADMIN_TOKEN

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) [CTL/adminGenerateId] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Current admin token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/admin/request/generate-id"
  REQUEST="{}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/admin-generate-id.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${ADMIN_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/adminGenerateId] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2 >&2
    exit 1
  fi

  GENERATED_ID=$(cat ${RESPONSE_FILE} | jq -r .id)
  if [ -z "${GENERATED_ID}" -o "${GENERATED_ID}" == "null" ]; then
    echo "$(date) [CTL/adminGenerateId] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: GENERATED_ID was not received" >&2
    exit 1
  fi
  echo "export OMG_GENERATED_ID=${GENERATED_ID}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/adminGenerateId] (${OMG_INSTALLATION_NAME:-unknown}) Id was generated, GENERATED_ID=\"${GENERATED_ID}\""
}

handler_admin_bcryptHash() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl

  VALUE=$1

  if [ -z "${VALUE}" ]; then
    help "admin bcryptHash"
    exit 1
  fi

  ADMIN_TOKEN=$OMG_ADMIN_TOKEN

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) [CTL/adminBcryptHash] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Current admin token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/admin/request/bcrypt-hash"
  REQUEST="{ \"value\": \"${VALUE}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/admin-bcrypt-hash.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${ADMIN_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/adminBcryptHash] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2 >&2
    exit 1
  fi

  BCRYPTED_HASH=$(cat ${RESPONSE_FILE} | jq -r .hash)
  if [ -z "${BCRYPTED_HASH}" -o "${BCRYPTED_HASH}" == "null" ]; then
    echo "$(date) [CTL/adminBcryptHash] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: BCRYPTED_HASH was not received" >&2
    exit 1
  fi
  echo "export OMG_BCRYPTED_HASH=${BCRYPTED_HASH}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/adminBcryptHash] (${OMG_INSTALLATION_NAME:-unknown}) Value was bcrypted, BCRYPTED_HASH=${BCRYPTED_HASH}"
}

handler_admin_pingDockerHost() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl

  DOCKER_DAEMON_URI=$1

  if [ -z "${DOCKER_DAEMON_URI}" ]; then
    help "admin pingDockerHost"
    exit 1
  fi

  ADMIN_TOKEN=$OMG_ADMIN_TOKEN

  if [ -z "${ADMIN_TOKEN}" ]; then
    echo "$(date) [CTL/adminPingDockerHost] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Current admin token was not found" >&2
    exit 1
  fi

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/admin/request/ping-docker-host"
  REQUEST="{\"docker_daemon_uri\": \"${DOCKER_DAEMON_URI}\"}"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${ADMIN_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMG_CONTEXT_DIRECTORY}/temp/admin-ping-docker-host.json)

  cat ${OMG_CONTEXT_DIRECTORY}/temp/admin-ping-docker-host.json >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/adminPingDockerHost] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2 >&2
    exit 1
  fi

  SUCCESSFUL=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/admin-ping-docker-host.json | jq -r .successful)
  if [ -z "${SUCCESSFUL}" -o "${SUCCESSFUL}" == "null" ]; then
    echo "$(date) [CTL/adminPingDockerHost] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: SUCCESSFUL was not received" >&2
    exit 1
  fi
  echo "export OMG_SUCCESSFUL=${SUCCESSFUL}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  FROM_SERVER=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/admin-ping-docker-host.json | jq -r .from_server)
  if [ -z "${FROM_SERVER}" -o "${FROM_SERVER}" == "null" ]; then
    echo "$(date) [CTL/adminPingDockerHost] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: FROM_SERVER was not received" >&2
    exit 1
  fi

  if [ "${SUCCESSFUL}" == "true" ]; then
    echo "$(date) [CTL/adminPingDockerHost] (${OMG_INSTALLATION_NAME:-unknown}) Docker host was pinged, FROM_SERVER=${FROM_SERVER}, DOCKER_DAEMON_URI=${DOCKER_DAEMON_URI}"
  else
    echo "$(date) [CTL/adminPingDockerHost] (${OMG_INSTALLATION_NAME:-unknown}) Docker host was not pinged, FROM_SERVER=${FROM_SERVER}, DOCKER_DAEMON_URI=${DOCKER_DAEMON_URI}"
  fi
}

# SUPPORT

handler_support_printCurrent() {
  internal_ensureEnvironment

  SUPPORT_USER=${OMG_SUPPORT_USER}
  SUPPORT_TOKEN=${OMG_SUPPORT_TOKEN}

  if [ -z "${SUPPORT_USER}" ]; then
    echo "$(date) [CTL/supportPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Current support was not found" >&2
    exit 1
  fi

  if [ -z "${SUPPORT_TOKEN}" ]; then
    echo "$(date) [CTL/supportPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) Current support was found, SUPPORT_USER=$SUPPORT_USER, (without token)"
  else
    echo "$(date) [CTL/supportPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) Current support was found, SUPPORT_USER=$SUPPORT_USER, (token exists)"
  fi
}

handler_support_createToken() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl

  SUPPORT_USER=$1
  SUPPORT_PASSWORD=$2

  if [ -z "${SUPPORT_USER}" -o -z "${SUPPORT_PASSWORD}" ]; then
    help "support createToken"
    exit 1
  fi

  echo "$(date) [CTL/supportCreateToken] (${OMG_INSTALLATION_NAME:-unknown}) Using, SUPPORT_USER=\"$SUPPORT_USER\"" >&2

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-token"
  REQUEST="{\"user\": \"${SUPPORT_USER}\", \"password\": \"${SUPPORT_PASSWORD}\"}"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${OMG_CONTEXT_DIRECTORY}/temp/support-create-token_${SUPPORT_USER}.json)

  cat ${OMG_CONTEXT_DIRECTORY}/temp/support-create-token_${SUPPORT_USER}.json >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateToken] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2 >&2
    exit 1
  fi

  RAW_TOKEN=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/support-create-token_${SUPPORT_USER}.json | jq -r .raw_token)
  if [ -z "$RAW_TOKEN" -o "$RAW_TOKEN" == "null" ]; then
    echo "$(date) [CTL/supportCreateToken] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: RAW_TOKEN was not received" >&2
    exit 1
  fi

  echo "export OMG_SUPPORT_USER=$SUPPORT_USER" >> ${OMG_CONTEXT_DIRECTORY}/environment
  echo "export OMG_SUPPORT_TOKEN=$RAW_TOKEN" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/supportCreateToken] (${OMG_INSTALLATION_NAME:-unknown}) Support token was created" >&2
}

handler_support_createTenant() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-tenant"
  REQUEST="{}"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMG_CONTEXT_DIRECTORY}/temp/support-create-tenant.json)

  cat ${OMG_CONTEXT_DIRECTORY}/temp/support-create-tenant.json >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateTenant] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  TENANT=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/support-create-tenant.json | jq -r .id)
  if [ -z "$TENANT" -o "$TENANT" == "null" ]; then
    echo "$(date) [CTL/supportCreateTenant] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: TENANT was not received" >&2
    exit 1
  fi
  echo "export OMG_TENANT=$TENANT" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/supportCreateTenant] (${OMG_INSTALLATION_NAME:-unknown}) Tenant was created, TENANT=\"$TENANT\"" >&2
}

handler_support_createTenantAlias() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT_ID=$1
  ALIAS=$2

  if [ -z "${TENANT_ID}" -o -z "${ALIAS}" ]; then
    help "support createTenantAlias"
    exit 1
  fi

  echo "$(date) [CTL/supportCreateTenantAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT_ID=\"${TENANT_ID}\"" >&2
  echo "$(date) [CTL/supportCreateTenantAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, ALIAS=\"${ALIAS}\"" >&2

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-tenant-alias"
  REQUEST="{\"tenant_id\": ${TENANT_ID}, \"alias\": \"${ALIAS}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-create-tenant-alias_${TENANT_ID}_${ALIAS}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateTenantAlias] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  echo "$(date) [CTL/supportCreateTenantAlias] (${OMG_INSTALLATION_NAME:-unknown}) Tenant alias was created"
}

handler_support_deleteTenant() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "support deleteTenant"
    exit 1
  fi

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/delete-tenant"
  REQUEST="{\"tenant\": \"${TENANT}\"}"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMG_CONTEXT_DIRECTORY}/temp/support-delete-tenant_${TENANT}.json)

  cat ${OMG_CONTEXT_DIRECTORY}/temp/support-delete-tenant_${TENANT}.json >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportDeleteTenant] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/support-delete-tenant_${TENANT}.json | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "$(date) [CTL/supportDeleteTenant] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMG_DELETED=$DELETED" >> ${OMG_CONTEXT_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) [CTL/supportDeleteTenant] (${OMG_INSTALLATION_NAME:-unknown}) Tenant was deleted, TENANT=${TENANT}"
  else
    echo "$(date) [CTL/supportDeleteTenant] (${OMG_INSTALLATION_NAME:-unknown}) Tenant was not deleted, TENANT=${TENANT}"
  fi
}

# Project

handler_support_createProject() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "support createProject"
    exit 1
  fi

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-project"
  REQUEST="{\"tenant\": \"${TENANT}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-create-project_${TENANT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  PROJECT=$(cat ${RESPONSE_FILE} | jq -r .project_id)
  if [ -z "$PROJECT" -o "$PROJECT" == "null" ]; then
    echo "$(date) [CTL/supportCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: PROJECT was not received" >&2
    exit 1
  fi
  echo "export OMG_PROJECT=${PROJECT}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  STAGE=$(cat ${RESPONSE_FILE} | jq -r .stage_id)
  if [ -z "${STAGE}" -o "${STAGE}" == "null" ]; then
    echo "$(date) [CTL/supportCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: STAGE was not received" >&2
    exit 1
  fi
  echo "export OMG_STAGE=${STAGE}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/supportCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) Project was created, PROJECT=\"${PROJECT}\", STAGE=\"${STAGE}\""
}

handler_support_createProjectAlias() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1
  PROJECT_ID=$2
  ALIAS=$3

  if [ -z "${TENANT}" -o -z "${PROJECT_ID}" -o -z "${ALIAS}" ]; then
    help "support createProjectAlias"
    exit 1
  fi

  echo "$(date) [CTL/supportCreateProjectAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/supportCreateProjectAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT_ID=\"${PROJECT_ID}\"" >&2
  echo "$(date) [CTL/supportCreateProjectAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, ALIAS=\"${ALIAS}\"" >&2

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-project-alias"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project_id\": \"${PROJECT_ID}\", \"alias\": \"${ALIAS}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-create-project-alias_${TENANT}_${PROJECT_ID}_${ALIAS}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateProjectAlias] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  echo "$(date) [CTL/supportCreateProjectAlias] (${OMG_INSTALLATION_NAME:-unknown}) Project alias was created"
}

handler_support_deleteProject() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "support deleteProject"
    exit 1
  fi

  SUPPORT_TOKEN=${OMG_SUPPORT_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/delete-project"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-delete-project_${TENANT}_${PROJECT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportDeleteProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "$(date) [CTL/supportDeleteProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMG_DELETED=$DELETED" >> ${OMG_CONTEXT_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) [CTL/supportDeleteProject] (${OMG_INSTALLATION_NAME:-unknown}) Project was deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  else
    echo "$(date) [CTL/supportDeleteProject] (${OMG_INSTALLATION_NAME:-unknown}) Project was not deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  fi
}

# Stage

handler_support_createStage() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

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

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-stage"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-create-stage_${TENANT}_${PROJECT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateStage] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  STAGE=$(cat ${RESPONSE_FILE} | jq -r .stage_id)
  if [ -z "${STAGE}" -o "${STAGE}" == "null" ]; then
    echo "$(date) [CTL/supportCreateStage] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: STAGE was not received" >&2
    exit 1
  fi
  echo "export OMG_STAGE=${STAGE}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/supportCreateStage] (${OMG_INSTALLATION_NAME:-unknown}) Stage was created, STAGE=\"${STAGE}\""
}

handler_support_createStageAlias() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1
  STAGE_ID=$2
  ALIAS=$3

  if [ -z "${TENANT}" -o -z "${STAGE_ID}" -o -z "${ALIAS}" ]; then
    help "support createStageAlias"
    exit 1
  fi

  echo "$(date) [CTL/supportCreateStageAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/supportCreateStageAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, STAGE_ID=\"${STAGE_ID}\"" >&2
  echo "$(date) [CTL/supportCreateStageAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, ALIAS=\"${ALIAS}\"" >&2

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-stage-alias"
  REQUEST="{\"tenant\": \"${TENANT}\", \"stage_id\": \"${STAGE_ID}\", \"alias\": \"${ALIAS}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-create-stage-alias_${TENANT}_${STAGE_ID}_${ALIAS}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateStageAlias] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  echo "$(date) [CTL/supportCreateStageAlias] (${OMG_INSTALLATION_NAME:-unknown}) Stage alias was created"
}

handler_support_deleteStage() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1
  PROJECT=$2
  STAGE=$3

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" ]; then
    help "support deleteStage"
    exit 1
  fi

  SUPPORT_TOKEN=${OMG_SUPPORT_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/delete-stage"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-delete-project_${TENANT}_${PROJECT}_${STAGE}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportDeleteStage] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "$(date) [CTL/supportDeleteStage] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMG_DELETED=$DELETED" >> ${OMG_CONTEXT_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) [CTL/supportDeleteStage] (${OMG_INSTALLATION_NAME:-unknown}) Stage was deleted"
  else
    echo "$(date) [CTL/supportDeleteStage] (${OMG_INSTALLATION_NAME:-unknown}) Stage was not deleted"
  fi
}

# Developer

handler_support_createDeveloper() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-developer"
  REQUEST="{}"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${OMG_CONTEXT_DIRECTORY}/temp/support-create-developer_${TENANT}.json)

  cat ${OMG_CONTEXT_DIRECTORY}/temp/support-create-developer_${TENANT}.json >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateDeveloper] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  USER=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/support-create-developer_${TENANT}.json | jq -r .user_id)
  if [ -z "$USER" -o "$USER" == "null" ]; then
    echo "$(date) [CTL/supportCreateDeveloper] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: USER was not received" >&2
    exit 1
  fi
  echo "export OMG_DEVELOPER_USER=$USER" >> ${OMG_CONTEXT_DIRECTORY}/environment

  PASSWORD=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/support-create-developer_${TENANT}.json | jq -r .password)
  if [ -z "$PASSWORD" -o "$PASSWORD" == "null" ]; then
    echo "$(date) [CTL/supportCreateDeveloper] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: PASSWORD was not received" >&2
    exit 1
  fi
  echo "export OMG_DEVELOPER_PASSWORD='$PASSWORD'" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/supportCreateDeveloper] (${OMG_INSTALLATION_NAME:-unknown}) Developer was created, DEVELOPER_USER=\"${USER}\""

  handler_developer_createToken ${USER} ${PASSWORD}
}

handler_support_createTenantPermission() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1
  USER=$2
  PERMISSION=$3

  if [ -z "${TENANT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support createTenantPermission"
    exit 1
  fi

  echo "$(date) [CTL/supportCreateTenantPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/supportCreateTenantPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, USER=\"${USER}\"" >&2
  echo "$(date) [CTL/supportCreateTenantPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, PERMISSION=\"${PERMISSION}\"" >&2

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-tenant-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"user_id\": \"${USER}\", \"permissions_to_create\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-create-tenant-permissions_${TENANT}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateTenantPermission] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .created_permissions)
  echo "$(date) [CTL/supportCreateTenantPermission] (${OMG_INSTALLATION_NAME:-unknown}) Created permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

handler_support_deleteTenantPermission() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1
  USER=$2
  PERMISSION=$3

  if [ -z "${TENANT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support deleteTenantPermission"
    exit 1
  fi

  echo "$(date) [CTL/supportDeleteTenantPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/supportDeleteTenantPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, USER=\"${USER}\"" >&2
  echo "$(date) [CTL/supportDeleteTenantPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, PERMISSION=\"${PERMISSION}\"" >&2

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/delete-tenant-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"user_id\": \"${USER}\", \"permissions_to_delete\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-delete-tenant-permissions_${TENANT}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportDeleteTenantPermission] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .deleted_permissions)
  echo "$(date) [CTL/supportDeleteTenantPermission] (${OMG_INSTALLATION_NAME:-unknown}) Deleted permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

handler_support_createProjectPermission() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1
  PROJECT=$2
  USER=$3
  PERMISSION=$4

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support createProjectPermission"
    exit 1
  fi

  echo "$(date) [CTL/supportCreateProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/supportCreateProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT=\"${PROJECT}\"" >&2
  echo "$(date) [CTL/supportCreateProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, USER=\"${USER}\"" >&2
  echo "$(date) [CTL/supportCreateProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, PERMISSION=\"${PERMISSION}\"" >&2

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-project-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"user_id\": \"${USER}\", \"permissions_to_create\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-create-project-permissions_${TENANT}_${PROJECT}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .created_permissions)
  echo "$(date) [CTL/supportCreateProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) Created tenant project permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

handler_support_deleteProjectPermission() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1
  PROJECT=$2
  USER=$3
  PERMISSION=$4

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support deleteProjectPermission"
    exit 1
  fi

  echo "$(date) [CTL/supportDeleteProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/supportDeleteProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT=\"${PROJECT}\"" >&2
  echo "$(date) [CTL/supportDeleteProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, USER=\"${USER}\"" >&2
  echo "$(date) [CTL/supportDeleteProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, PERMISSION=\"${PERMISSION}\"" >&2

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/delete-project-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"user_id\": \"${USER}\", \"permissions_to_delete\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-delete-project-permissions_${TENANT}_${PROJECT}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportDeleteProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .deleted_permissions)
  echo "$(date) [CTL/supportDeleteProjectPermission] (${OMG_INSTALLATION_NAME:-unknown}) Deleted tenant project permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

handler_support_createStagePermission() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1
  PROJECT=$2
  STAGE=$3
  USER=$4
  PERMISSION=$5

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support createStagePermission"
    exit 1
  fi

  echo "$(date) [CTL/supportCreateStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/supportCreateStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT=\"${PROJECT}\"" >&2
  echo "$(date) [CTL/supportCreateStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, STAGE=\"${STAGE}\"" >&2
  echo "$(date) [CTL/supportCreateStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, USER=\"${USER}\"" >&2
  echo "$(date) [CTL/supportCreateStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, PERMISSION=\"${PERMISSION}\"" >&2

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/create-stage-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\", \"user_id\": \"${USER}\", \"permissions_to_create\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-create-stage-permissions_${TENANT}_${PROJECT}_${STAGE}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportCreateStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  CREATED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .created_permissions)
  echo "$(date) [CTL/supportCreateStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Created tenant stage permissions, CREATED_PERMISSION=${CREATED_PERMISSION}"
}

handler_handler_support_deleteStagePermission() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureSupportToken

  TENANT=$1
  PROJECT=$2
  STAGE=$3
  USER=$4
  PERMISSION=$5

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" -o -z "${USER}" -o -z "${PERMISSION}" ]; then
    help "support deleteStagePermission"
    exit 1
  fi

  echo "$(date) [CTL/supportDeleteStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/supportDeleteStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT=\"${PROJECT}\"" >&2
  echo "$(date) [CTL/supportDeleteStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, STAGE=\"${STAGE}\"" >&2
  echo "$(date) [CTL/supportDeleteStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, USER=\"${USER}\"" >&2
  echo "$(date) [CTL/supportDeleteStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Using, PERMISSION=\"${PERMISSION}\"" >&2

  SUPPORT_TOKEN=$OMG_SUPPORT_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/support/request/delete-stage-permissions"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\", \"user_id\": ${USER}, \"permissions_to_delete\": [\"${PERMISSION}\"]}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/support-delete-stage-permissions_${TENANT}_${PROJECT}_${STAGE}_${DEVELOPER_USER}_${PERMISSION}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${SUPPORT_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/supportDeleteStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED_PERMISSION=$(cat ${RESPONSE_FILE} | jq -c -r .deleted_permissions)
  echo "$(date) [CTL/supportDeleteStagePermission] (${OMG_INSTALLATION_NAME:-unknown}) Deleted tenant stage permissions, DELETED_PERMISSION=${DELETED_PERMISSION}"
}

# DEVELOPER

handler_developer_printCurrent() {
  DEVELOPER_USER=${OMG_DEVELOPER_USER}
  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  if [ -z "${DEVELOPER_USER}" ]; then
    echo "$(date) [CTL/developerPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Current developer was not found" >&2
    exit 1
  fi

  if [ -z "${DEVELOPER_TOKEN}" ]; then
    echo "$(date) [CTL/developerPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) Current developer was found, DEVELOPER_USER=${DEVELOPER_USER}, (without token)"
  else
    echo "$(date) [CTL/developerPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) Current developer was found, DEVELOPER_USER=${DEVELOPER_USER}, (token exists)"
  fi
}

handler_developer_createToken() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl

  DEVELOPER_USER=$1
  DEVELOPER_PASSWORD=$2

  if [ -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    help "developer createToken"
    exit 1
  fi

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/create-token"
  REQUEST="{\"user_id\": \"${DEVELOPER_USER}\", \"password\": \"${DEVELOPER_PASSWORD}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-create-token_${DEVELOPER_USER}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  RAW_TOKEN=$(cat ${RESPONSE_FILE} | jq -r .raw_token)
  if [ -z "$RAW_TOKEN" -o "$RAW_TOKEN" == "null" ]; then
    echo "$(date) [CTL/developerPrintCurrent] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: RAW_TOKEN was not received" >&2
    exit 1
  fi
  echo "export OMG_DEVELOPER_USER=$DEVELOPER_USER" >> ${OMG_CONTEXT_DIRECTORY}/environment
  echo "export OMG_DEVELOPER_TOKEN=$RAW_TOKEN" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/developerCreateToken] (${OMG_INSTALLATION_NAME:-unknown}) Developer token was created" >&2
}

handler_developer_getTenantDetails() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "developer getTenantDetails"
    exit 1
  fi

  echo "$(date) [CTL/developerGetTenantDetails] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"$TENANT\"" >&2

  DEVELOPER_TOKEN=$OMG_DEVELOPER_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/get-tenant-details"
  REQUEST="{\"tenant\": \"${TENANT}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-get-tenant-details_${TENANT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerGetTenantDetails] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  internal_format_file ${RESPONSE_FILE}
}

handler_developer_createProject() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1

  if [ -z "${TENANT}" ]; then
    help "developer createProject"
    exit 1
  fi

  echo "$(date) [CTL/developerCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"$TENANT\"" >&2

  DEVELOPER_TOKEN=$OMG_DEVELOPER_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/create-project"
  REQUEST="{\"tenant\": \"${TENANT}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-create-project_${TENANT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  PROJECT=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/developer-create-project_${TENANT}.json | jq -r .project_id)
  if [ -z "$PROJECT" -o "$PROJECT" == "null" ]; then
    echo "$(date) [CTL/developerCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: PROJECT was not received" >&2
    exit 1
  fi
  echo "export OMG_PROJECT=$PROJECT" >> ${OMG_CONTEXT_DIRECTORY}/environment

  STAGE=$(cat ${OMG_CONTEXT_DIRECTORY}/temp/developer-create-project_${TENANT}.json | jq -r .stage_id)
  if [ -z "$STAGE" -o "$STAGE" == "null" ]; then
    echo "$(date) [CTL/developerCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: STAGE was not received" >&2
    exit 1
  fi
  echo "export OMG_STAGE=$STAGE" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/developerCreateProject] (${OMG_INSTALLATION_NAME:-unknown}) Project was created, PROJECT=\"${PROJECT}\", STAGE=\"${STAGE}\""
}

handler_developer_createProjectAlias() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  PROJECT_ID=$2
  ALIAS=$3

  if [ -z "${TENANT}" -o -z "${PROJECT_ID}" -o -z "${ALIAS}" ]; then
    help "developer createProjectAlias"
    exit 1
  fi

  echo "$(date) [CTL/developerCreateProjectAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerCreateProjectAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT_ID=\"${PROJECT_ID}\"" >&2
  echo "$(date) [CTL/developerCreateProjectAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, ALIAS=\"${ALIAS}\"" >&2

  DEVELOPER_TOKEN=$OMG_DEVELOPER_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/create-project-alias"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project_id\": ${PROJECT_ID}, \"alias\": \"${ALIAS}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-create-project-alias_${TENANT}_${PROJECT_ID}_${ALIAS}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerCreateProjectAlias] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  echo "$(date) [CTL/developerCreateProjectAlias] (${OMG_INSTALLATION_NAME:-unknown}) Project alias was created"
}

handler_developer_getProjectDetails() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "developer getProjectDetails"
    exit 1
  fi

  echo "$(date) [CTL/developerGetProjectDetails] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerGetProjectDetails] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT=\"${PROJECT}\"" >&2

  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/get-project-details"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-get-project-details_${TENANT}_${PROJECT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerGetProjectDetails] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  internal_format_file ${RESPONSE_FILE}
}

handler_developer_deleteProject() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "developer deleteProject"
    exit 1
  fi

  echo "$(date) [CTL/developerDeleteProject] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerDeleteProject] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT=\"${PROJECT}\"" >&2

  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/delete-project"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\" }"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-delete-project_${TENANT}_${PROJECT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerDeleteProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "$(date) [CTL/developerDeleteProject] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMG_DELETED=$DELETED" >> ${OMG_CONTEXT_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) [CTL/developerDeleteProject] (${OMG_INSTALLATION_NAME:-unknown}) Project was deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  else
    echo "$(date) [CTL/developerDeleteProject] (${OMG_INSTALLATION_NAME:-unknown}) Project was not deleted, TENANT=${TENANT}, PROJECT=${PROJECT}"
  fi
}

handler_developer_createStage() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  PROJECT=$2

  if [ -z "${TENANT}" -o -z "${PROJECT}" ]; then
    help "developer createStage"
    exit 1
  fi

  echo "$(date) [CTL/developerCreateStage] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"$TENANT\"" >&2
  echo "$(date) [CTL/developerCreateStage] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT=\"${PROJECT}\"" >&2

  DEVELOPER_TOKEN=$OMG_DEVELOPER_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/create-stage"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-create-stage_${TENANT}_${PROJECT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerCreateStage] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  STAGE=$(cat ${RESPONSE_FILE} | jq -r .stage_id)
  if [ -z "$STAGE" -o "$STAGE" == "null" ]; then
    echo "$(date) [CTL/developerCreateStage] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: STAGE was not received" >&2
    exit 1
  fi
  echo "export OMG_STAGE=$STAGE" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/developerCreateStage] (${OMG_INSTALLATION_NAME:-unknown}) Stage was created, STAGE=\"${STAGE}\""
}

handler_developer_createStageAlias() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  STAGE_ID=$2
  ALIAS=$3

  if [ -z "${TENANT}" -o -z "${STAGE_ID}" -o -z "${ALIAS}" ]; then
    help "developer createStageAlias"
    exit 1
  fi

  echo "$(date) [CTL/developerCreateStageAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"$TENANT\"" >&2
  echo "$(date) [CTL/developerCreateStageAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, STAGE_ID=\"${STAGE_ID}\"" >&2
  echo "$(date) [CTL/developerCreateStageAlias] (${OMG_INSTALLATION_NAME:-unknown}) Using, ALIAS=\"${ALIAS}\"" >&2

  DEVELOPER_TOKEN=$OMG_DEVELOPER_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/create-stage-alias"
  REQUEST="{\"tenant\": \"${TENANT}\", \"stage_id\": \"${STAGE_ID}\", \"alias\": \"${ALIAS}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-create-stage_${TENANT}_${STAGE_ID}_${ALIAS}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerCreateStageAlias] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  echo "$(date) [CTL/developerCreateStageAlias] (${OMG_INSTALLATION_NAME:-unknown}) Stage alias was created"
}

handler_developer_getStageDetails() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  PROJECT=$2
  STAGE=$3

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" ]; then
    help "developer getStageDetails"
    exit 1
  fi

  echo "$(date) [CTL/developerGetStageDetails] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerGetStageDetails] (${OMG_INSTALLATION_NAME:-unknown}) Using, STAGE=\"${STAGE}\"" >&2

  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/get-stage-details"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-get-stage-details_${TENANT}_${PROJECT}_${STAGE}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerGetStageDetails] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  internal_format_file ${RESPONSE_FILE}
}

handler_developer_deleteStage() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  PROJECT=$2
  STAGE=$3

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" ]; then
    help "developer deleteStage"
    exit 1
  fi

  echo "$(date) [CTL/developerDeleteStage] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerDeleteStage] (${OMG_INSTALLATION_NAME:-unknown}) Using, STAGE=\"${STAGE}\"" >&2

  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/delete-stage"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-delete-stage_${TENANT}_${PROJECT}_${STAGE}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerDeleteStage] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "$(date) [CTL/developerDeleteStage] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMG_DELETED=$DELETED" >> ${OMG_CONTEXT_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) [CTL/developerDeleteStage] (${OMG_INSTALLATION_NAME:-unknown}) Stage was deleted, TENANT=${TENANT}, STAGE=${STAGE}"
  else
    echo "$(date) [CTL/developerDeleteStage] (${OMG_INSTALLATION_NAME:-unknown}) Stage was not deleted, TENANT=${TENANT}, STAGE=${STAGE}"
  fi
}

handler_developer_createVersion() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  PROJECT=$2
  CONFIG_PATH=$3

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${CONFIG_PATH}" ]; then
    help "developer createVersion"
    exit 1
  fi

  echo "$(date) [CTL/developerCreateVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerCreateVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT=\"${PROJECT}\"" >&2
  echo "$(date) [CTL/developerCreateVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, CONFIG_PATH=\"${CONFIG_PATH}\"" >&2

  if [ ! -f ${CONFIG_PATH} ]; then
    echo "$(date) [CTL/developerCreateVersion] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Config file was not found, CONFIG_PATH=\"${CONFIG_PATH}\"" >&2
    exit 1
  fi

  CONFIG=$(cat ${CONFIG_PATH} | jq -c -r)

  if [ -z "${CONFIG}" -o "${CONFIG}" == "null" ]; then
    echo "$(date) [CTL/developerCreateVersion] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Version config is empty" >&2
    exit 1
  fi

  DEVELOPER_TOKEN=$OMG_DEVELOPER_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/create-version"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"config\": ${CONFIG}}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-create-version_${TENANT}_${PROJECT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerCreateVersion] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  VERSION=$(cat ${RESPONSE_FILE} | jq -r .version_id)
  if [ -z "$VERSION" -o "$VERSION" == "null" ]; then
    echo "$(date) [CTL/developerCreateVersion] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: VERSION was not received" >&2
    exit 1
  fi
  echo "export OMG_VERSION=${VERSION}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/developerCreateVersion] (${OMG_INSTALLATION_NAME:-unknown}) Version was created, VERSION=\"${VERSION}\"" >&2
}

handler_developer_uploadFilesArchive() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  VERSION=$2
  FILES_DIRECTORY_PATH=$3

  if [ -z "${TENANT}" -o -z "${VERSION}" -o -z "${FILES_DIRECTORY_PATH}" ]; then
    help "developer uploadFilesArchive"
    exit 1
  fi

  echo "$(date) [CTL/developerUploadFilesArchive] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerUploadFilesArchive] (${OMG_INSTALLATION_NAME:-unknown}) Using, VERSION=\"${VERSION}\"" >&2
  echo "$(date) [CTL/developerUploadFilesArchive] (${OMG_INSTALLATION_NAME:-unknown}) Using, FILES_DIRECTORY_PATH=\"${FILES_DIRECTORY_PATH}\"" >&2

  DEVELOPER_TOKEN=$OMG_DEVELOPER_TOKEN

  ARCHIVE_PATH=$(eval echo ${OMG_CONTEXT_DIRECTORY}/versions/version_${TENANT}_${VERSION}.zip)
  echo "$(date) [CTL/developerUploadFilesArchive] (${OMG_INSTALLATION_NAME:-unknown}) Using, ARCHIVE_PATH=\"${ARCHIVE_PATH}\"" >&2

  pushd ${FILES_DIRECTORY_PATH}

  find . -type f -name "*.lua" | zip -@ ${ARCHIVE_PATH}

  popd >> /dev/null

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/upload-files-archive"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-upload-files-archive_${TENANT}_${VERSION}.json"

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: multipart/form-data" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -F "tenant=${TENANT}" \
    -F "tenantVersionId=${VERSION}" \
    -F "version.zip=@${ARCHIVE_PATH}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerUploadFilesArchive] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  FILES_ARCHIVE=$(cat ${RESPONSE_FILE} | jq -r .files_archive_id)
  if [ -z "${FILES_ARCHIVE}" -o "${FILES_ARCHIVE}" == "null" ]; then
    echo "$(date) [CTL/developerUploadFilesArchive] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: FILES_ARCHIVE was not received" >&2
    exit 1
  fi
  echo "export OMG_FILES_ARCHIVE=${FILES_ARCHIVE}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/developerUploadFilesArchive] (${OMG_INSTALLATION_NAME:-unknown}) Files archive was uploaded, FILES_ARCHIVE=${FILES_ARCHIVE}"
}

handler_developer_getVersionDetails() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  VERSION=$2

  if [ -z "${TENANT}" -o -z "${VERSION}" ]; then
    help "developer getVersionDetails"
    exit 1
  fi

  echo "$(date) [CTL/developerGetVersionDetails] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerGetVersionDetails] (${OMG_INSTALLATION_NAME:-unknown}) Using, VERSION=\"${VERSION}\"" >&2

  DEVELOPER_TOKEN=$OMG_DEVELOPER_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/get-version-details"
  REQUEST="{\"tenant\": \"${TENANT}\", \"version_id\": ${VERSION}}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-get-version-details_${TENANT}_${VERSION}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerGetVersionDetails] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  internal_format_file ${RESPONSE_FILE}
}

handler_developer_deleteVersion() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  VERSION=$2

  if [ -z "${TENANT}" -o -z "${VERSION}" ]; then
    help "developer deleteVersion"
    exit 1
  fi

  echo "$(date) [CTL/developerDeleteVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerDeleteVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, VERSION=\"${VERSION}\"" >&2

  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/delete-version"
  REQUEST="{\"tenant\": \"${TENANT}\", \"id\": \"${VERSION}\" }"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-tenant-version_${TENANT}_${VERSION}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerDeleteVersion] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "$(date) [CTL/developerDeleteVersion] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMG_DELETED=$DELETED" >> ${OMG_CONTEXT_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) [CTL/developerDeleteVersion] (${OMG_INSTALLATION_NAME:-unknown}) Version was deleted, TENANT=${TENANT}, VERSION=${VERSION}"
  else
    echo "$(date) [CTL/developerDeleteVersion] (${OMG_INSTALLATION_NAME:-unknown}) Version was not deleted, TENANT=${TENANT}, VERSION=${VERSION}"
  fi
}

handler_developer_deployVersion() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  PROJECT=$2
  STAGE=$3
  VERSION=$4

  if [ -z "${TENANT}" -o -z "${PROJECT}" -o -z "${STAGE}" -o -z "${VERSION}" ]; then
    help "developer deployVersion"
    exit 1
  fi

  echo "$(date) [CTL/developerDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, PROJECT=\"${PROJECT}\"" >&2
  echo "$(date) [CTL/developerDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, STAGE=\"${STAGE}\"" >&2
  echo "$(date) [CTL/developerDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Using, VERSION=\"${VERSION}\"" >&2

  DEVELOPER_TOKEN=$OMG_DEVELOPER_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/deploy-version"
  REQUEST="{\"tenant\": \"${TENANT}\", \"project\": \"${PROJECT}\", \"stage\": \"${STAGE}\", \"version_id\": ${VERSION}}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-deploy-version_${TENANT}_${PROJECT}_${STAGE}_${VERSION}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [OMGSERVERSCTL] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DEPLOYMENT=$(cat ${RESPONSE_FILE} | jq -r .deployment_id)
  if [ -z "${DEPLOYMENT}" -o "${DEPLOYMENT}" == "null" ]; then
    echo "$(date) [CTL/developerDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DEPLOYMENT was not received" >&2
    exit 1
  fi
  echo "export OMG_DEPLOYMENT=${DEPLOYMENT}" >> ${OMG_CONTEXT_DIRECTORY}/environment

  echo "$(date) [CTL/developerDeployVersion] (${OMG_INSTALLATION_NAME:-unknown}) Deployment was created, DEPLOYMENT=\"${DEPLOYMENT}\"" >&2
}

handler_developer_getDeploymentDetails() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  DEPLOYMENT=$2

  if [ -z "${TENANT}" -o -z "${DEPLOYMENT}" ]; then
    help "developer getDeploymentDetails"
    exit 1
  fi

  echo "$(date) [CTL/developerGetDeploymentDetails] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerGetDeploymentDetails] (${OMG_INSTALLATION_NAME:-unknown}) Using, DEPLOYMENT=${DEPLOYMENT}" >&2

  DEVELOPER_TOKEN=$OMG_DEVELOPER_TOKEN

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/get-deployment-details"
  REQUEST="{\"tenant\": \"${TENANT}\", \"deployment_id\": ${DEPLOYMENT}}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-get-deployment-details_${TENANT}_${DEPLOYMENT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerGetDeploymentDetails] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  internal_format_file ${RESPONSE_FILE}
}

handler_developer_deleteDeployment() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  DEPLOYMENT=$2

  if [ -z "${TENANT}" -o -z "${DEPLOYMENT}" ]; then
    help "developer deleteDeployment"
    exit 1
  fi

  echo "$(date) [CTL/developerDeleteDeployment] (${OMG_INSTALLATION_NAME:-unknown}) Using, TENANT=\"${TENANT}\"" >&2
  echo "$(date) [CTL/developerDeleteDeployment] (${OMG_INSTALLATION_NAME:-unknown}) Using, DEPLOYMENT=${DEPLOYMENT}" >&2

  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/delete-deployment"
  REQUEST="{\"tenant\": \"${TENANT}\", \"id\": \"${DEPLOYMENT}\" }"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-delete-deployment_${TENANT}_${DEPLOYMENT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerDeleteDeployment] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "$(date) [CTL/developerDeleteDeployment] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMG_DELETED=$DELETED" >> ${OMG_CONTEXT_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) [CTL/developerDeleteDeployment] (${OMG_INSTALLATION_NAME:-unknown}) Deployment was deleted, TENANT=${TENANT}, DEPLOYMENT=${DEPLOYMENT}"
  else
    echo "$(date) [CTL/developerDeleteDeployment] (${OMG_INSTALLATION_NAME:-unknown}) Deployment was not deleted, TENANT=${TENANT}, DEPLOYMENT=${DEPLOYMENT}"
  fi
}

handler_developer_createLobbyRequest() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  DEPLOYMENT=$2

  if [ -z "${TENANT}" -o -z "${DEPLOYMENT}" ]; then
    help "developer createLobbyRequest"
    exit 1
  fi

  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/create-lobby-request"
  REQUEST="{\"tenant\": \"${TENANT}\", \"deployment_id\": \"${DEPLOYMENT}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/create-lobby-request_${TENANT}_${DEPLOYMENT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerCreateLobbyRequest] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  echo "Lobby request created successfully."
}

handler_developer_deleteLobby() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  LOBBY_ID=$1

  if [ -z "${LOBBY_ID}" ]; then
    help "developer deleteLobby"
    exit 1
  fi

  echo "$(date) [CTL/developerDeleteLobby] (${OMG_INSTALLATION_NAME:-unknown}) Using, LOBBY_ID=\"${LOBBY_ID}\"" >&2

  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/delete-lobby"
  REQUEST="{\"lobby_id\": \"${LOBBY_ID}\" }"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-delete-lobby_${LOBBY_ID}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerDeleteLobby] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "$(date) [CTL/developerDeleteLobby] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMG_DELETED=$DELETED" >> ${OMG_CONTEXT_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) [CTL/developerDeleteLobby] (${OMG_INSTALLATION_NAME:-unknown}) Lobby was deleted, LOBBY_ID=${LOBBY_ID}"
  else
    echo "$(date) [CTL/developerDeleteLobby] (${OMG_INSTALLATION_NAME:-unknown}) Lobby was not deleted, LOBBY_ID=${LOBBY_ID}"
  fi
}

handler_developer_createMatchmakerRequest() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  TENANT=$1
  DEPLOYMENT=$2

  if [ -z "${TENANT}" -o -z "${DEPLOYMENT}" ]; then
    help "developer createMatchmakerRequest"
    exit 1
  fi

  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/create-matchmaker-request"
  REQUEST="{\"tenant\": \"${TENANT}\", \"deployment_id\": \"${DEPLOYMENT}\"}"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/create-matchmaker-request_${TENANT}_${DEPLOYMENT}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerCreateMatchmakerRequest] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  echo "Matchmaker request created successfully."
}

handler_developer_deleteMatchmaker() {
  internal_ensureEnvironment
  internal_ensureInstallationUrl
  internal_ensureDeveloperToken

  MATCHMAKER_ID=$1

  if [ -z "${MATCHMAKER_ID}" ]; then
    help "developer deleteMatchmaker"
    exit 1
  fi

  echo "$(date) [CTL/developerDeleteMatchmaker] (${OMG_INSTALLATION_NAME:-unknown}) Using, MATCHMAKER_ID=\"${MATCHMAKER_ID}\"" >&2

  DEVELOPER_TOKEN=${OMG_DEVELOPER_TOKEN}

  ENDPOINT="${OMG_INSTALLATION_URL}/service/v1/entrypoint/developer/request/delete-matchmaker"
  REQUEST="{\"matchmaker_id\": \"${MATCHMAKER_ID}\" }"
  RESPONSE_FILE="${OMG_CONTEXT_DIRECTORY}/temp/developer-delete-matchmaker_${MATCHMAKER_ID}.json"

  echo >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $ENDPOINT >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo $REQUEST >> ${OMG_CONTEXT_DIRECTORY}/logs

  HTTP_CODE=$(curl -s -S -X POST -w "%{http_code}" \
    "${ENDPOINT}" \
    -H "Content-type: application/json" \
    -H "Authorization: Bearer ${DEVELOPER_TOKEN}" \
    -d "${REQUEST}" \
    -o ${RESPONSE_FILE})

  cat ${RESPONSE_FILE} >> ${OMG_CONTEXT_DIRECTORY}/logs
  echo >> ${OMG_CONTEXT_DIRECTORY}/logs

  if [ "${HTTP_CODE}" -ge 400 ]; then
    echo "$(date) [CTL/developerDeleteMatchmaker] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: Operation was failed, HTTP_CODE=\"${HTTP_CODE}\", ${ENDPOINT}" >&2
    tail -2 ${OMG_CONTEXT_DIRECTORY}/logs >&2
    exit 1
  fi

  DELETED=$(cat ${RESPONSE_FILE} | jq -r .deleted)
  if [ -z "$DELETED" -o "$DELETED" == "null" ]; then
    echo "$(date) [CTL/developerDeleteMatchmaker] (${OMG_INSTALLATION_NAME:-unknown}) ERROR: DELETED was not received" >&2
    exit 1
  fi
  echo "export OMG_DELETED=$DELETED" >> ${OMG_CONTEXT_DIRECTORY}/environment

  if [ "${DELETED}" == "true" ]; then
    echo "$(date) [CTL/developerDeleteMatchmaker] (${OMG_INSTALLATION_NAME:-unknown}) Matchmaker was deleted, MATCHMAKER_ID=${MATCHMAKER_ID}"
  else
    echo "$(date) [CTL/developerDeleteMatchmaker] (${OMG_INSTALLATION_NAME:-unknown}) Matchmaker was not deleted, MATCHMAKER_ID=${MATCHMAKER_ID}"
  fi
}

# MAIN

ARG=$1
if [ -z "${ARG}" ]; then
  help
  exit 0
else
  shift
  if [ "${ARG}" = "help" ]; then
    help "$*"
  elif [ "${ARG}" = "logs" ]; then
    logs $@
  elif [ "${ARG}" = "environment" ]; then
    ARG=$1
    if [ -z "${ARG}" ]; then
      help "environment"
      exit 1
    else
      shift
      if [ "${ARG}" = "reset" ]; then
        handler_environment_reset $@
      elif [ "${ARG}" = "printCurrent" ]; then
        handler_environment_printCurrent $@
      elif [ "${ARG}" = "printVariable" ]; then
        handler_environment_printVariable $@
      else
        help "environment"
      fi
    fi
  elif [ "${ARG}" = "localtesting" ]; then
    ARG=$1
    if [ -z "${ARG}" ]; then
      help "localtesting"
      exit 1
    else
      shift
      if [ "${ARG}" = "runServer" ]; then
        handler_localtesting_runServer $@
      elif [ "${ARG}" = "stopServer" ]; then
        handler_localtesting_stopServer $@
      elif [ "${ARG}" = "resetServer" ]; then
        handler_localtesting_resetServer $@
      elif [ "${ARG}" = "initProject" ]; then
        handler_localtesting_initProject $@
      elif [ "${ARG}" = "deployProject" ]; then
        handler_localtesting_deployProject $@
      elif [ "${ARG}" = "cleanupProject" ]; then
        handler_localtesting_cleanupProject $@
      elif [ "${ARG}" = "viewContainers" ]; then
        handler_localtesting_viewContainers $@
      elif [ "${ARG}" = "viewDockerStats" ]; then
        handler_localtesting_viewDockerStats $@
      elif [ "${ARG}" = "viewDockerLogs" ]; then
        handler_localtesting_viewDockerLogs $@
      elif [ "${ARG}" = "viewLatestLogs" ]; then
        handler_localtesting_viewLatestLogs $@
      else
        help "localtesting"
      fi
    fi
  elif [ "${ARG}" = "installation" ]; then
    ARG=$1
    if [ -z "${ARG}" ]; then
      help "installation"
      exit 1
    else
      shift
      if [ "${ARG}" = "useCustomServer" ]; then
        handler_installation_useCustomServer $@
      elif [ "${ARG}" = "useDemoServer" ]; then
        handler_installation_useDemoServer $@
      elif [ "${ARG}" = "useLocalServer" ]; then
        handler_installation_useLocalServer $@
      elif [ "${ARG}" = "createTenant" ]; then
        handler_installation_createTenant $@
      elif [ "${ARG}" = "createProject" ]; then
        handler_installation_createProject $@
      elif [ "${ARG}" = "deployVersion" ]; then
        handler_installation_deployVersion $@
      else
        help "installation"
      fi
    fi
  elif [ "${ARG}" = "admin" ]; then
    ARG=$1
    if [ -z "${ARG}" ]; then
      help "admin"
      exit 1
    else
      shift
      if [ "${ARG}" = "printCurrent" ]; then
        handler_admin_printCurrent $@
      elif [ "${ARG}" = "createToken" ]; then
        handler_admin_createToken $@
      elif [ "${ARG}" = "calculateShard" ]; then
        handler_admin_calculateShard $@
      elif [ "${ARG}" = "generateId" ]; then
        handler_admin_generateId $@
      elif [ "${ARG}" = "bcryptHash" ]; then
        handler_admin_bcryptHash $@
      elif [ "${ARG}" = "pingDockerHost" ]; then
        handler_admin_pingDockerHost $@
      else
        help "admin"
      fi
    fi
  elif [ "${ARG}" = "support" ]; then
    ARG=$1
    if [ -z "${ARG}" ]; then
      help "support"
      exit 1
    else
      shift
      if [ "${ARG}" = "printCurrent" ]; then
        handler_support_printCurrent $@
      elif [ "${ARG}" = "createToken" ]; then
        handler_support_createToken $@
      elif [ "${ARG}" = "createTenant" ]; then
        handler_support_createTenant $@
      elif [ "${ARG}" = "createTenantAlias" ]; then
        handler_support_createTenantAlias $@
      elif [ "${ARG}" = "deleteTenant" ]; then
        handler_support_deleteTenant $@
      # Project
      elif [ "${ARG}" = "createProject" ]; then
        handler_support_createProject $@
      elif [ "${ARG}" = "createProjectAlias" ]; then
        handler_support_createProjectAlias $@
      elif [ "${ARG}" = "deleteProject" ]; then
        handler_support_deleteProject $@
      # Stage
      elif [ "${ARG}" = "createStage" ]; then
        handler_support_createStage $@
      elif [ "${ARG}" = "createStageAlias" ]; then
        handler_support_createStageAlias $@
      elif [ "${ARG}" = "deleteStage" ]; then
        handler_support_deleteStage $@
      # Developer
      elif [ "${ARG}" = "createDeveloper" ]; then
        handler_support_createDeveloper $@
      # Permissions
      elif [ "${ARG}" = "createTenantPermission" ]; then
        handler_support_createTenantPermission $@
      elif [ "${ARG}" = "deleteTenantPermission" ]; then
        handler_support_deleteTenantPermission $@
      elif [ "${ARG}" = "createProjectPermission" ]; then
        handler_support_createProjectPermission $@
      elif [ "${ARG}" = "deleteProjectPermission" ]; then
        handler_support_deleteProjectPermission $@
      elif [ "${ARG}" = "createStagePermission" ]; then
        handler_support_createStagePermission $@
      elif [ "${ARG}" = "deleteStagePermission" ]; then
        handler_handler_support_deleteStagePermission $@
      else
        help "support"
      fi
    fi
  elif [ "${ARG}" = "developer" ]; then
    ARG=$1
    if [ -z "${ARG}" ]; then
      help "developer"
      exit 1
    else
      shift
      if [ "${ARG}" = "printCurrent" ]; then
        handler_developer_printCurrent $@
      elif [ "${ARG}" = "createToken" ]; then
        handler_developer_createToken $@
      elif [ "${ARG}" = "getTenantDetails" ]; then
        handler_developer_getTenantDetails $@
      elif [ "${ARG}" = "createProject" ]; then
        handler_developer_createProject $@
      elif [ "${ARG}" = "createProjectAlias" ]; then
        handler_developer_createProjectAlias $@
      elif [ "${ARG}" = "getProjectDetails" ]; then
        handler_developer_getProjectDetails $@
      elif [ "${ARG}" = "deleteProject" ]; then
        handler_developer_deleteProject $@
      elif [ "${ARG}" = "createStage" ]; then
        handler_developer_createStage $@
      elif [ "${ARG}" = "createStageAlias" ]; then
        handler_developer_createStageAlias $@
      elif [ "${ARG}" = "getStageDetails" ]; then
        handler_developer_getStageDetails $@
      elif [ "${ARG}" = "deleteStage" ]; then
        handler_developer_deleteStage $@
      elif [ "${ARG}" = "createVersion" ]; then
        handler_developer_createVersion $@
      elif [ "${ARG}" = "uploadFilesArchive" ]; then
        handler_developer_uploadFilesArchive $@
      elif [ "${ARG}" = "getVersionDetails" ]; then
        handler_developer_getVersionDetails $@
      elif [ "${ARG}" = "deleteVersion" ]; then
        handler_developer_deleteVersion $@
      elif [ "${ARG}" = "deployVersion" ]; then
        handler_developer_deployVersion $@
      elif [ "${ARG}" = "getDeploymentDetails" ]; then
        handler_developer_getDeploymentDetails $@
      elif [ "${ARG}" = "deleteDeployment" ]; then
        handler_developer_deleteDeployment $@
      elif [ "${ARG}" = "createLobbyRequest" ]; then
        handler_developer_createLobbyRequest $@
      elif [ "${ARG}" = "deleteLobby" ]; then
        handler_developer_deleteLobby $@
      elif [ "${ARG}" = "createMatchmakerRequest" ]; then
        handler_developer_createMatchmakerRequest $@
      elif [ "${ARG}" = "deleteMatchmaker" ]; then
        handler_developer_deleteMatchmaker $@
      else
        help "developer"
      fi
    fi
  else
    help
  fi
fi
