package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.getMatchMethod;

import com.omgservers.dto.matchmakerModule.GetMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetMatchMethod {
    Uni<GetMatchInternalResponse> getMatch(GetMatchInternalRequest request);

    default GetMatchInternalResponse getMatch(long timeout, GetMatchInternalRequest request) {
        return getMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
