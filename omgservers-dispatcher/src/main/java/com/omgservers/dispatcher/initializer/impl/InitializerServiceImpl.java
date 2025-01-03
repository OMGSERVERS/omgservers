package com.omgservers.dispatcher.initializer.impl;

import com.omgservers.dispatcher.configuration.StartUpPriorityConfiguration;
import com.omgservers.dispatcher.initializer.InitializerService;
import com.omgservers.dispatcher.initializer.impl.method.InitializeExpiredConnectionsHandlerJobMethod;
import com.omgservers.dispatcher.initializer.impl.method.InitializeRefreshDispatcherTokenJobMethod;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InitializerServiceImpl implements InitializerService {

    final InitializeExpiredConnectionsHandlerJobMethod initializeExpiredConnectionsHandlerJobMethod;
    final InitializeRefreshDispatcherTokenJobMethod initializeRefreshDispatcherTokenJobMethod;

    @WithSpan
    void startup(@Observes @Priority(StartUpPriorityConfiguration
            .START_UP_INITIALIZER_PRIORITY) final StartupEvent event) {
        initialize().await().indefinitely();
    }

    @Override
    public Uni<Void> initialize() {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> initializeExpiredConnectionsHandlerJob())
                .flatMap(voidItem -> initializeRefreshDispatcherTokenJob());
    }

    Uni<Void> initializeExpiredConnectionsHandlerJob() {
        return initializeExpiredConnectionsHandlerJobMethod.execute()
                .invoke(voidItem -> log.info("The expired connections handler job was initialized."));
    }

    Uni<Void> initializeRefreshDispatcherTokenJob() {
        return initializeRefreshDispatcherTokenJobMethod.execute()
                .invoke(voidItem -> log.info("The refresh dispatcher token job was initialized."));
    }
}
