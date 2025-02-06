#!/bin/sh
set -e

echo WORKING_DIR=$WORKING_DIR

docker run --rm \
  --network=host \
  -v ${WORKING_DIR}/.omgserversctl:/opt/omgserversctl/.omgserversctl \
  -v ${WORKING_DIR}/config.json:/opt/omgserversctl/config.json:ro \
  -v /etc/resolv.conf:/etc/resolv.conf:ro \
  omgservers/ctl:${OMGSERVERS_VERSION} $@