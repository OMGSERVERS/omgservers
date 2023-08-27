#!/bin/bash
set -e

pushd omgservers-parent; ./mvnw clean install; popd
pushd omgservers-exception; ./mvnw clean install; popd
pushd omgservers-model; ./mvnw clean install; popd
pushd omgservers-dto; ./mvnw clean install; popd
pushd omgservers-base; ./mvnw clean install; popd
pushd omgservers-gateway; ./mvnw clean install; popd
pushd omgservers-user; ./mvnw clean install; popd
pushd omgservers-lua; ./mvnw clean install; popd
pushd omgservers-version; ./mvnw clean install; popd
pushd omgservers-tenant; ./mvnw clean install; popd
pushd omgservers-matchmaker; ./mvnw clean install; popd
pushd omgservers-handler; ./mvnw clean install; popd
pushd omgservers-runtime; ./mvnw clean install; popd
pushd omgservers-developer; ./mvnw clean install; popd
pushd omgservers-admin; ./mvnw clean install; popd
pushd omgservers-application; ./mvnw clean install; popd

echo Build finished!