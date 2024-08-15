package com.omgservers.service.service.index.impl.method.syncIndex;

import com.omgservers.service.service.index.dto.SyncIndexRequest;
import com.omgservers.service.service.index.dto.SyncIndexResponse;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<SyncIndexResponse> syncIndex(SyncIndexRequest request);
}
