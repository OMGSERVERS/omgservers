package com.omgservers.service.module.pool.impl.service.dockerService.impl.operation;

import com.github.dockerjava.api.DockerClient;

import java.net.URI;

public interface GetDockerDaemonClientOperation {
    DockerClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
