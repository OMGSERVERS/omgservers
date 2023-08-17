#!/bin/bash

curl -s -S --fail-with-body \
  -u "admin:admin" \
  -X PUT "http://localhost:10001/omgservers/admin-api/v1/request/ping-server"

curl -s -S --fail-with-body \
  -u "admin:admin" \
  -X PUT "http://localhost:10002/omgservers/admin-api/v1/request/ping-server"

curl -s -S --fail-with-body \
  -u "admin:admin" \
  -X PUT "http://localhost:10003/omgservers/admin-api/v1/request/ping-server"