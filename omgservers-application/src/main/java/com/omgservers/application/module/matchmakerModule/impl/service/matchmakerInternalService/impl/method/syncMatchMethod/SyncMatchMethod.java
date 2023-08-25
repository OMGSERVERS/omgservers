package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchMethod;

import com.omgservers.dto.matchmakerModule.SyncMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchMethod {
    Uni<SyncMatchInternalResponse> syncMatch(SyncMatchInternalRequest request);

    default SyncMatchInternalResponse syncMatch(long timeout, SyncMatchInternalRequest request) {
        return syncMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
