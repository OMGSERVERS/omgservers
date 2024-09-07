#!/bin/bash

if [ ! -d .omgserversctl ]; then
  mkdir -p .omgserversctl
fi

docker run --rm -it \
  --network=host \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v $PWD/.omgserversctl:/root/.omgserversctl \
  -v $PWD/server:/opt/omgserversctl/server \
  omgservers/ctl:1.0.0-SNAPSHOT $@