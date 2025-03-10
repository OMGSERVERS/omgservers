package com.omgservers.service.service.initializer.impl;

import com.omgservers.service.configuration.ServicePriorityConfiguration;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.initializer.InitializerService;
import com.omgservers.service.service.initializer.impl.method.*;
import com.omgservers.service.service.initializer.impl.method.InitializeEventHandlerJobMethod;
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

    final InitializeEventHandlerJobMethod initializeEventHandlerJobMethod;
    final InitializeDatabaseSchemaMethod initializeDatabaseSchemaMethod;
    final InitializeSchedulerJobMethod initializeSchedulerJobMethod;
    final InitializeBootstrapJobMethod initializeBootstrapJobMethod;
    final InitializeServerIndexMethod initializeServerIndexMethod;

    final GetServiceConfigOperation getServiceConfigOperation;

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
                .flatMap(voidItem -> initializeServerIndex())
                .flatMap(voidItem -> initializeEventHandlerJob())
                .flatMap(voidItem -> initializeSchedulerJob())
                .flatMap(voidItem -> initializeBootstrapJob());
    }

    Uni<Void> initializeDatabaseSchema() {
        if (getServiceConfigOperation.getServiceConfig().initialization().databaseSchema().enabled()) {
            return initializeDatabaseSchemaMethod.execute()
                    .invoke(voidItem -> log.info("The database schema was initialized"));
        } else {
            log.info("Database schema initialization is not enabled, skipping this step");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeServerIndex() {
        if (getServiceConfigOperation.getServiceConfig().initialization().serverIndex().enabled()) {
            return initializeServerIndexMethod.execute()
                    .invoke(voidItem -> log.info("Server index was initialized"));
        } else {
            log.info("Server index initialization is not enabled, skip this step");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeEventHandlerJob() {
        if (getServiceConfigOperation.getServiceConfig().initialization().eventHandlerJob().enabled()) {
            return initializeEventHandlerJobMethod.execute()
                    .invoke(voidItem -> log.info("The event handler job was initialized"));
        } else {
            log.info("Event handler job initialization is not enabled, skipping this step.");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeSchedulerJob() {
        if (getServiceConfigOperation.getServiceConfig().initialization().schedulerJob().enabled()) {
            return initializeSchedulerJobMethod.execute()
                    .invoke(voidItem -> log.info("The scheduler job was initialized."));
        } else {
            log.info("Scheduler job initialization is not enabled, skipping this step");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeBootstrapJob() {
        if (getServiceConfigOperation.getServiceConfig().initialization().bootstrapJob().enabled()) {
            return initializeBootstrapJobMethod.execute()
                    .invoke(voidItem -> log.info("The bootstrap job was initialized."));
        } else {
            log.info("Bootstrap job initialization is not enabled, skipping this step");
            return Uni.createFrom().voidItem();
        }
    }
}
