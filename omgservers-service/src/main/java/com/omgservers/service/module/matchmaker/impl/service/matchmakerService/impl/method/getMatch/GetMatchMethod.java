package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatch;

import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetMatchMethod {
    Uni<GetMatchResponse> getMatch(GetMatchRequest request);

    default GetMatchResponse getMatch(long timeout, GetMatchRequest request) {
        return getMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
