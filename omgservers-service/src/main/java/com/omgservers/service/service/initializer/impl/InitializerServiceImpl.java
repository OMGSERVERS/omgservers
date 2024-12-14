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
import com.omgservers.service.service.initializer.impl.method.InitializeDispatcherJobMethod;
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
    final InitializeDispatcherJobMethod initializeDispatcherJobMethod;
    final InitializeSchedulerJobMethod initializeSchedulerJobMethod;
    final InitializeRelayJobMethod initializeRelayJobMethod;

    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    @WithSpan
    void startup(@Observes @Priority(ServicePriorityConfiguration.START_UP_BOOTSTRAP_SERVICE_PRIORITY)
                 final StartupEvent event) {
        initialize().await().indefinitely();
    }

    @Override
    public Uni<Void> initialize() {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> initializeDatabaseSchema())
                .flatMap(voidItem -> initializeRelayJob())
                .flatMap(voidItem -> initializeSchedulerJob())
                .flatMap(voidItem -> initializeDispatcherJob())
                .flatMap(voidItem -> requestServiceBootstrap());
    }

    Uni<Void> initializeDatabaseSchema() {
        if (getConfigOperation.getServiceConfig().initialization().databaseSchema().enabled()) {
            return initializeDatabaseSchemaMethod.execute()
                    .invoke(voidItem -> log.info("The database schema was initialized"));
        } else {
            log.debug("Database schema initialization is not enabled, skipping this step");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeRelayJob() {
        if (getConfigOperation.getServiceConfig().initialization().relayJob().enabled()) {
            return initializeRelayJobMethod.execute()
                    .invoke(voidItem -> log.info("The relay job was initialized"));
        } else {
            log.debug("Relay job initialization is not enabled, skipping this step.");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeSchedulerJob() {
        if (getConfigOperation.getServiceConfig().initialization().schedulerJob().enabled()) {
            return initializeSchedulerJobMethod.execute()
                    .invoke(voidItem -> log.info("The scheduler job was initialized."));
        } else {
            log.debug("Scheduler job initialization is not enabled, skipping this step");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeDispatcherJob() {
        if (getConfigOperation.getServiceConfig().initialization().dispatcherJob().enabled()) {
            return initializeDispatcherJobMethod.execute()
                    .invoke(voidItem -> log.info("The dispatcher job was initialized."));
        } else {
            log.debug("Dispatcher job initialization is not enabled, skipping this step");
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
