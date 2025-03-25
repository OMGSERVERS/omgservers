package com.omgservers.service.shard.match.impl.service.matchService.impl.method;

import com.omgservers.schema.module.match.SyncMatchRequest;
import com.omgservers.schema.module.match.SyncMatchResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchMethod {
    Uni<SyncMatchResponse> execute(SyncMatchRequest request);
}
