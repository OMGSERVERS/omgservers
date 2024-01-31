package com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClientRuntime;

import com.omgservers.model.dto.client.SyncClientRuntimeRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientRuntimeMethod {
    Uni<SyncClientRuntimeResponse> syncClientRuntime(SyncClientRuntimeRequest request);
}
