#!/bin/bash
set -e

docker-compose kill
docker-compose rm -f
docker-compose up -d --remove-orphans
