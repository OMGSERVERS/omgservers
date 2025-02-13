#!/bin/sh
set -e
set -o pipefail
export TZ=UTC

if [ -z "${OMGTOOLCTL_WORKING_DIRECTORY}" ]; then
  echo "$(date) [OMGTOOLCTL] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) ERROR: OMGTOOLCTL_WORKING_DIRECTORY is not set" >&2
  exit 1
fi

OMGTOOLCTL_CONTEXT_DIRECTORY=${OMGTOOLCTL_CONTEXT_DIRECTORY:-.omgtoolctl}
if [ ! -d "${OMGTOOLCTL_CONTEXT_DIRECTORY}" ]; then
  mkdir -p ${OMGTOOLCTL_CONTEXT_DIRECTORY}
fi

if [ ! -f "${OMGTOOLCTL_CONTEXT_DIRECTORY}/environment" ]; then
  touch ${OMGTOOLCTL_CONTEXT_DIRECTORY}/environment
fi
source ${OMGTOOLCTL_CONTEXT_DIRECTORY}/environment

if [ ! -f "${OMGTOOLCTL_CONTEXT_DIRECTORY}/logs" ]; then
  touch ${OMGTOOLCTL_CONTEXT_DIRECTORY}/logs
fi

OMGTOOLCTL_LOCALTESTING_CONTAINER=${OMGTOOLCTL_LOCALTESTING_CONTAINER:-"omgservers"}

# INTERNAL

internal_print_command() {
  printf "  %-85s %s\n" "$1" "$2"
}

internal_ctl() {
  docker run --rm \
    --network=host \
    -v ${OMGTOOLCTL_WORKING_DIRECTORY}/.omgserversctl/${OMGTOOLCTL_INSTALLATION_NAME}:/opt/omgserversctl/.omgserversctl \
    -v ${OMGTOOLCTL_WORKING_DIRECTORY}/config.json:/opt/omgserversctl/config.json:ro \
    -v /etc/resolv.conf:/etc/resolv.conf:ro \
    omgservers/ctl:${OMGSERVERS_VERSION} $@
}

# HANDLERS

handler_help() {
  echo "OMGTOOL ctl, v${OMGSERVERS_VERSION}"
  echo "Usage: $0"
  if [ -z "$1" -o "$1" = "help" ]; then
    internal_print_command " help [command]" "Display this/command help message."
  fi
  # Localtesting
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting up" ]; then
    internal_print_command " localtesting up" "Start the local environment."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting ps" ]; then
    internal_print_command " localtesting ps" "List running containers."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting stats" ]; then
    internal_print_command " localtesting stats" "Stat of containers."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting logs" ]; then
    internal_print_command " localtesting logs [options]" "Show containers logs."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting down" ]; then
    internal_print_command " localtesting down" "Stop the local environment."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting reset" ]; then
    internal_print_command " localtesting reset" "Reset the local environment."
  fi
  # Installation
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation printCurrent" ]; then
    internal_print_command " installation printCurrent" "Print current installation details."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation useCustomUrl" ]; then
    internal_print_command " installation useCustomUrl <name> <url>" "Use the custom installation URL."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation useLocalServer" ]; then
    internal_print_command " installation useLocalServer" "Use the local server installation."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation useDemoServer" ]; then
    internal_print_command " installation useDemoServer" "Use the demo server installation."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation ctl" ]; then
    internal_print_command " installation ctl <command>" "Run the installation command through ctl."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation admin" -o "$1" = "installation admin createToken" ]; then
    internal_print_command " installation admin createToken <user> <password>" "Create an admin user token."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation support" -o "$1" = "installation support createToken" ]; then
    internal_print_command " installation support createToken <user> <password>" "Create a support user token."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation support" -o "$1" = "installation support createTenant" ]; then
    internal_print_command " installation support createTenant <tenant>" "Create a new tenant."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation support" -o "$1" = "installation support createProject" ]; then
    internal_print_command " installation support createProject <tenant> <project> <stage>" "Create a new project in the tenant."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation developer" -o "$1" = "installation developer createToken" ]; then
    internal_print_command " installation developer createToken <user> <password>" "Create a developer user token."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation developer" -o "$1" = "installation developer deployVersion" ]; then
    internal_print_command " installation developer deployVersion <tenant> <project> <stage> <user> <password>" "Deploy a new version."
  fi
}

handler_localtesting_up() {
  docker run --privileged --rm --name "${OMGTOOLCTL_LOCALTESTING_CONTAINER}" --tmpfs /tmp -p 8080:8080 -d "omgservers/dind:${OMGSERVERS_VERSION}"
}

handler_localtesting_ps() {
  docker exec "${OMGTOOLCTL_LOCALTESTING_CONTAINER}" docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t{{.Command}}" $@
}

handler_localtesting_stats() {
  docker exec "${OMGTOOLCTL_LOCALTESTING_CONTAINER}" docker stats
}

handler_localtesting_logs() {
  if [ -z "$1" ] || [ "$1" = "-f" ]; then
    docker logs "${OMGTOOLCTL_LOCALTESTING_CONTAINER}" $@
  else
    docker exec "${OMGTOOLCTL_LOCALTESTING_CONTAINER}" docker logs $@
  fi
}

handler_localtesting_down() {
  docker kill "${OMGTOOLCTL_LOCALTESTING_CONTAINER}"
}

handler_localtesting_reset() {
  handler_localtesting_down || true
  handler_localtesting_up
}

# Installation

handler_installation_printCurrent() {
  if [ -z "${OMGTOOLCTL_INSTALLATION_NAME}" -o -z "${OMGTOOLCTL_INSTALLATION_URL}" ]; then
    echo "$(date) [OMGTOOLCTL/installationPrintCurrent] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) ERROR: Installation was not set yet" >&2
    exit 1
  fi

  echo "$(date) [OMGTOOLCTL/installationPrintCurrent] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Installation was found, INSTALLATION_NAME=\"${OMGTOOLCTL_INSTALLATION_NAME}\", INSTALLATION_URL=\"${OMGTOOLCTL_INSTALLATION_URL}\""
}

handler_installation_useCustomUrl() {
  INSTALLATION_NAME=$1
  INSTALLATION_URL=$2

  if [ -z "${INSTALLATION_NAME}" -o -z "${INSTALLATION_URL}" ]; then
    handler_help "installation useCustomUrl"
    exit 1
  fi

  echo "export OMGTOOLCTL_INSTALLATION_NAME=${INSTALLATION_NAME}" >> ${OMGTOOLCTL_CONTEXT_DIRECTORY}/environment
  echo "export OMGTOOLCTL_INSTALLATION_URL=${INSTALLATION_URL}" >> ${OMGTOOLCTL_CONTEXT_DIRECTORY}/environment
  source ${OMGTOOLCTL_CONTEXT_DIRECTORY}/environment

  internal_ctl installation useCustomUrl ${INSTALLATION_NAME} "${INSTALLATION_URL}"
}

handler_installation_useLocalServer() {
  handler_installation_useCustomUrl localserver "http://localhost:8080"
}

handler_installation_useDemoServer() {
  handler_installation_useCustomUrl demoserver "https://demoserver.omgservers.dev"
}

handler_installation_ctl() {
  internal_ctl $@
}

handler_installation_admin_createToken() {
  ADMIN_USER=$1
  ADMIN_PASSWORD=$2

  if [ -z "${ADMIN_USER}" -o -z "${ADMIN_PASSWORD}" ]; then
    handler_help "installation admin createToken"
    exit 1
  fi

  echo "$(date) [OMGTOOLCTL/installationAdminCreateToken] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, ADMIN_USER=\"${ADMIN_USER}\"" >&2

  internal_ctl admin createToken "${ADMIN_USER}" "${ADMIN_PASSWORD}"
}

handler_installation_support_createToken() {
  SUPPORT_USER=$1
  SUPPORT_PASSWORD=$2

  if [ -z "${SUPPORT_USER}" -o -z "${SUPPORT_PASSWORD}" ]; then
    handler_help "installation support createToken"
    exit 1
  fi

  echo "$(date) [OMGTOOLCTL/installationSupportCreateToken] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, SUPPORT_USER=\"${SUPPORT_USER}\"" >&2

  internal_ctl support createToken "${SUPPORT_USER}" "${SUPPORT_PASSWORD}"
}

handler_installation_support_createTenant() {
  TENANT_ALIAS=$1

  if [ -z "${TENANT_ALIAS}" ]; then
    handler_help "installation support createTenant"
    exit 1
  fi

  echo "$(date) [OMGTOOLCTL/installationSupportCreateTenant] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, TENANT_ALIAS=\"${TENANT_ALIAS}\"" >&2

  internal_ctl support createTenant

  TENANT=$(internal_ctl environment printVariable TENANT)
  if [ -z "${TENANT}" ]; then
    echo "$(date) [OMGTOOLCTL/installationSupportCreateTenant] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) ERROR: TENANT was not found" >&2
    exit 1
  fi

  internal_ctl support createTenantAlias ${TENANT} ${TENANT_ALIAS}
}

handler_installation_support_createProject() {
  TENANT_ALIAS=$1
  PROJECT_ALIAS=$2
  STAGE_ALIAS=$3

  if [ -z "${TENANT_ALIAS}" -o -z "${PROJECT_ALIAS}" -o -z "${STAGE_ALIAS}" ]; then
    handler_help "installation support createProject"
    exit 1
  fi

  echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, TENANT_ALIAS=\"${TENANT_ALIAS}\"" >&2
  echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, PROJECT_ALIAS=\"${PROJECT_ALIAS}\"" >&2
  echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, STAGE_ALIAS=\"${STAGE_ALIAS}\"" >&2

  internal_ctl support createProject ${TENANT_ALIAS}

  PROJECT=$(internal_ctl environment printVariable PROJECT)
  if [ -z "${PROJECT}" ]; then
    echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) ERROR: PROJECT was not found" >&2
    exit 1
  fi

  STAGE=$(internal_ctl environment printVariable STAGE)
  if [ -z "${STAGE}" ]; then
    echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) ERROR: STAGE was not found" >&2
    exit 1
  fi

  internal_ctl support createProjectAlias ${TENANT_ALIAS} ${PROJECT} ${PROJECT_ALIAS}
  internal_ctl support createStageAlias ${TENANT_ALIAS} ${STAGE} ${STAGE_ALIAS}

  echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Create a new developer account" >&2

  internal_ctl support createDeveloper

  DEVELOPER_USER=$(internal_ctl environment printVariable DEVELOPER_USER)
  if [ -z "${DEVELOPER_USER}" ]; then
    echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) ERROR: DEVELOPER_USER was not found" >&2
    exit 1
  fi

  DEVELOPER_PASSWORD=$(internal_ctl environment printVariable DEVELOPER_PASSWORD)
  if [ -z "${DEVELOPER_PASSWORD}" ]; then
    echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) ERROR: DEVELOPER_PASSWORD was not found" >&2
    exit 1
  fi

  echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Configure developer permissions" >&2

  internal_ctl support createTenantPermission ${TENANT_ALIAS} ${DEVELOPER_USER} TENANT_VIEWER
  internal_ctl support createProjectPermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${DEVELOPER_USER} VERSION_MANAGER
  internal_ctl support createProjectPermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${DEVELOPER_USER} PROJECT_VIEWER
  internal_ctl support createStagePermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE} ${DEVELOPER_USER} DEPLOYMENT_MANAGER
  internal_ctl support createStagePermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE} ${DEVELOPER_USER} STAGE_VIEWER

  echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Login using developer account, DEVELOPER_USER=\"${DEVELOPER_USER}\", DEVELOPER_PASSWORD=\"${DEVELOPER_PASSWORD}\"" >&2

  internal_ctl developer createToken ${DEVELOPER_USER} ${DEVELOPER_PASSWORD}

  echo "$(date) [OMGTOOLCTL/installationSupportCreateProject] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) All is done" >&2
}

handler_installation_developer_createToken() {
  DEVELOPER_USER=$1
  DEVELOPER_PASSWORD=$2

  if [ -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    handler_help "installation developer createToken"
    exit 1
  fi

  echo "$(date) [OMGTOOLCTL/installationDeveloperCreateToken] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, DEVELOPER_USER=\"${DEVELOPER_USER}\"" >&2

  internal_ctl developer createToken "${DEVELOPER_USER}" "${DEVELOPER_PASSWORD}"
}

handler_installation_developer_deployVersion() {
  TENANT_ALIAS=$1
  PROJECT_ALIAS=$2
  STAGE_ALIAS=$3
  DEVELOPER_USER=$4
  DEVELOPER_PASSWORD=$5

  if [ -z "${TENANT_ALIAS}" -o -z "${PROJECT_ALIAS}" -o -z "${STAGE_ALIAS}" -o -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    handler_help "installation developer deployVersion"
    exit 1
  fi

  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, TENANT_ALIAS=\"${TENANT_ALIAS}\"" >&2
  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, PROJECT_ALIAS=\"${PROJECT_ALIAS}\"" >&2
  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, STAGE_ALIAS=\"${STAGE_ALIAS}\"" >&2
  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, DEVELOPER_USER=\"${DEVELOPER_USER}\"" >&2

  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Create a new version" >&2

  internal_ctl developer createToken "${DEVELOPER_USER}" "${DEVELOPER_PASSWORD}"
  internal_ctl developer createVersion ${TENANT_ALIAS} ${PROJECT_ALIAS} "./config.json"
  VERSION=$(internal_ctl environment printVariable VERSION)
  if [ -z "${VERSION}" -o "${VERSION}" = "null" ]; then
    echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) ERROR: VERSION was not found" >&2
    exit 1
  fi

  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Push docker image" >&2

  REGISTRY_SERVER=$(echo ${OMGTOOLCTL_INSTALLATION_URL} | sed 's/^https*:\/\///')
  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, REGISTRY_SERVER=\"${REGISTRY_SERVER}\"" >&2

  DOCKER_IMAGE="${TENANT_ALIAS}/${PROJECT_ALIAS}:latest"
  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, DOCKER_IMAGE=\"${DOCKER_IMAGE}\"" >&2
  TARGET_IMAGE="${REGISTRY_SERVER}/omgservers/${TENANT_ALIAS}/${PROJECT_ALIAS}/universal:${VERSION}"
  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Using, TARGET_IMAGE=\"${TARGET_IMAGE}\"" >&2
  echo ${DEVELOPER_PASSWORD} | docker login -u ${DEVELOPER_USER} --password-stdin "${REGISTRY_SERVER}" >&2
  docker tag ${DOCKER_IMAGE} ${TARGET_IMAGE} >&2
  docker push ${TARGET_IMAGE} >&2

  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Waiting for the Docker image to propagate..." >&2

  while [ $(internal_ctl developer getVersionDetails ${TENANT_ALIAS} ${VERSION} | jq '.details.images | length') -eq 0 ]; do
    sleep 1
  done

  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) Deploy a new version" >&2

  internal_ctl developer deployVersion ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE_ALIAS} ${VERSION}
  DEPLOYMENT=$(internal_ctl environment printVariable DEPLOYMENT)
  if [ -z "${DEPLOYMENT}" -o "${DEPLOYMENT}" = "null" ]; then
    echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) ERROR: DEPLOYMENT was not found" >&2
    exit 1
  fi

  echo "$(date) [OMGTOOLCTL/installationDeveloperDeployVersion] (${OMGTOOLCTL_INSTALLATION_NAME:-unknown}) All is done" >&2
}

ARG=$1
if [ -z "${ARG}" ]; then
  handler_help
  exit 0
else
  shift

  if [ "${ARG}" = "help" ]; then
    handler_help "$*"
  elif [ "${ARG}" = "localtesting" ]; then
    ARG=$1
    if [ -z "${ARG}" ]; then
      handler_help "localtesting"
      exit 1
    else
      shift
      if [ "${ARG}" = "up" ]; then
        handler_localtesting_up $@
      elif [ "${ARG}" = "ps" ]; then
        handler_localtesting_ps $@
      elif [ "${ARG}" = "stats" ]; then
        handler_localtesting_stats $@
      elif [ "${ARG}" = "logs" ]; then
        handler_localtesting_logs $@
      elif [ "${ARG}" = "down" ]; then
        handler_localtesting_down $@
      elif [ "${ARG}" = "reset" ]; then
        handler_localtesting_reset $@
      else
        handler_help "localtesting"
        exit 1
      fi
    fi

  elif [ "${ARG}" = "installation" ]; then
    ARG=$1
    if [ -z "${ARG}" ]; then
      handler_help "installation"
      exit 1
    else
      shift
      if [ "${ARG}" = "printCurrent" ]; then
        handler_installation_printCurrent $@
      elif [ "${ARG}" = "useCustomUrl" ]; then
        handler_installation_useCustomUrl $@
      elif [ "${ARG}" = "useLocalServer" ]; then
        handler_installation_useLocalServer $@
      elif [ "${ARG}" = "useDemoServer" ]; then
        handler_installation_useDemoServer $@
      elif [ "${ARG}" = "ctl" ]; then
        handler_installation_ctl $@
      elif [ "${ARG}" = "admin" ]; then
        ARG=$1
        if [ -z "${ARG}" ]; then
          handler_help "installation admin"
          exit 1
        else
          shift
          if [ "${ARG}" = "createToken" ]; then
            handler_installation_admin_createToken $@
          else
            handler_help "installation admin"
            exit 1
          fi
        fi
      elif [ "${ARG}" = "support" ]; then
        ARG=$1
        if [ -z "${ARG}" ]; then
          handler_help "installation support"
          exit 1
        else
          shift
          if [ "${ARG}" = "createToken" ]; then
            handler_installation_support_createToken $@
          elif [ "${ARG}" = "createTenant" ]; then
            handler_installation_support_createTenant $@
          elif [ "${ARG}" = "createProject" ]; then
            handler_installation_support_createProject $@
          else
            handler_help "installation support"
            exit 1
          fi
        fi
      elif [ "${ARG}" = "developer" ]; then
        ARG=$1
        if [ -z "${ARG}" ]; then
          handler_help "installation developer"
          exit 1
        else
          shift
          if [ "${ARG}" = "createToken" ]; then
            handler_installation_developer_createToken $@
          elif [ "${ARG}" = "deployVersion" ]; then
            handler_installation_developer_deployVersion $@
          else
            handler_help "installation developer"
            exit 1
          fi
        fi
      else
        handler_help "installation"
        exit 1
      fi
    fi
  else
    handler_help
    exit 1
  fi
fi
