package com.omgservers.service;

import com.omgservers.service.server.initializer.InitializerService;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ApplicationInitializer {

    final InitializerService initializerService;

    @WithSpan
    void startup(@Observes final StartupEvent event) {
        initializerService.initialize()
                .await().indefinitely();
    }
}
