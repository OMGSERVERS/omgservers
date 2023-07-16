package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.getMatchmakerMethod;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.GetMatchmakerInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.GetMatchmakerInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetMatchmakerMethod {
    Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerInternalRequest request);

    default GetMatchmakerInternalResponse getMatchmaker(long timeout, GetMatchmakerInternalRequest request) {
        return getMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
