#!/bin/bash

curl -s -S --fail-with-body \
  -u "admin:admin" \
  -X PUT "http://localhost:10001/omgservers/admin-api/v1/request/collect-logs" \
  -H "Content-type: application/json" \
  -d {} \
  | jq . > /tmp/collect_logs.json

echo Saved to /tmp/collect_logs.json
open /tmp/collect_logs.json
