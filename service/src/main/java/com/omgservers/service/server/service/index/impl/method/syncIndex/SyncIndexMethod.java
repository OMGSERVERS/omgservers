package com.omgservers.service.server.service.index.impl.method.syncIndex;

import com.omgservers.schema.service.system.SyncIndexRequest;
import com.omgservers.schema.service.system.SyncIndexResponse;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<SyncIndexResponse> syncIndex(SyncIndexRequest request);
}
