package com.omgservers.service.shard.docker.impl.service.dockerService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.docker.StartDockerContainerRequest;
import com.omgservers.schema.shard.docker.StartDockerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface StartDockerContainerMethod {
    Uni<StartDockerContainerResponse> execute(ShardModel shardModel, StartDockerContainerRequest request);
}
