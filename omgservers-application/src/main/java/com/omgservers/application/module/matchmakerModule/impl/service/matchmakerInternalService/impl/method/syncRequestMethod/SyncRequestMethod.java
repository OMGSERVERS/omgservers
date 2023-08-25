package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncRequestMethod;

import com.omgservers.dto.matchmakerModule.SyncRequestInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRequestMethod {
    Uni<SyncRequestInternalResponse> syncRequest(SyncRequestInternalRequest request);

    default SyncRequestInternalResponse syncRequest(long timeout, SyncRequestInternalRequest request) {
        return syncRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
