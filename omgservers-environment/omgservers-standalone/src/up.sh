#!/bin/bash
set -e

docker compose up --remove-orphans -d
docker compose ps