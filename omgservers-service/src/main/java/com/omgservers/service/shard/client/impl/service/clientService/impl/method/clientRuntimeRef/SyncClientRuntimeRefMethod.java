package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientRuntimeRef.SyncClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.SyncClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientRuntimeRefMethod {
    Uni<SyncClientRuntimeRefResponse> execute(ShardModel shardModel, SyncClientRuntimeRefRequest request);
}
