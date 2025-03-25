package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.module.client.clientRuntimeRef.SyncClientRuntimeRefRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.SyncClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientRuntimeRefMethod {
    Uni<SyncClientRuntimeRefResponse> execute(SyncClientRuntimeRefRequest request);
}
