package com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClientRuntimeRef;

import com.omgservers.model.dto.client.SyncClientRuntimeRefRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientRuntimeRefMethod {
    Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(SyncClientRuntimeRefRequest request);
}
