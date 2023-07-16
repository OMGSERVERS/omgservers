package com.omgservers.application.module.userModule.impl.service.userHelpService;

import com.omgservers.application.module.userModule.impl.service.userHelpService.request.RespondClientHelpRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface UserHelpService {

    Uni<Void> respondClient(RespondClientHelpRequest request);

    default void respondClient(long timeout, RespondClientHelpRequest request) {
        respondClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
