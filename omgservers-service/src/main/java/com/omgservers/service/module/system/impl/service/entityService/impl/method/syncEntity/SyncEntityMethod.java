package com.omgservers.service.module.system.impl.service.entityService.impl.method.syncEntity;

import com.omgservers.model.dto.system.SyncEntityRequest;
import com.omgservers.model.dto.system.SyncEntityResponse;
import io.smallrye.mutiny.Uni;

public interface SyncEntityMethod {
    Uni<SyncEntityResponse> syncEntity(SyncEntityRequest request);
}
