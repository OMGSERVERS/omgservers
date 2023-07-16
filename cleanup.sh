#!/bin/bash
set -e

ls | grep omgservers | xargs -I {} bash -c "pushd {}; ./mvnw clean; rm -rf ./target popd"
