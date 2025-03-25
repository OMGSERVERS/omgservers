package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchResourceMethod {
    Uni<SyncMatchmakerMatchResourceResponse> execute(SyncMatchmakerMatchResourceRequest request);
}
