package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncMatchmaker;

import com.omgservers.dto.matchmaker.SyncMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchmakerMethod {

    Uni<SyncMatchmakerShardResponse> syncMatchmaker(SyncMatchmakerShardedRequest request);

    default SyncMatchmakerShardResponse syncMatchmaker(long timeout, SyncMatchmakerShardedRequest request) {
        return syncMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
