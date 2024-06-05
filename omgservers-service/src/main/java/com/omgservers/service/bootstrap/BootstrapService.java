package com.omgservers.service.bootstrap;

import com.omgservers.service.configuration.ServicePriorityConfiguration;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.system.IndexModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
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
public class BootstrapService {

    final SystemModule systemModule;
    final UserModule userModule;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;
    final EventModelFactory eventModelFactory;
    final UserModelFactory userModelFactory;

    @WithSpan
    void startup(@Observes @Priority(ServicePriorityConfiguration.START_UP_BOOTSTRAP_SERVICE_PRIORITY)
                 final StartupEvent event) {
        Uni.createFrom().voidItem()
                .flatMap(voidItem -> bootstrapSchema())
                .flatMap(voidItem -> bootstrapIndex())
                .flatMap(voidItem -> bootstrapRoot())
                .flatMap(voidItem -> bootstrapAdmin())
                .flatMap(voidItem -> bootstrapDefaultPool())
                .flatMap(voidItem -> bootstrapDockerHost())
                .flatMap(voidItem -> bootstrapRelay())
                .await().indefinitely();
    }

    Uni<Void> bootstrapSchema() {
        if (getConfigOperation.getServiceConfig().bootstrap().schema().enabled()) {
            return systemModule.getBootstrapService().bootstrapSchema()
                    .invoke(voidItem -> log.info("Schema was initialized"));
        } else {
            log.info("Bootstrap schema is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapIndex() {
        if (getConfigOperation.getServiceConfig().bootstrap().index().enabled()) {
            return systemModule.getBootstrapService().bootstrapIndex()
                    .invoke(voidItem -> log.info("Index was initialized"));
        } else {
            log.info("Bootstrap index is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapRoot() {
        if (getConfigOperation.getServiceConfig().bootstrap().root().enabled()) {
            return systemModule.getBootstrapService().bootstrapRoot()
                    .invoke(voidItem -> log.info("Root was initialized"));
        } else {
            log.info("Bootstrap root is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapAdmin() {
        if (getConfigOperation.getServiceConfig().bootstrap().admin().enabled()) {
            return systemModule.getBootstrapService().bootstrapAdmin()
                    .invoke(voidItem -> log.info("Admin was initialized"));
        } else {
            log.info("Bootstrap admin is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapDefaultPool() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultPool().enabled()) {
            return systemModule.getBootstrapService().bootstrapDefaultPool()
                    .invoke(voidItem -> log.info("Default pool was initialized"));
        } else {
            log.info("Bootstrap default pool is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapDockerHost() {
        if (getConfigOperation.getServiceConfig().bootstrap().dockerHost().enabled()) {
            return systemModule.getBootstrapService().bootstrapDockerHost()
                    .invoke(voidItem -> log.info("Docker host was initialized"));
        } else {
            log.info("Bootstrap docker host is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> bootstrapRelay() {
        if (getConfigOperation.getServiceConfig().bootstrap().relayJob().enabled()) {
            return systemModule.getBootstrapService().bootstrapRelay()
                    .invoke(voidItem -> log.info("Relay was initialized"));
        } else {
            log.info("Bootstrap relay is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }
}
