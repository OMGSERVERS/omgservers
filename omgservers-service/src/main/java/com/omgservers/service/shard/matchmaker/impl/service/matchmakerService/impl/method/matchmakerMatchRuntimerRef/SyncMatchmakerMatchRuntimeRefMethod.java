package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchRuntimeRefMethod {
    Uni<SyncMatchmakerMatchRuntimeRefResponse> execute(SyncMatchmakerMatchRuntimeRefRequest request);
}
