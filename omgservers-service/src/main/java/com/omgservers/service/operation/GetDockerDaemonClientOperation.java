package com.omgservers.service.operation;

import com.github.dockerjava.api.DockerClient;

import java.net.URI;

public interface GetDockerDaemonClientOperation {
    DockerClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
