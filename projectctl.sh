#!/bin/bash
set -e

# INTERNAL

internal_help() {
  echo "PROJECT ctl, v0.1.0"
  echo "Usage:"
  if [ -z "$1" -o "$1" = "help" ]; then
    echo " $0 help"
  fi
  # Build
  if [ -z "$1" -o "$1" = "build" -o "$1" = "build printVersion" ]; then
    echo " $0 build printVersion"
  fi
  if [ -z "$1" -o "$1" = "build" -o "$1" = "build withTests" ]; then
    echo " $0 build withTests"
  fi
  if [ -z "$1" -o "$1" = "build" -o "$1" = "build withoutTests" ]; then
    echo " $0 build withoutTests"
  fi
  if [ -z "$1" -o "$1" = "build" -o "$1" = "build testWithSonar" ]; then
    echo " $0 build testWithSonar"
  fi
  if [ -z "$1" -o "$1" = "build" -o "$1" = "build beforeCommit" ]; then
    echo " $0 build beforeCommit"
  fi
  if [ -z "$1" -o "$1" = "build" -o "$1" = "build pushImages" ]; then
    echo " $0 build pushImages"
  fi
  # Environment
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment printCurrent" ]; then
    echo " $0 environment printCurrent"
  fi
  if [ -z "$1" -o "$1" = "environment" -o "$1" = "environment down" ]; then
    echo " $0 environment down"
  fi
  # Orphan
  if [ -z "$1" -o "$1" = "orphan" -o "$1" = "orphan up" ]; then
    echo " $0 orphan up"
  fi
  if [ -z "$1" -o "$1" = "orphan" -o "$1" = "orphan ps" ]; then
    echo " $0 orphan ps"
  fi
  if [ -z "$1" -o "$1" = "orphan" -o "$1" = "orphan reset" ]; then
    echo " $0 orphan reset"
  fi
  if [ -z "$1" -o "$1" = "orphan" -o "$1" = "orphan test" ]; then
    echo " $0 orphan test"
  fi
  # Singleinstance
  if [ -z "$1" -o "$1" = "singleinstance" -o "$1" = "singleinstance up" ]; then
    echo " $0 singleinstance up"
  fi
  if [ -z "$1" -o "$1" = "singleinstance" -o "$1" = "singleinstance ps" ]; then
    echo " $0 singleinstance ps"
  fi
  if [ -z "$1" -o "$1" = "singleinstance" -o "$1" = "singleinstance reset" ]; then
    echo " $0 singleinstance reset"
  fi
  if [ -z "$1" -o "$1" = "singleinstance" -o "$1" = "singleinstance logs" ]; then
    echo " $0 singleinstance logs"
  fi
  if [ -z "$1" -o "$1" = "singleinstance" -o "$1" = "singleinstance database" ]; then
    echo " $0 singleinstance database"
  fi
  if [ -z "$1" -o "$1" = "singleinstance" -o "$1" = "singleinstance ctl" ]; then
    echo " $0 singleinstance ctl"
  fi
  if [ -z "$1" -o "$1" = "singleinstance" -o "$1" = "singleinstance test" ]; then
    echo " $0 singleinstance test"
  fi
  # Multiinstance
  if [ -z "$1" -o "$1" = "multiinstance" -o "$1" = "multiinstance up" ]; then
    echo " $0 multiinstance up"
  fi
  if [ -z "$1" -o "$1" = "multiinstance" -o "$1" = "multiinstance ps" ]; then
    echo " $0 multiinstance ps"
  fi
  if [ -z "$1" -o "$1" = "multiinstance" -o "$1" = "multiinstance reset" ]; then
    echo " $0 multiinstance reset"
  fi
  if [ -z "$1" -o "$1" = "multiinstance" -o "$1" = "multiinstance test" ]; then
    echo " $0 multiinstance test"
  fi
  # Integration
  if [ -z "$1" -o "$1" = "integration" -o "$1" = "integration up" ]; then
    echo " $0 integration up"
  fi
  if [ -z "$1" -o "$1" = "integration" -o "$1" = "integration ps" ]; then
    echo " $0 integration ps"
  fi
  if [ -z "$1" -o "$1" = "integration" -o "$1" = "integration reset" ]; then
    echo " $0 integration reset"
  fi
  if [ -z "$1" -o "$1" = "integration" -o "$1" = "integration logs" ]; then
    echo " $0 integration logs"
  fi
  if [ -z "$1" -o "$1" = "integration" -o "$1" = "integration test" ]; then
    echo " $0 integration test"
  fi
}

internal_ask_down() {
  ENVIRONMENTS=$1

  CURRENT_ENVIRONMENT=$(docker compose ls -q | grep -E "${ENVIRONMENTS}" || echo "")
  if [ -n "${CURRENT_ENVIRONMENT}" ]; then
    read -p "Does \"${CURRENT_ENVIRONMENT}\" has to be to down (y/n)? : " ANSWER
    if [ "${ANSWER}" == "y" ]; then
      docker compose -p ${CURRENT_ENVIRONMENT} down -v
    else
      echo "Operation was cancelled"
      exit 0
    fi
  fi
}

# HANDLERS

build_printVersion() {
  ./mvnw -B help:evaluate -Dexpression=project.version -q -DforceStdout
}

build_withTests() {
  ./mvnw -B install
}

build_withoutTests() {
  ./mvnw -B install -DskipTests
}

build_testWithSonar() {
  ./mvnw -B install org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=OMGSERVERS_omgservers
}

build_beforeCommit() {
  environment_down
  build_withTests
  integration_test

  environment_down
}

build_pushImages() {
  ./mvnw -B install -DskipTests -Dquarkus.container-image.push=true
}

environment_printCurrent() {
  docker compose ls
}

environment_down() {
  read -p 'Continue (y/n)? : ' ANSWER
  if [ "${ANSWER}" == "y" ]; then
    docker compose -p orphan down -v
    docker compose -p singleinstance down -v
    docker compose -p integration down -v
    docker compose -p multiinstance down -v
  else
    echo "Operation was cancelled"
  fi
}

orphan_up() {
  internal_ask_down "singleinstance|integration|multiinstance"

  OMGSERVERS_VERSION=$(build_printVersion)

  if [ -z "${OMGSERVERS_VERSION}" ]; then
    echo "$(date) ERROR: Current version was not detected, OMGSERVERS_VERSION=${OMGSERVERS_VERSION}"
    exit 1
  fi

  echo "$(date) Using version, OMGSERVERS_VERSION=${OMGSERVERS_VERSION}"

  OMGSERVERS_VERSION=${OMGSERVERS_VERSION} docker compose -p orphan -f omgservers-testing/orphan-environment/src/compose.yaml up --remove-orphans -d
  docker compose -p orphan ps
}

orphan_ps() {
  docker compose -p orphan ps
}

orphan_reset() {
  read -p 'Continue (y/n)? : ' ANSWER
  if [ "${ANSWER}" == "y" ]; then
    docker compose -p orphan down -v
    orphan_up
  else
    echo "Operation was cancelled"
  fi
}

orphan_test() {
  OMGSERVERS_TESTER_ENVIRONMENT=orphan \
    ./mvnw -B -Dquarkus.test.profile=test -DskipITs=false -f pom.xml verify
}

singleinstance_up() {
  internal_ask_down "orphan|integration|multiinstance"

  OMGSERVERS_VERSION=$(build_printVersion)

  if [ -z "${OMGSERVERS_VERSION}" ]; then
    echo "$(date) ERROR: Current version was not detected, OMGSERVERS_VERSION=${OMGSERVERS_VERSION}"
    exit 1
  fi

  echo "$(date) Using version, OMGSERVERS_VERSION=${OMGSERVERS_VERSION}"

  OMGSERVERS_VERSION=${OMGSERVERS_VERSION} docker compose -p singleinstance -f omgservers-testing/singleinstance-environment/src/compose.yaml up --remove-orphans -d
  docker compose -p singleinstance ps
}

singleinstance_ps() {
  docker compose -p singleinstance ps
}

singleinstance_reset() {
  read -p 'Continue (y/n)? : ' ANSWER
  if [ "${ANSWER}" == "y" ]; then
    docker compose -p singleinstance down -v
    singleinstance_up
  else
    echo "Operation was cancelled"
  fi
}

singleinstance_logs() {
  docker compose -p singleinstance logs $@
}

singleinstance_database() {
  docker compose -p singleinstance exec database psql
}

singleinstance_ctl() {
  docker compose -p singleinstance exec ctl /bin/bash
}

singleinstance_test() {
  singleinstance_up

  OMGSERVERS_TESTER_ENVIRONMENT=SINGLEINSTANCE \
    ./mvnw -B -Dquarkus.test.profile=test -DskipITs=false -f pom.xml verify
}

multiinstance_up() {
  internal_ask_down "orphan|singleinstance|integration"

  OMGSERVERS_VERSION=$(build_printVersion)

  if [ -z "${OMGSERVERS_VERSION}" ]; then
    echo "$(date) ERROR: Current version was not detected, OMGSERVERS_VERSION=${OMGSERVERS_VERSION}"
    exit 1
  fi

  echo "$(date) Using version, OMGSERVERS_VERSION=${OMGSERVERS_VERSION}"

  OMGSERVERS_VERSION=${OMGSERVERS_VERSION} docker compose -p multiinstance -f omgservers-testing/multiinstance-environment/src/compose.yaml up --remove-orphans -d
  docker compose -p multiinstance ps
}

multiinstance_ps() {
  docker compose -p multiinstance ps
}

multiinstance_reset() {
  read -p 'Continue (y/n)? : ' ANSWER
  if [ "${ANSWER}" == "y" ]; then
    docker compose -p multiinstance down -v
    multiinstance_up
  else
    echo "Operation was cancelled"
  fi
}

multiinstance_test() {
  OMGSERVERS_TESTER_ENVIRONMENT=MULTIINSTANCE \
    ./mvnw -B -Dquarkus.test.profile=test -DskipITs=false -f pom.xml verify
}

integration_up() {
  internal_ask_down "orphan|singleinstance|multiinstance"

  OMGSERVERS_VERSION=$(build_printVersion)

  if [ -z "${OMGSERVERS_VERSION}" ]; then
    echo "$(date) ERROR: Current version was not detected, OMGSERVERS_VERSION=${OMGSERVERS_VERSION}"
    exit 1
  fi

  echo "$(date) Using version, OMGSERVERS_VERSION=${OMGSERVERS_VERSION}"

  OMGSERVERS_VERSION=${OMGSERVERS_VERSION} docker compose -p integration -f omgservers-testing/integration-environment/src/compose.yaml up --remove-orphans -d
  docker compose -p integration ps
}

integration_ps() {
  docker compose -p integration ps
}

integration_reset() {
  read -p 'Continue (y/n)? : ' ANSWER
  if [ "${ANSWER}" == "y" ]; then
    docker compose -p integration down -v
    integration_up
  else
    echo "Operation was cancelled"
  fi
}

integration_logs() {
  docker compose -p integration logs $@
}

integration_test() {
  integration_up

  OMGSERVERS_TESTER_ENVIRONMENT=INTEGRATION \
      ./mvnw -B -Dquarkus.test.profile=test -DskipITs=false -f pom.xml verify
}

# Build
if [ "$1" = "build" ]; then
  if [ "$2" = "printVersion" ]; then
    build_printVersion
  elif [ "$2" = "withTests" ]; then
    build_withTests
  elif [ "$2" = "withoutTests" ]; then
    build_withoutTests
  elif [ "$2" = "testWithSonar" ]; then
    build_testWithSonar
  elif [ "$2" = "beforeCommit" ]; then
    build_beforeCommit
  elif [ "$2" = "pushImages" ]; then
    build_pushImages
  else
    internal_help "build"
  fi
# Environment
elif [ "$1" = "environment" ]; then
  if [ "$2" = "printCurrent" ]; then
    environment_printCurrent
  elif [ "$2" = "down" ]; then
    environment_down
  else
    internal_help "environment"
  fi
elif [ "$1" = "orphan" ]; then
  if [ "$2" = "up" ]; then
    orphan_up
  elif [ "$2" = "ps" ]; then
    orphan_ps
  elif [ "$2" = "reset" ]; then
    orphan_reset
  elif [ "$2" = "test" ]; then
    orphan_test
  else
    internal_help "orphan"
  fi
elif [ "$1" = "singleinstance" ]; then
  if [ "$2" = "up" ]; then
    singleinstance_up
  elif [ "$2" = "ps" ]; then
    singleinstance_ps
  elif [ "$2" = "reset" ]; then
    singleinstance_reset
  elif [ "$2" = "logs" ]; then
    singleinstance_logs "${@:3}"
  elif [ "$2" = "database" ]; then
    singleinstance_database
  elif [ "$2" = "ctl" ]; then
    singleinstance_ctl
  elif [ "$2" = "test" ]; then
    singleinstance_test
  else
    internal_help "singleinstance"
  fi
elif [ "$1" = "multiinstance" ]; then
  if [ "$2" = "up" ]; then
    multiinstance_up
  elif [ "$2" = "ps" ]; then
    multiinstance_ps
  elif [ "$2" = "reset" ]; then
    multiinstance_reset
  elif [ "$2" = "test" ]; then
    multiinstance_test
  else
    internal_help "multiinstance"
  fi
elif [ "$1" = "integration" ]; then
  if [ "$2" = "up" ]; then
    integration_up
  elif [ "$2" = "ps" ]; then
    integration_ps
  elif [ "$2" = "reset" ]; then
    integration_reset
  elif [ "$2" = "logs" ]; then
    integration_logs "${@:3}"
  elif [ "$2" = "test" ]; then
    integration_test
  else
    internal_help "integration"
  fi
else
  internal_help
fi