package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.syncVersionMatchmakerRef;

import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMatchmakerRefMethod {
    Uni<SyncVersionMatchmakerRefResponse> syncVersionMatchmakerRef(SyncVersionMatchmakerRefRequest request);
}
