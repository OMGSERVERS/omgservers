package com.omgservers.tester.operation.getDockerClient;

import com.github.dockerjava.api.DockerClient;

public interface GetDockerClientOperation {
    DockerClient getDockerClient(Long developerUserId, String developerPassword);
}
