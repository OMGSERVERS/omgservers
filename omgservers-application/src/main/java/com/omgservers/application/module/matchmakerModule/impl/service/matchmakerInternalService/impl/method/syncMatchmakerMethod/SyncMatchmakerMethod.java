package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchmakerMethod;

import com.omgservers.dto.matchmakerModule.SyncMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchmakerMethod {

    Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerRoutedRequest request);

    default SyncMatchmakerInternalResponse syncMatchmaker(long timeout, SyncMatchmakerRoutedRequest request) {
        return syncMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
