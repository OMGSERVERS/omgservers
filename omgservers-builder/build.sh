#!/bin/bash

PROJECT_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
IMAGE_NAME="omgservers/omgservers-builder:$PROJECT_VERSION"

docker build -t $IMAGE_NAME -f src/main/docker/Dockerfile .