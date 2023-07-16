package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.createMatchMethod;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.CreateMatchInternalRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateMatchMethod {
    Uni<Void> createMatch(CreateMatchInternalRequest request);

    default void createMatch(long timeout, CreateMatchInternalRequest request) {
        createMatch(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
