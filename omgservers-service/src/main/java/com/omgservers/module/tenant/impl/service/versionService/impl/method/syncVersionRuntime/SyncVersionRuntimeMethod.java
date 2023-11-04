package com.omgservers.module.tenant.impl.service.versionService.impl.method.syncVersionRuntime;

import com.omgservers.model.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionRuntimeMethod {
    Uni<SyncVersionRuntimeResponse> syncVersionRuntime(SyncVersionRuntimeRequest request);
}
