package com.omgservers.service.service.initializer.impl;

import com.omgservers.service.configuration.ServicePriorityConfiguration;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.bootstrap.BootstrapService;
import com.omgservers.service.service.initializer.InitializerService;
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

    final UserModule userModule;

    final BootstrapService bootstrapService;

    final GetConfigOperation getConfigOperation;

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
                .flatMap(voidItem -> initializeServerIndex())
                .flatMap(voidItem -> initializeServiceRoot())
                .flatMap(voidItem -> initializeAdminUser())
                .flatMap(voidItem -> initializeSupportUser())
                .flatMap(voidItem -> initializeRegistryUser())
                .flatMap(voidItem -> initializeBuilderUser())
                .flatMap(voidItem -> initializeServiceUser())
                .flatMap(voidItem -> initializeDefaultPool())
                .flatMap(voidItem -> initializeRelayJob())
                .flatMap(voidItem -> initializeSchedulerJob());
    }

    Uni<Void> initializeDatabaseSchema() {
        if (getConfigOperation.getServiceConfig().bootstrap().schema().enabled()) {
            return bootstrapService.bootstrapDatabaseSchema()
                    .invoke(voidItem -> log.info("Database schema was initialized"));
        } else {
            log.info("Bootstrap of database schema is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeServerIndex() {
        if (getConfigOperation.getServiceConfig().bootstrap().index().enabled()) {
            return bootstrapService.bootstrapServerIndex()
                    .invoke(voidItem -> log.info("Server index was initialized"));
        } else {
            log.info("Bootstrap of server index is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeServiceRoot() {
        if (getConfigOperation.getServiceConfig().bootstrap().root().enabled()) {
            return bootstrapService.bootstrapServiceRoot()
                    .invoke(voidItem -> log.info("Service root was initialized"));
        } else {
            log.info("Bootstrap of service root is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeAdminUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultUsers().enabled()) {
            return bootstrapService.bootstrapAdminUser()
                    .invoke(voidItem -> log.info("Admin user was initialized"));
        } else {
            log.info("Bootstrap of default user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeSupportUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultUsers().enabled()) {
            return bootstrapService.bootstrapSupportUser()
                    .invoke(voidItem -> log.info("Support user was initialized"));
        } else {
            log.info("Bootstrap of default user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeRegistryUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultUsers().enabled()) {
            return bootstrapService.bootstrapRegistryUser()
                    .invoke(voidItem -> log.info("Registry user was initialized"));
        } else {
            log.info("Bootstrap of default user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeBuilderUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultUsers().enabled()) {
            return bootstrapService.bootstrapBuilderUser()
                    .invoke(voidItem -> log.info("Builder user was initialized"));
        } else {
            log.info("Bootstrap of default user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeServiceUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultUsers().enabled()) {
            return bootstrapService.bootstrapServiceUser()
                    .invoke(voidItem -> log.info("Service user was initialized"));
        } else {
            log.info("Bootstrap of default user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeDefaultPool() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultPool().enabled()) {
            return bootstrapService.bootstrapDefaultPool()
                    .invoke(voidItem -> log.info("Default pool was initialized"));
        } else {
            log.info("Bootstrap of default pool is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeRelayJob() {
        if (getConfigOperation.getServiceConfig().bootstrap().relayJob().enabled()) {
            return bootstrapService.bootstrapRelayJob()
                    .invoke(voidItem -> log.info("Relay job was initialized"));
        } else {
            log.info("Bootstrap of relay job is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeSchedulerJob() {
        if (getConfigOperation.getServiceConfig().bootstrap().schedulerJob().enabled()) {
            return bootstrapService.bootstrapSchedulerJob()
                    .invoke(voidItem -> log.info("Scheduler job was initialized"));
        } else {
            log.info("Bootstrap of scheduler job is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }
}
