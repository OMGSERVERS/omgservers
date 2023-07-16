#!/bin/bash
set -e

pushd omgservers-parent; ./mvnw clean install; popd
pushd omgservers-application; ./mvnw clean install; popd

echo build finished!