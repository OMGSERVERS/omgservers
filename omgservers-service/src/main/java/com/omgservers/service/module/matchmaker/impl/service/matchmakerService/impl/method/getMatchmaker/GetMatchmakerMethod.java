package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmaker;

import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetMatchmakerMethod {
    Uni<GetMatchmakerResponse> getMatchmaker(GetMatchmakerRequest request);

    default GetMatchmakerResponse getMatchmaker(long timeout, GetMatchmakerRequest request) {
        return getMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
