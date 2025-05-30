#!/bin/bash
set -e

VERSION="0.5.0"

DOWNLOAD_URL="https://github.com/OMGSERVERS/omgservers/releases/download/${VERSION}/localtesting-environment.zip"
ARCHIVE_NAME=$(basename ${DOWNLOAD_URL})
TEMP_DIR=$(mktemp -d)

echo "Getting ${DOWNLOAD_URL}"
curl -L ${DOWNLOAD_URL} -o "${TEMP_DIR}/${ARCHIVE_NAME}"
unzip -q "${TEMP_DIR}/${ARCHIVE_NAME}" -d "${TEMP_DIR}"

echo "Bring up local testing environment"
docker compose -f "${TEMP_DIR}/localtesting-environment/compose.yaml" up -d
