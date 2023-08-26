package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchmakerMethod;

import com.omgservers.dto.matchmakerModule.SyncMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchmakerMethod {

    Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerShardRequest request);

    default SyncMatchmakerInternalResponse syncMatchmaker(long timeout, SyncMatchmakerShardRequest request) {
        return syncMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
