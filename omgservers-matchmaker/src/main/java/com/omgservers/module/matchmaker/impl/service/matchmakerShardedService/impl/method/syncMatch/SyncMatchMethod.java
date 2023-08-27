package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncMatch;

import com.omgservers.dto.matchmaker.SyncMatchShardResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchMethod {
    Uni<SyncMatchShardResponse> syncMatch(SyncMatchShardedRequest request);

    default SyncMatchShardResponse syncMatch(long timeout, SyncMatchShardedRequest request) {
        return syncMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
