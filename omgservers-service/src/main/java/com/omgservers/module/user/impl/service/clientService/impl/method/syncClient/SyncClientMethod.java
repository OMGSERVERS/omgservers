package com.omgservers.module.user.impl.service.clientService.impl.method.syncClient;

import com.omgservers.dto.user.SyncClientResponse;
import com.omgservers.dto.user.SyncClientRequest;
import io.smallrye.mutiny.Uni;

public interface SyncClientMethod {

    Uni<SyncClientResponse> syncClient(SyncClientRequest request);
}
