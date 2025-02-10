#!/bin/sh
set -e
set -o pipefail
export TZ=UTC

if [ -z "${WORKING_DIR}" ]; then
  echo "ERROR: WORKING_DIR is not set" >&2
  exit 1
fi

DIND_CONTAINER_NAME=${DIND_CONTAINER_NAME:-"omgservers"}

OMGTOOLCTL_DIRECTORY=${OMGTOOLCTL_DIRECTORY:-.omgtoolctl}
if [ ! -d "${OMGTOOLCTL_DIRECTORY}" ]; then
  mkdir -p ${OMGTOOLCTL_DIRECTORY}
fi

if [ ! -f "${OMGTOOLCTL_DIRECTORY}/environment" ]; then
  touch ${OMGTOOLCTL_DIRECTORY}/environment
fi
source ${OMGTOOLCTL_DIRECTORY}/environment

if [ ! -f "${OMGTOOLCTL_DIRECTORY}/logs" ]; then
  touch ${OMGTOOLCTL_DIRECTORY}/logs
fi

# INTERNAL

internal_print_command() {
  printf "  %-50s %s\n" "$1" "$2"
}

internal_ctl() {
  docker run --rm -it \
    --network=host \
    -v ${WORKING_DIR}/.omgserversctl:/opt/omgserversctl/.omgserversctl \
    -v ${WORKING_DIR}/config.json:/opt/omgserversctl/config.json:ro \
    -v /etc/resolv.conf:/etc/resolv.conf:ro \
    omgservers/ctl:${OMGSERVERS_VERSION} $@
}

# HANDLERS

handler_help() {
  echo "OMGTOOL ctl, v${OMGSERVERS_VERSION}"
  echo "Usage: $0 <category> <command> [options]"
  if [ -z "$1" -o "$1" = "help" ]; then
    internal_print_command " help [command]" "Display this/command help message."
  fi
  # Dind
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
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting init" ]; then
    internal_print_command " localtesting init" "Initialize a tenant and developer account."
  fi
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting cleanup" ]; then
    internal_print_command " localtesting cleanup" "Clean up the tenant from the local environment."
  fi
  # Game
  if [ -z "$1" -o "$1" = "localtesting" -o "$1" = "localtesting install" ]; then
    internal_print_command " localtesting install" "Install a Docker image locally."
  fi
  if [ -z "$1" -o "$1" = "installation" -o "$1" = "installation deploy" ]; then
    internal_print_command " installation deploy <url> <user> <password>" "Deploy a Docker image to the service installation."
  fi
}

handler_localtesting_up() {
  docker run --privileged --rm -it --name "${DIND_CONTAINER_NAME}" --tmpfs /tmp -p 8080:8080 -d "omgservers/dind:${OMGSERVERS_VERSION}"
}

handler_localtesting_ps() {
  docker exec -it "${DIND_CONTAINER_NAME}" docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t{{.Command}}"
}

handler_localtesting_stats() {
  docker exec -it "${DIND_CONTAINER_NAME}" docker stats
}

handler_localtesting_logs() {
  if [ -z "$1" ] || [ "$1" = "-f" ]; then
    docker logs "${DIND_CONTAINER_NAME}" $@
  else
    docker exec -it "${DIND_CONTAINER_NAME}" docker logs $@
  fi
}

handler_localtesting_down() {
  docker kill "${DIND_CONTAINER_NAME}"
}

handler_localtesting_reset() {
  handler_localtesting_down || true
  handler_localtesting_up
}

handler_localtesting_init() {
  if [ -z "${TENANT_ALIAS}" ]; then
    echo "ERROR: TENANT_ALIAS was not set" >&2
    exit 1
  fi

  if [ -z "${PROJECT_ALIAS}" ]; then
    echo "ERROR: PROJECT_ALIAS was not set" >&2
    exit 1
  fi

  if [ -z "${STAGE_ALIAS}" ]; then
    echo "ERROR: STAGE_ALIAS was not set" >&2
    exit 1
  fi

  echo "$(date) Using, TENANT_ALIAS=\"${TENANT_ALIAS}\""
  echo "$(date) Using, PROJECT_ALIAS=\"${PROJECT_ALIAS}\""
  echo "$(date) Using, STAGE_ALIAS=\"${STAGE_ALIAS}\""

  internal_ctl environment useEnvironment local "http://localhost:8080"

  echo "$(date) Create a new tenant"

  internal_ctl support createToken "support" "support"
  internal_ctl support createTenant

  TENANT=$(internal_ctl environment printVariable TENANT)
  if [ -z "${TENANT}" ]; then
    echo "ERROR: TENANT was not found" >&2
    exit 1
  fi

  internal_ctl support createTenantAlias ${TENANT} ${TENANT_ALIAS}

  echo "$(date) Create a new project"

  internal_ctl support createProject ${TENANT_ALIAS}

  PROJECT=$(internal_ctl environment printVariable PROJECT)
  if [ -z "${PROJECT}" ]; then
    echo "ERROR: PROJECT was not found" >&2
    exit 1
  fi

  STAGE=$(internal_ctl environment printVariable STAGE)
  if [ -z "${STAGE}" ]; then
    echo "ERROR: STAGE was not found" >&2
    exit 1
  fi

  internal_ctl support createProjectAlias ${TENANT_ALIAS} ${PROJECT} ${PROJECT_ALIAS}
  internal_ctl support createStageAlias ${TENANT_ALIAS} ${STAGE} ${STAGE_ALIAS}

  echo "$(date) Create a new developer account"

  internal_ctl support createDeveloper

  DEVELOPER_USER=$(internal_ctl environment printVariable DEVELOPER_USER)
  if [ -z "${DEVELOPER_USER}" ]; then
    echo "ERROR: DEVELOPER_USER was not found" >&2
    exit 1
  fi

  DEVELOPER_PASSWORD=$(internal_ctl environment printVariable DEVELOPER_PASSWORD)
  if [ -z "${DEVELOPER_PASSWORD}" ]; then
    echo "ERROR: DEVELOPER_PASSWORD was not found" >&2
    exit 1
  fi

  echo "$(date) Configure developer permissions"

  internal_ctl support createTenantPermission ${TENANT_ALIAS} ${DEVELOPER_USER} TENANT_VIEWER
  internal_ctl support createProjectPermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${DEVELOPER_USER} VERSION_MANAGER
  internal_ctl support createProjectPermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${DEVELOPER_USER} PROJECT_VIEWER
  internal_ctl support createStagePermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE} ${DEVELOPER_USER} DEPLOYMENT_MANAGER
  internal_ctl support createStagePermission ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE} ${DEVELOPER_USER} STAGE_VIEWER

  echo "$(date) Login using developer account, DEVELOPER_USER=\"${DEVELOPER_USER}\", DEVELOPER_PASSWORD=\"${DEVELOPER_PASSWORD}\""

  internal_ctl developer createToken ${DEVELOPER_USER} ${DEVELOPER_PASSWORD}

  echo "export OMGTOOLCTL_LOCALTESTING_DEVELOPER_USER=${DEVELOPER_USER}" >> ${OMGTOOLCTL_DIRECTORY}/environment
  echo "export OMGTOOLCTL_LOCALTESTING_DEVELOPER_PASSWORD=${DEVELOPER_PASSWORD}" >> ${OMGTOOLCTL_DIRECTORY}/environment

  echo "$(date) All is done"
}

handler_localtesting_cleanup() {
  if [ -z "${TENANT_ALIAS}" ]; then
    echo "ERROR: TENANT_ALIAS was not set" >&2
    exit 1
  fi

  echo "$(date) Using, TENANT_ALIAS=\"${TENANT_ALIAS}\""

  internal_ctl environment useEnvironment local "http://localhost:8080"
  internal_ctl support deleteTenant ${TENANT_ALIAS}
}

handler_installation_deploy() {
  SERVICE_URL=$1
  DEVELOPER_USER=$2
  DEVELOPER_PASSWORD=$3

  if [ -z "${SERVICE_URL}" -o -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    handler_help "deploy"
    exit 1
  fi

  echo "$(date) Using, DOCKER_IMAGE=\"${DOCKER_IMAGE}\""
  echo "$(date) Using, SERVICE_URL=\"${SERVICE_URL}\""
  echo "$(date) Using, DEVELOPER_USER=\"${DEVELOPER_USER}\""
  echo "$(date) Using, TENANT_ALIAS=\"${TENANT_ALIAS}\""
  echo "$(date) Using, PROJECT_ALIAS=\"${PROJECT_ALIAS}\""
  echo "$(date) Using, STAGE_ALIAS=\"${STAGE_ALIAS}\""

  echo "$(date) Login using developer account"

  internal_ctl environment useEnvironment target "${SERVICE_URL}"
  internal_ctl developer createToken "${DEVELOPER_USER}" "${DEVELOPER_PASSWORD}"

  echo "$(date) Create a new version"

  internal_ctl developer createVersion ${TENANT_ALIAS} ${PROJECT_ALIAS} "./config.json"
  VERSION=$(internal_ctl environment printVariable VERSION)
  if [ -z "${VERSION}" -o "${VERSION}" == "null" ]; then
    echo "ERROR: VERSION was not found" >&2
    exit 1
  fi

  echo "$(date) Push docker image"

  REGISTRY_SERVER=$(echo ${SERVICE_URL} | sed 's/^https*:\/\///')
  echo "$(date) Using, REGISTRY_SERVER=${REGISTRY_SERVER}"

  TARGET_IMAGE="${REGISTRY_SERVER}/omgservers/${TENANT_ALIAS}/${PROJECT_ALIAS}/universal:${VERSION}"
  echo "Using, TARGET_IMAGE=${TARGET_IMAGE}"
  docker login -u ${DEVELOPER_USER} -p ${DEVELOPER_PASSWORD} "${REGISTRY_SERVER}"
  docker tag ${DOCKER_IMAGE} ${TARGET_IMAGE}
  docker push ${TARGET_IMAGE}

  echo "$(date) Deploy a new version"

  internal_ctl developer deployVersion ${TENANT_ALIAS} ${PROJECT_ALIAS} ${STAGE_ALIAS} ${VERSION}
  DEPLOYMENT=$(internal_ctl environment printVariable DEPLOYMENT)
  if [ -z "${DEPLOYMENT}" -o "${DEPLOYMENT}" == "null" ]; then
    echo "ERROR: DEPLOYMENT was not found" >&2
    exit 1
  fi

  echo "$(date) All is done"
}

handler_localtesting_install() {
  DEVELOPER_USER=${OMGTOOLCTL_LOCALTESTING_DEVELOPER_USER}
  DEVELOPER_PASSWORD=${OMGTOOLCTL_LOCALTESTING_DEVELOPER_PASSWORD}

  if [ -z "${DEVELOPER_USER}" -o -z "${DEVELOPER_PASSWORD}" ]; then
    echo "ERROR: Localtesting developer account was not found" >&2
    exit 1
  fi

  handler_installation_deploy "http://localhost:8080" "${DEVELOPER_USER}" "${DEVELOPER_PASSWORD}"
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
      elif [ "${ARG}" = "init" ]; then
        handler_localtesting_init
      elif [ "${ARG}" = "cleanup" ]; then
        handler_localtesting_cleanup
      elif [ "${ARG}" = "install" ]; then
        handler_localtesting_install
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
      if [ "${ARG}" = "deploy" ]; then
        handler_installation_deploy $@
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
