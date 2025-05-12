package com.omgservers.service.shard.docker.impl.operation;

import java.net.URI;

public interface GetDockerModuleClientOperation {
    DockerModuleClient execute(URI uri);
}
