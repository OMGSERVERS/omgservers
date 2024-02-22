package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionMatchmakerRef;

import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMatchmakerRefMethod {
    Uni<SyncVersionMatchmakerRefResponse> syncVersionMatchmakerRef(SyncVersionMatchmakerRefRequest request);
}
