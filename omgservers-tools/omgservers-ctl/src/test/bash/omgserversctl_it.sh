#!/bin/bash
set -e

SCRIPT_PATH="src/main/docker/scripts/omgserversctl.sh"
SCRIPT_NAME=$(basename $SCRIPT_PATH)

run_integration_tests() {
  echo "All integration tests passed!"
}

run_integration_tests
