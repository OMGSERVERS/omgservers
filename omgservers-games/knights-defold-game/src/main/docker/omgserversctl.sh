#!/bin/bash
set -e

CTL_DIRECTORY=/tmp/knights-defold-game/.omgserversctl
if [ ! -d CTL_DIRECTORY ]; then
  mkdir -p ${CTL_DIRECTORY}
fi

docker run --rm -it \
  --network=host \
  -e OMGSERVERSCTL_DIRECTORY=/tmp/.omgserversctl \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v ${CTL_DIRECTORY}:/tmp/.omgserversctl \
  omgservers/ctl:1.0.0-SNAPSHOT $@

