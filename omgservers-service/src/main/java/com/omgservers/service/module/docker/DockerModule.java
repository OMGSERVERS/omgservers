package com.omgservers.service.module.docker;

import com.omgservers.service.module.docker.impl.service.dockerService.DockerService;

public interface DockerModule {

    DockerService getService();
}
