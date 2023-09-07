package com.omgservers.module.system.impl.service.indexService.impl.method.syncIndex;

import com.omgservers.dto.internal.SyncIndexRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<Void> syncIndex(SyncIndexRequest request);
}
