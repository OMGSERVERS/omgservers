package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatch;

import com.omgservers.model.dto.matchmaker.SyncMatchResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchMethod {
    Uni<SyncMatchResponse> syncMatch(SyncMatchRequest request);

    default SyncMatchResponse syncMatch(long timeout, SyncMatchRequest request) {
        return syncMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
