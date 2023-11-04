package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncRequest;

import com.omgservers.model.dto.matchmaker.SyncRequestResponse;
import com.omgservers.model.dto.matchmaker.SyncRequestRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRequestMethod {
    Uni<SyncRequestResponse> syncRequest(SyncRequestRequest request);

    default SyncRequestResponse syncRequest(long timeout, SyncRequestRequest request) {
        return syncRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
