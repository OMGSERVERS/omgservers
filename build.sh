#!/bin/bash
set -e

MVN_ARGS="clean install"

pushd omgservers-parent; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-exception; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-common; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-migration; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-model; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-dto; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-base; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-gateway; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-user; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-lua; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-version; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-tenant; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-matchmaker; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-runtime; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-context; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-handler; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-job; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-developer; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-admin; ./mvnw ${MVN_ARGS}; popd
pushd omgservers-application; ./mvnw ${MVN_ARGS}; popd

echo Build finished!