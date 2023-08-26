package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncRequestMethod;

import com.omgservers.dto.matchmakerModule.SyncRequestRoutedRequest;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncRequestMethod {
    Uni<SyncRequestInternalResponse> syncRequest(SyncRequestRoutedRequest request);

    default SyncRequestInternalResponse syncRequest(long timeout, SyncRequestRoutedRequest request) {
        return syncRequest(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
