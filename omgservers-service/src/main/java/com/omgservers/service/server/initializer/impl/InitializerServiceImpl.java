package com.omgservers.service.server.initializer.impl;

import com.omgservers.service.configuration.ServicePriorityConfiguration;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.initializer.InitializerService;
import com.omgservers.service.server.initializer.impl.method.InitializeBootstrapJobMethod;
import com.omgservers.service.server.initializer.impl.method.InitializeEventHandlerJobMethod;
import com.omgservers.service.server.initializer.impl.method.InitializeSchedulerJobMethod;
import com.omgservers.service.server.initializer.impl.method.InitializeServerIndexMethod;
import com.omgservers.service.server.initializer.impl.method.InitializeServerSchemaMethod;
import com.omgservers.service.server.initializer.impl.method.InitializeShardsSchemasMethod;
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
    final InitializeShardsSchemasMethod initializeShardsSchemasMethod;
    final InitializeServerSchemaMethod initializeServerSchemaMethod;
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
                .flatMap(voidItem -> initializeServerSchema())
                .flatMap(voidItem -> initializeServerIndex())
                .flatMap(voidItem -> initializeShardsSchemas())
                .flatMap(voidItem -> initializeEventHandlerJob())
                .flatMap(voidItem -> initializeSchedulerJob())
                .flatMap(voidItem -> initializeBootstrapJob());
    }

    Uni<Void> initializeServerSchema() {
        if (getServiceConfigOperation.getServiceConfig().initialization().databaseSchema().enabled()) {
            return initializeServerSchemaMethod.execute()
                    .invoke(voidItem -> log.info("Server schema initialized"));
        } else {
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeServerIndex() {
        if (getServiceConfigOperation.getServiceConfig().initialization().serverIndex().enabled()) {
            return initializeServerIndexMethod.execute()
                    .invoke(voidItem -> log.info("Server index initialized"));
        } else {
            log.info("Server index initialization disabled");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeShardsSchemas() {
        if (getServiceConfigOperation.getServiceConfig().initialization().databaseSchema().enabled()) {
            return initializeShardsSchemasMethod.execute()
                    .invoke(voidItem -> log.info("Shards schemas initialized"));
        } else {
            log.info("Database schema initialization disabled");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeEventHandlerJob() {
        if (getServiceConfigOperation.getServiceConfig().initialization().eventHandlerJob().enabled()) {
            return initializeEventHandlerJobMethod.execute()
                    .invoke(voidItem -> log.info("Event handler job initialized"));
        } else {
            log.info("Event handler job initialization disabled");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeSchedulerJob() {
        if (getServiceConfigOperation.getServiceConfig().initialization().schedulerJob().enabled()) {
            return initializeSchedulerJobMethod.execute()
                    .invoke(voidItem -> log.info("Scheduler job initialized"));
        } else {
            log.info("Scheduler job initialization disabled");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeBootstrapJob() {
        if (getServiceConfigOperation.getServiceConfig().initialization().bootstrapJob().enabled()) {
            return initializeBootstrapJobMethod.execute()
                    .invoke(voidItem -> log.info("Bootstrap job initialized"));
        } else {
            log.info("Bootstrap job initialization disabled");
            return Uni.createFrom().voidItem();
        }
    }
}
