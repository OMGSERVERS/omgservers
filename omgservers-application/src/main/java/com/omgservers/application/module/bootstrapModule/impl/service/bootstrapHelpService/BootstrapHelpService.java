package com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService;

import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface BootstrapHelpService {

    Uni<Void> bootstrap();

    default void bootstrap(long timeout) {
        bootstrap()
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
