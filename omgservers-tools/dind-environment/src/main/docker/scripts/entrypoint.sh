#!/bin/sh
set -e
set -m

dockerd-entrypoint.sh &
while ! docker info >/dev/null 2>&1; do sleep 1; done
echo "Dind is up and running"

docker compose -p omgservers -f configs/compose.yaml up --remove-orphans $@
