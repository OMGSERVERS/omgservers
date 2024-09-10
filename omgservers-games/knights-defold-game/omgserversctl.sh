#!/bin/bash
set -e

docker run --rm -it \
  --network=host \
  -v ${PWD}/.omgserversctl:/opt/omgserversctl/.omgserversctl \
  -v ${PWD}/src:/opt/omgserversctl/src \
  omgservers/ctl:1.0.0-SNAPSHOT $@

