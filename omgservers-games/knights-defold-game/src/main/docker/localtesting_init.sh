#!/bin/bash
set -e

./omgserversctl.sh environment useLocal

./omgserversctl.sh localtesting printProject || ./omgserversctl.sh localtesting createProject

ENVIRONMENT_NAME=$(./omgserversctl.sh environment printVariable ENVIRONMENT_NAME)
LOCALTESTING_TENANT_ID=$(./omgserversctl.sh environment printVariable LOCALTESTING_TENANT_ID)
LOCALTESTING_STAGE_ID=$(./omgserversctl.sh environment printVariable LOCALTESTING_STAGE_ID)
LOCALTESTING_STAGE_SECRET=$(./omgserversctl.sh environment printVariable LOCALTESTING_STAGE_SECRET)

cat > ./knights-defold-game/main/localtesting.lua << EOF
return {
  tenant_id = "${LOCALTESTING_TENANT_ID}",
  stage_id = "${LOCALTESTING_STAGE_ID}",
  stage_secret = "${LOCALTESTING_STAGE_SECRET}",
}
EOF
echo "Project file ./knights-defold-game/main/localtesting.lua was written"