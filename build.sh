#!/bin/bash
set -e

pushd omgservers-parent; ./mvnw clean install; popd
pushd omgservers-luaj; ./mvnw clean install; popd
pushd omgservers-exception; ./mvnw clean install; popd
pushd omgservers-model; ./mvnw clean install; popd
pushd omgservers-dto; ./mvnw clean install; popd
pushd omgservers-base; ./mvnw clean install; popd
pushd omgservers-application; ./mvnw clean install; popd

echo Build finished!