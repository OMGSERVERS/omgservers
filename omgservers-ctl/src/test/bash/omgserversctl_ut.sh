#!/bin/bash
set -e

SCRIPT_PATH="src/main/docker/omgserversctl.sh"
SCRIPT_NAME=$(basename $SCRIPT_PATH)

test_without_arguments() {
  {
    OUTPUT=$($SCRIPT_PATH)
    RESULT=$?
    if [ $RESULT -eq 0 ]; then
      echo "Test $SCRIPT_NAME without arguments: PASSED"
    else
      echo "Test $SCRIPT_NAME without arguments: FAILED"
      echo "$OUTPUT"
      exit 1
    fi
  } || true
}

run_unit_tests() {
  test_without_arguments
  echo "All unit tests passed!"
}

run_unit_tests
