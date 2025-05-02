package com.omgservers.ctl.operation.docker;

import com.github.dockerjava.api.DockerClient;

import java.net.URI;

public interface CreateDockerDaemonClientOperation {
    DockerClient execute(URI dockerDaemonUri,
                         URI registryUri,
                         String developer,
                         String password);
}
