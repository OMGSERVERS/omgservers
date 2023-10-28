package com.omgservers.module.user.impl.service.clientService.impl.method.syncClient;

import com.omgservers.model.dto.user.SyncClientResponse;
import com.omgservers.model.dto.user.SyncClientRequest;
import io.smallrye.mutiny.Uni;

public interface SyncClientMethod {

    Uni<SyncClientResponse> syncClient(SyncClientRequest request);
}
