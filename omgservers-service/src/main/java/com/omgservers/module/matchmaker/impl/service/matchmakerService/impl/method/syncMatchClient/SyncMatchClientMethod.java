package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchClient;

import com.omgservers.model.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchClientResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchClientMethod {
    Uni<SyncMatchClientResponse> syncMatchClient(SyncMatchClientRequest request);

    default SyncMatchClientResponse syncMatchClient(long timeout, SyncMatchClientRequest request) {
        return syncMatchClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
