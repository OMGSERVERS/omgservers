package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.getMatch;

import com.omgservers.dto.matchmaker.GetMatchShardResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetMatchMethod {
    Uni<GetMatchShardResponse> getMatch(GetMatchShardedRequest request);

    default GetMatchShardResponse getMatch(long timeout, GetMatchShardedRequest request) {
        return getMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
