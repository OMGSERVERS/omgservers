package com.omgservers.service.module.client.impl.service.clientService.impl.method.client.syncClient;

import com.omgservers.schema.module.client.SyncClientRequest;
import com.omgservers.schema.module.client.SyncClientResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMethod {
    Uni<SyncClientResponse> syncClient(SyncClientRequest request);
}
