package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.getMatchmakerMethod;

import com.omgservers.dto.matchmakerModule.GetMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetMatchmakerMethod {
    Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerRoutedRequest request);

    default GetMatchmakerInternalResponse getMatchmaker(long timeout, GetMatchmakerRoutedRequest request) {
        return getMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
