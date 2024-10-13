package com.omgservers.service.service.initializer.impl;

import com.omgservers.service.configuration.ServicePriorityConfiguration;
import com.omgservers.service.event.body.internal.ServiceBootstrapRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.bootstrap.BootstrapService;
import com.omgservers.service.service.event.EventService;
import com.omgservers.service.service.event.dto.SyncEventRequest;
import com.omgservers.service.service.initializer.InitializerService;
import com.omgservers.service.service.initializer.impl.method.InitializeDatabaseSchemaMethod;
import com.omgservers.service.service.initializer.impl.method.InitializeRelayJobMethod;
import com.omgservers.service.service.initializer.impl.method.InitializeSchedulerJobMethod;
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

    final BootstrapService bootstrapService;
    final EventService eventService;

    final InitializeDatabaseSchemaMethod initializeDatabaseSchemaMethod;
    final InitializeSchedulerJobMethod initializeSchedulerJobMethod;
    final InitializeRelayJobMethod initializeRelayJobMethod;

    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    @WithSpan
    void startup(@Observes @Priority(ServicePriorityConfiguration.START_UP_BOOTSTRAP_SERVICE_PRIORITY)
                 final StartupEvent event) {
        initialize()
                .await().indefinitely();
    }

    @Override
    public Uni<Void> initialize() {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> initializeDatabaseSchema())
                .flatMap(voidItem -> initializeRelayJob())
                .flatMap(voidItem -> initializeSchedulerJob())
                .flatMap(voidItem -> requestServiceBootstrap());
    }

    Uni<Void> initializeDatabaseSchema() {
        if (getConfigOperation.getServiceConfig().initialization().schema().enabled()) {
            return initializeDatabaseSchemaMethod.execute()
                    .invoke(voidItem -> log.info("Database schema was initialized"));
        } else {
            log.info("Initialization of database schema is not enabled, skip step");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeRelayJob() {
        if (getConfigOperation.getServiceConfig().initialization().relayJob().enabled()) {
            return bootstrapService.bootstrapRelayJob()
                    .invoke(voidItem -> log.info("Relay job was initialized"));
        } else {
            log.info("Initialization of relay job is not enabled, skip step");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeSchedulerJob() {
        if (getConfigOperation.getServiceConfig().initialization().schedulerJob().enabled()) {
            return bootstrapService.bootstrapSchedulerJob()
                    .invoke(voidItem -> log.info("Scheduler job was initialized"));
        } else {
            log.info("Initialization of scheduler job is not enabled, skip step");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> requestServiceBootstrap() {
        final var eventBody = new ServiceBootstrapRequestedEventBodyModel();
        final var eventModel = eventModelFactory.create(eventBody, "bootstrap/service");

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .replaceWithVoid();
    }
}
