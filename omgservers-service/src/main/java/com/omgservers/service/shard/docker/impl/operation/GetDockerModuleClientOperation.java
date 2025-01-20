package com.omgservers.service.shard.docker.impl.operation;

import java.net.URI;

public interface GetDockerModuleClientOperation {
    DockerModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
