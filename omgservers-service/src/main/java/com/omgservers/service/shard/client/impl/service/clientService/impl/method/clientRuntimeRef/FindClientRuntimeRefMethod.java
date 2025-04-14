package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientRuntimeRef.FindClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.FindClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindClientRuntimeRefMethod {
    Uni<FindClientRuntimeRefResponse> execute(ShardModel shardModel, FindClientRuntimeRefRequest request);
}
