package com.omgservers.module.tenant.impl.service.versionService.impl.method.syncVersion;

import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.dto.tenant.SyncVersionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMethod {
    Uni<SyncVersionResponse> syncVersion(SyncVersionRequest request);
}
