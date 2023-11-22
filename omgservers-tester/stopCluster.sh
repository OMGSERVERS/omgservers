#!/bin/bash

docker-compose -f docker-compose-cluster.yaml kill
docker-compose -f docker-compose-cluster.yaml rm