package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.doMatchmakingMethod;

import com.omgservers.dto.matchmakerModule.DoMatchmakingShardRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DoMatchmakingMethod {
    Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingShardRequest request);

    default DoMatchmakingInternalResponse doMatchmaking(long timeout, DoMatchmakingShardRequest request) {
        return doMatchmaking(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
