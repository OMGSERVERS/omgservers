package com.omgservers.service.server.index.impl.method.syncIndex;

import com.omgservers.service.server.index.dto.SyncIndexRequest;
import com.omgservers.service.server.index.dto.SyncIndexResponse;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<SyncIndexResponse> syncIndex(SyncIndexRequest request);
}
