package com.omgservers.service.shard.client.impl.service.clientService.impl.method.client;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.client.client.DeleteClientRequest;
import com.omgservers.schema.module.client.client.DeleteClientResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMethod {
    Uni<DeleteClientResponse> execute(ShardModel shardModel, DeleteClientRequest request);
}
