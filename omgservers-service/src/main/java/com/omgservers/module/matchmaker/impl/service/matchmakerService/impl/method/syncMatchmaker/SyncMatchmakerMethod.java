package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmaker;

import com.omgservers.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface SyncMatchmakerMethod {

    Uni<SyncMatchmakerResponse> syncMatchmaker(SyncMatchmakerRequest request);

    default SyncMatchmakerResponse syncMatchmaker(long timeout, SyncMatchmakerRequest request) {
        return syncMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
