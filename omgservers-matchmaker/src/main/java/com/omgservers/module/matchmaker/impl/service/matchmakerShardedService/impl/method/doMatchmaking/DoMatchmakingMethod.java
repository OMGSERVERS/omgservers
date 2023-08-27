package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.doMatchmaking;

import com.omgservers.dto.matchmaker.DoMatchmakingShardResponse;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DoMatchmakingMethod {
    Uni<DoMatchmakingShardResponse> doMatchmaking(DoMatchmakingShardedRequest request);

    default DoMatchmakingShardResponse doMatchmaking(long timeout, DoMatchmakingShardedRequest request) {
        return doMatchmaking(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
