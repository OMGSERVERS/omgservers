package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.doMatchmaking;

import com.omgservers.dto.matchmaker.DoMatchmakingShardedResponse;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DoMatchmakingMethod {
    Uni<DoMatchmakingShardedResponse> doMatchmaking(DoMatchmakingShardedRequest request);

    default DoMatchmakingShardedResponse doMatchmaking(long timeout, DoMatchmakingShardedRequest request) {
        return doMatchmaking(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
