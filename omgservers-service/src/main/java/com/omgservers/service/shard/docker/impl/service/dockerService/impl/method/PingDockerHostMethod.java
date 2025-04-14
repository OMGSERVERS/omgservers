package com.omgservers.service.shard.docker.impl.service.dockerService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.docker.PingDockerHostRequest;
import com.omgservers.schema.shard.docker.PingDockerHostResponse;
import io.smallrye.mutiny.Uni;

public interface PingDockerHostMethod {
    Uni<PingDockerHostResponse> execute(ShardModel shardModel, PingDockerHostRequest request);
}
