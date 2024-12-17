package com.omgservers.service.handler.impl.internal;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.ServiceBootstrapRequestedEventBodyModel;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.bootstrap.BootstrapService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ServiceBootstrapRequestedEventHandlerImpl implements EventHandler {

    final BootstrapService bootstrapService;

    final GetConfigOperation getConfigOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SERVICE_BOOTSTRAP_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (ServiceBootstrapRequestedEventBodyModel) event.getBody();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> initializeServerIndex())
                .flatMap(voidItem -> initializeServiceRoot())
                .flatMap(voidItem -> initializeAdminUser())
                .flatMap(voidItem -> initializeSupportUser())
                .flatMap(voidItem -> initializeRegistryUser())
                .flatMap(voidItem -> initializeBuilderUser())
                .flatMap(voidItem -> initializeServiceUser())
                .flatMap(voidItem -> initializeDefaultPool())
                .onFailure().transform(
                        t -> new ServerSideInternalException(ExceptionQualifierEnum.BOOTSTRAP_FAILED,
                                t.getMessage(),
                                t));
    }

    Uni<Void> initializeServerIndex() {
        if (getConfigOperation.getServiceConfig().bootstrap().index().enabled()) {
            return bootstrapService.bootstrapServerIndex()
                    .invoke(voidItem -> log.debug("Server index was initialized"));
        } else {
            log.debug("Bootstrap of server index is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeServiceRoot() {
        if (getConfigOperation.getServiceConfig().bootstrap().root().enabled()) {
            return bootstrapService.bootstrapServiceRoot()
                    .invoke(voidItem -> log.debug("Service root was initialized"));
        } else {
            log.debug("Bootstrap of service root is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeAdminUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultUsers().enabled()) {
            return bootstrapService.bootstrapAdminUser()
                    .invoke(voidItem -> log.debug("Admin user was initialized"));
        } else {
            log.debug("Bootstrap of default user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeSupportUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultUsers().enabled()) {
            return bootstrapService.bootstrapSupportUser()
                    .invoke(voidItem -> log.debug("Support user was initialized"));
        } else {
            log.debug("Bootstrap of default user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeRegistryUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultUsers().enabled()) {
            return bootstrapService.bootstrapRegistryUser()
                    .invoke(voidItem -> log.debug("Registry user was initialized"));
        } else {
            log.debug("Bootstrap of default user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeBuilderUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultUsers().enabled()) {
            return bootstrapService.bootstrapBuilderUser()
                    .invoke(voidItem -> log.debug("Builder user was initialized"));
        } else {
            log.debug("Bootstrap of default user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeServiceUser() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultUsers().enabled()) {
            return bootstrapService.bootstrapServiceUser()
                    .invoke(voidItem -> log.debug("Service user was initialized"));
        } else {
            log.debug("Bootstrap of default user is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> initializeDefaultPool() {
        if (getConfigOperation.getServiceConfig().bootstrap().defaultPool().enabled()) {
            return bootstrapService.bootstrapDefaultPool()
                    .invoke(voidItem -> log.debug("Default pool was initialized"));
        } else {
            log.debug("Bootstrap of default pool is not enabled, skip operation");
            return Uni.createFrom().voidItem();
        }
    }
}
