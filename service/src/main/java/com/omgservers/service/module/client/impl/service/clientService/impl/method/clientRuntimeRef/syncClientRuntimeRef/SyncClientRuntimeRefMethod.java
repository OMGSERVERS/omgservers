package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.syncClientRuntimeRef;

import com.omgservers.schema.module.client.SyncClientRuntimeRefRequest;
import com.omgservers.schema.module.client.SyncClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientRuntimeRefMethod {
    Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(SyncClientRuntimeRefRequest request);
}
