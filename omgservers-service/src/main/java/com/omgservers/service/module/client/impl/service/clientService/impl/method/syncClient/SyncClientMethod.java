package com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClient;

import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMethod {
    Uni<SyncClientResponse> syncClient(SyncClientRequest request);
}
