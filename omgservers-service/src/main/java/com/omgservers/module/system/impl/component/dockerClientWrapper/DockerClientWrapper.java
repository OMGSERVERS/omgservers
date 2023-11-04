package com.omgservers.module.system.impl.component.dockerClientWrapper;

import com.github.dockerjava.api.DockerClient;

public interface DockerClientWrapper {

    DockerClient getDockerClient();
}
