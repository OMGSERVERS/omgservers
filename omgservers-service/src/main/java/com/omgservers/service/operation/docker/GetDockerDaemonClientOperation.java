package com.omgservers.service.operation.docker;

import com.github.dockerjava.api.DockerClient;

import java.net.URI;

public interface GetDockerDaemonClientOperation {
    DockerClient execute(URI uri);
}
