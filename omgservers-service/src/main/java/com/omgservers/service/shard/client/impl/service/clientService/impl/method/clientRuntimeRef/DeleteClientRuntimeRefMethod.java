package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientRuntimeRef.DeleteClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.DeleteClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientRuntimeRefMethod {
    Uni<DeleteClientRuntimeRefResponse> execute(ShardModel shardModel, DeleteClientRuntimeRefRequest request);
}
