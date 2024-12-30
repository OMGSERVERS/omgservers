package com.omgservers.service.entrypoint.docker;

import com.omgservers.service.entrypoint.docker.impl.service.dockerService.DockerService;

public interface DockerEntrypoint {

    DockerService getService();
}