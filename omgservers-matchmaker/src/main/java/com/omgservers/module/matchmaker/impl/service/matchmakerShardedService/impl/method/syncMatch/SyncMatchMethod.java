package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncMatch;

import com.omgservers.dto.matchmaker.SyncMatchShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchMethod {
    Uni<SyncMatchShardedResponse> syncMatch(SyncMatchShardedRequest request);

    default SyncMatchShardedResponse syncMatch(long timeout, SyncMatchShardedRequest request) {
        return syncMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
