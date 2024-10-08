package com.omgservers.service.module.docker.impl.operation;

import java.net.URI;

public interface GetDockerModuleClientOperation {
    DockerModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
