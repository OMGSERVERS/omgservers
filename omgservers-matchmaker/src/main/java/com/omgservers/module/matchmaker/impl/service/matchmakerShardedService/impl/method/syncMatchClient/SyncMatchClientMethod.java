package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncMatchClient;

import com.omgservers.dto.matchmaker.SyncMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchClientMethod {
    Uni<SyncMatchClientShardedResponse> syncMatchClient(SyncMatchClientShardedRequest request);

    default SyncMatchClientShardedResponse syncMatchClient(long timeout, SyncMatchClientShardedRequest request) {
        return syncMatchClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
