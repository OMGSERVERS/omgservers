package com.omgservers.service.shard.client.impl.service.clientService.impl.method.client;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.client.SyncClientRequest;
import com.omgservers.schema.shard.client.client.SyncClientResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMethod {
    Uni<SyncClientResponse> execute(ShardModel shardModel, SyncClientRequest request);
}
