package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientRuntimeRef.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.ViewClientRuntimeRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewClientRuntimeRefsMethod {
    Uni<ViewClientRuntimeRefsResponse> execute(ShardModel shardModel, ViewClientRuntimeRefsRequest request);
}
