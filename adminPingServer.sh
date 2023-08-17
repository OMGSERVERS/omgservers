#!/bin/bash

curl -s -S --fail-with-body \
  -u "admin:admin" \
  -X PUT "http://localhost:10001/omgservers/admin-api/v1/request/ping-server" \
