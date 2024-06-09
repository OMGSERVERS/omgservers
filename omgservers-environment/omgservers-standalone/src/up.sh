#!/bin/bash
set -e

pushd certs
./generate_key_pair.sh jwt_keys
popd

docker compose up --remove-orphans -d
docker compose ps