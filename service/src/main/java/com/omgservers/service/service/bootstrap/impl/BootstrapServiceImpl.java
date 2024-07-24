package com.omgservers.service.service.bootstrap.impl;

import com.omgservers.service.configuration.ServicePriorityConfiguration;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.bootstrap.BootstrapService;
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
public class BootstrapServiceImpl implements BootstrapService {

    final SystemModule systemModule;
    final UserModule userModule;

    final GetConfigOperation getConfigOperation;

    @WithSpan
    void startup(@Observes @Priority(ServicePriorityConfiguration.START_UP_BOOTSTRAP_SERVICE_PRIORITY)
                 final StartupEvent event) {
        bootstrap()
                .await().indefinitely();
    }

    @Override
    public Uni<Void> bootstrap() {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> bootstrapDatabaseSchema())
                .flatMap(voidItem -> bootstrapServerIndex())
                .flatMap(voidItem -> bootstrapServiceRoot())
                .flatMap(voidItem -> bootstrapAdminUser())
                .flatMap(voidItem -> bootstrapSupportUser())
                .flatMap(voidItem -> bootstrapRouterUser())
                .flatMap(voidItem -> bootstrapDefaultPool())
                .flatMap(voidItem -> bootstrapDockerHost())
                .flatMap(voidItem -> bootstrapRelayJob())
                .flatMap(voidItem -> bootstrapSchedulerJob());
    }

    Uni<Void> bootstrapDatabaseSchema() {
        if (getConfigOperation.getServiceConfig().bootstrap().schema().enabled()) {
            return systemModule.getBootstrapService().bootstrapDatabaseSchema()
                    .invoke(voidItem -> log.info("Database schema was initialized"));
        } else {
            log.info("Bootstrap of database schema is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapServerIndex() {
        if (getConfigOperation.getServiceConfig().bootstrap().index().enabled()) {
            return systemModule.getBootstrapService().bootstrapServerIndex()
                    .invoke(voidItem -> log.info("Server index was initialized"));
        } else {
            log.info("Bootstrap of server index is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapServiceRoot() {
        if (getConfigOperation.getServiceConfig().bootstrap().root().enabled()) {
            return systemModule.getBootstrapService().bootstrapServiceRoot()
                    .invoke(voidItem -> log.info("Service root was initialized"));
        } else {
            log.info("Bootstrap of service root is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapAdminUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().admin().enabled()) {
            return systemModule.getBootstrapService().bootstrapAdminUser()
                    .invoke(voidItem -> log.info("Admin user was initialized"));
        } else {
            log.info("Bootstrap of admin user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapSupportUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().support().enabled()) {
            return systemModule.getBootstrapService().bootstrapSupportUser()
                    .invoke(voidItem -> log.info("Support user was initialized"));
        } else {
            log.info("Bootstrap of support user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapRouterUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().routerUser().enabled()) {
            return systemModule.getBootstrapService().bootstrapRouterUser()
                    .invoke(voidItem -> log.info("Router user was initialized"));
        } else {
            log.info("Bootstrap of router user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapDefaultPool() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultPool().enabled()) {
            return systemModule.getBootstrapService().bootstrapDefaultPool()
                    .invoke(voidItem -> log.info("Default pool was initialized"));
        } else {
            log.info("Bootstrap of default pool is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapDockerHost() {
        if (getConfigOperation.getServiceConfig().bootstrap().dockerHost().enabled()) {
            return systemModule.getBootstrapService().bootstrapDockerHost()
                    .invoke(voidItem -> log.info("Docker host was initialized"));
        } else {
            log.info("Bootstrap of docker host is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapRelayJob() {
        if (getConfigOperation.getServiceConfig().bootstrap().relayJob().enabled()) {
            return systemModule.getBootstrapService().bootstrapRelayJob()
                    .invoke(voidItem -> log.info("Relay job was initialized"));
        } else {
            log.info("Bootstrap of relay job is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapSchedulerJob() {
        if (getConfigOperation.getServiceConfig().bootstrap().schedulerJob().enabled()) {
            return systemModule.getBootstrapService().bootstrapSchedulerJob()
                    .invoke(voidItem -> log.info("Scheduler job was initialized"));
        } else {
            log.info("Bootstrap of scheduler job is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }
}
