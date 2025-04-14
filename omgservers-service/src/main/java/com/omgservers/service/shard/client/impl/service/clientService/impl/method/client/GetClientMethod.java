package com.omgservers.service.shard.client.impl.service.clientService.impl.method.client;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.client.GetClientRequest;
import com.omgservers.schema.shard.client.client.GetClientResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientMethod {
    Uni<GetClientResponse> execute(ShardModel shardModel, GetClientRequest request);
}
