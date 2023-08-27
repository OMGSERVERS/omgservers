package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncRequest;

import com.omgservers.dto.matchmaker.SyncRequestShardResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRequestMethod {
    Uni<SyncRequestShardResponse> syncRequest(SyncRequestShardedRequest request);

    default SyncRequestShardResponse syncRequest(long timeout, SyncRequestShardedRequest request) {
        return syncRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
