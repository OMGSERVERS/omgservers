package com.omgservers.service.module.system.impl.component.dockerClient;

import com.github.dockerjava.api.DockerClient;

public interface DockerClientHolder {

    DockerClient getDockerClient();
}
