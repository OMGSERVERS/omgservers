package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.createMatchmakerMethod;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.CreateMatchmakerInternalRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateMatchmakerMethod {

    Uni<Void> createMatchmaker(CreateMatchmakerInternalRequest request);

    default void createMatchmaker(long timeout, CreateMatchmakerInternalRequest request) {
        createMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
