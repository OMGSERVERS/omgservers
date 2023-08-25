package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchmakerMethod;

import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchmakerMethod {

    Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerInternalRequest request);

    default SyncMatchmakerInternalResponse syncMatchmaker(long timeout, SyncMatchmakerInternalRequest request) {
        return syncMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
