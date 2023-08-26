package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.syncVersionMethod;

import com.omgservers.dto.versionModule.SyncVersionShardRequest;
import com.omgservers.dto.versionModule.SyncVersionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMethod {
    Uni<SyncVersionInternalResponse> syncVersion(SyncVersionShardRequest request);
}
