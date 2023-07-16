package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.syncVersionMethod;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.SyncVersionInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMethod {
    Uni<Void> syncVersion(SyncVersionInternalRequest request);
}
