package com.omgservers.module.user.impl.service.clientService.impl.method.syncClient;

import com.omgservers.dto.user.SyncClientShardedResponse;
import com.omgservers.dto.user.SyncClientShardedRequest;
import io.smallrye.mutiny.Uni;

public interface SyncClientMethod {

    Uni<SyncClientShardedResponse> syncClient(SyncClientShardedRequest request);
}
