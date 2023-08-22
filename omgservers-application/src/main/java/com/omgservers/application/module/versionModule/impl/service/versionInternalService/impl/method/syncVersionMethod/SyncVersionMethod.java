package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.syncVersionMethod;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.SyncVersionInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.SyncVersionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMethod {
    Uni<SyncVersionInternalResponse> syncVersion(SyncVersionInternalRequest request);
}
