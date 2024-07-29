package com.omgservers.service.server.operation.getDockerClient;

import com.github.dockerjava.api.DockerClient;

import java.net.URI;

public interface GetDockerClientOperation {
    DockerClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
