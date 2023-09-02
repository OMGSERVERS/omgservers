package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.syncVersion;

import com.omgservers.dto.tenant.SyncVersionShardedRequest;
import com.omgservers.dto.tenant.SyncVersionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMethod {
    Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request);
}
