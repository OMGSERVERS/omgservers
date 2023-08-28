package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.getMatchmaker;

import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetMatchmakerMethod {
    Uni<GetMatchmakerShardedResponse> getMatchmaker(GetMatchmakerShardedRequest request);

    default GetMatchmakerShardedResponse getMatchmaker(long timeout, GetMatchmakerShardedRequest request) {
        return getMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
