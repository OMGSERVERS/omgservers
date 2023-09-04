package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.getMatchClient;

import com.omgservers.dto.matchmaker.GetMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchClientShardedResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetMatchClientMethod {
    Uni<GetMatchClientShardedResponse> getMatchClient(GetMatchClientShardedRequest request);

    default GetMatchClientShardedResponse getMatchClient(long timeout, GetMatchClientShardedRequest request) {
        return getMatchClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
