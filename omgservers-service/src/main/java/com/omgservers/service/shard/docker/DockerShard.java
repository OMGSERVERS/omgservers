package com.omgservers.service.shard.docker;

import com.omgservers.service.shard.docker.impl.service.dockerService.DockerService;

public interface DockerShard {

    DockerService getService();
}
