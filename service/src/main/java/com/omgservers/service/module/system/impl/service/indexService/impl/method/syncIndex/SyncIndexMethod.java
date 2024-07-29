package com.omgservers.service.module.system.impl.service.indexService.impl.method.syncIndex;

import com.omgservers.schema.service.system.SyncIndexRequest;
import com.omgservers.schema.service.system.SyncIndexResponse;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<SyncIndexResponse> syncIndex(SyncIndexRequest request);
}
