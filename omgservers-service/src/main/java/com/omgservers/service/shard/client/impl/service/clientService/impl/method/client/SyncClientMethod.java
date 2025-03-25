package com.omgservers.service.shard.client.impl.service.clientService.impl.method.client;

import com.omgservers.schema.module.client.client.SyncClientRequest;
import com.omgservers.schema.module.client.client.SyncClientResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMethod {
    Uni<SyncClientResponse> execute(SyncClientRequest request);
}
