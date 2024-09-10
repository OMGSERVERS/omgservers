#!/bin/bash
set -e

echo "Build docker image"

mvn clean install

echo "All is done"