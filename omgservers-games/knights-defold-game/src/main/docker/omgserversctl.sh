#!/bin/bash
set -e

docker run --rm -it \
  --network=host \
  -v ${PWD}/.omgserversctl:/opt/omgserversctl/.omgserversctl \
  -v ${PWD}/knights-defold-game:/opt/omgserversctl/knights-defold-game \
  omgservers/ctl:1.0.0-SNAPSHOT $@

