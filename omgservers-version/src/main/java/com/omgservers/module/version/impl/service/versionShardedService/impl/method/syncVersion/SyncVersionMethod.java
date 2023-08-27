package com.omgservers.module.version.impl.service.versionShardedService.impl.method.syncVersion;

import com.omgservers.dto.version.SyncVersionShardedRequest;
import com.omgservers.dto.version.SyncVersionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMethod {
    Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request);
}
