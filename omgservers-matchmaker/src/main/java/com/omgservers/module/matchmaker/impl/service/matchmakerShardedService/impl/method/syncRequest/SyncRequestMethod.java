package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncRequest;

import com.omgservers.dto.matchmaker.SyncRequestShardedResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRequestMethod {
    Uni<SyncRequestShardedResponse> syncRequest(SyncRequestShardedRequest request);

    default SyncRequestShardedResponse syncRequest(long timeout, SyncRequestShardedRequest request) {
        return syncRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
