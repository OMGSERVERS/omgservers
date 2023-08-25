package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.syncVersionMethod;

import com.omgservers.dto.versionModule.SyncVersionInternalRequest;
import com.omgservers.dto.versionModule.SyncVersionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMethod {
    Uni<SyncVersionInternalResponse> syncVersion(SyncVersionInternalRequest request);
}
