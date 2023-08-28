package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncMatchmaker;

import com.omgservers.dto.matchmaker.SyncMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchmakerMethod {

    Uni<SyncMatchmakerShardedResponse> syncMatchmaker(SyncMatchmakerShardedRequest request);

    default SyncMatchmakerShardedResponse syncMatchmaker(long timeout, SyncMatchmakerShardedRequest request) {
        return syncMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
