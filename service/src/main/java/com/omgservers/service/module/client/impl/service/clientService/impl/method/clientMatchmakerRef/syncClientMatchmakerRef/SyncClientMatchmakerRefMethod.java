package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.syncClientMatchmakerRef;

import com.omgservers.schema.module.client.SyncClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.SyncClientMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMatchmakerRefMethod {
    Uni<SyncClientMatchmakerRefResponse> syncClientMatchmakerRef(SyncClientMatchmakerRefRequest request);
}
