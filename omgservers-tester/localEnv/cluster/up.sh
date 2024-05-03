#!/bin/bash

docker compose up --remove-orphans -d
docker compose ps

#sleep 4
#
## Init
#docker exec docker docker network create omgservers
#docker tag omgservers/omgservers-luajit:1.0.0-SNAPSHOT localhost:5050/omgservers/omgservers-luajit:1.0.0-SNAPSHOT \
#  && docker push localhost:5050/omgservers/omgservers-luajit:1.0.0-SNAPSHOT
#
#docker tag omgservers/omgservers-luajit:1.0.0-SNAPSHOT localhost:5050/omgservers/omgservers-luajit:1.0.0-SNAPSHOT \
#  && docker push localhost:5050/omgservers/omgservers-luajit:1.0.0-SNAPSHOT
