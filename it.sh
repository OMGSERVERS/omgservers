#!/bin/bash

mvn -Dquarkus.test.profile=local \
  -DskipITs=false \
  -DdockerCompose.skip=false \
  -f omgservers-tester/pom.xml \
  verify