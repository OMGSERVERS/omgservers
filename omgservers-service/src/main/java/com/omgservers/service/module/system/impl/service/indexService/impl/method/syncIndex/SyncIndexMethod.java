package com.omgservers.service.module.system.impl.service.indexService.impl.method.syncIndex;

import com.omgservers.model.dto.system.SyncIndexRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<Void> syncIndex(SyncIndexRequest request);
}
