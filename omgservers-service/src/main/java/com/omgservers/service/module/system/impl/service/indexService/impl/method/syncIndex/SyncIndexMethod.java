package com.omgservers.service.module.system.impl.service.indexService.impl.method.syncIndex;

import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<SyncIndexResponse> syncIndex(SyncIndexRequest request);
}
