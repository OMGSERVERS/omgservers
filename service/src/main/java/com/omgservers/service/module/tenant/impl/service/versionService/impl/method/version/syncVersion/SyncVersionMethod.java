package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.syncVersion;

import com.omgservers.schema.module.tenant.SyncVersionRequest;
import com.omgservers.schema.module.tenant.SyncVersionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMethod {
    Uni<SyncVersionResponse> syncVersion(SyncVersionRequest request);
}
