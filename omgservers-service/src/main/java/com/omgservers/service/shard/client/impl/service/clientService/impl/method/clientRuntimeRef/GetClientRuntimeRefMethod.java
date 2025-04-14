package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientRuntimeRef.GetClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.GetClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientRuntimeRefMethod {
    Uni<GetClientRuntimeRefResponse> execute(ShardModel shardModel, GetClientRuntimeRefRequest request);
}
