package com.omgservers.service.module.system.impl.component;

import com.github.dockerjava.api.DockerClient;

public interface DockerClientWrapper {

    DockerClient getDockerClient();
}
