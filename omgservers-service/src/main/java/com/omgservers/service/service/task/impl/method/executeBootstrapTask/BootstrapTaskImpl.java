package com.omgservers.service.service.task.impl.method.executeBootstrapTask;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.getServiceConfig.GetServiceConfigOperation;
import com.omgservers.service.service.bootstrap.BootstrapService;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultPoolResponse;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultUserResponse;
import com.omgservers.service.service.bootstrap.dto.BootstrapRootEntityRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapRootEntityResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@ApplicationScoped
public class BootstrapTaskImpl {

    final BootstrapService bootstrapService;

    final GetServiceConfigOperation getServiceConfigOperation;

    final AtomicBoolean fullyFinished;

    public BootstrapTaskImpl(final BootstrapService bootstrapService,
                             final GetServiceConfigOperation getServiceConfigOperation) {
        this.bootstrapService = bootstrapService;
        this.getServiceConfigOperation = getServiceConfigOperation;
        this.fullyFinished = new AtomicBoolean();
    }

    public Uni<Boolean> execute() {
        if (!getServiceConfigOperation.getServiceConfig().bootstrap().enabled()) {
            log.debug("Bootstrap is not enabled, skip operation");
            return Uni.createFrom().item(Boolean.TRUE);
        }

        if (fullyFinished.get()) {
            return Uni.createFrom().item(Boolean.TRUE);
        }

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> bootstrapServiceRoot())
                .flatMap(voidItem -> bootstrapAdminUser())
                .flatMap(voidItem -> bootstrapSupportUser())
                .flatMap(voidItem -> bootstrapRegistryUser())
                .flatMap(voidItem -> bootstrapBuilderUser())
                .flatMap(voidItem -> bootstrapServiceUser())
                .flatMap(voidItem -> bootstrapDispatcherUser())
                .flatMap(voidItem -> bootstrapDefaultPool())
                .invoke(voidItem -> fullyFinished.set(true))
                .replaceWith(Boolean.TRUE)
                .onFailure().transform(
                        t -> new ServerSideInternalException(ExceptionQualifierEnum.BOOTSTRAP_FAILED,
                                t.getMessage(),
                                t));
    }

    Uni<Boolean> bootstrapServiceRoot() {
        return bootstrapService.execute(new BootstrapRootEntityRequest())
                .map(BootstrapRootEntityResponse::getCreated);
    }

    Uni<Boolean> bootstrapAdminUser() {
        final var alias = getServiceConfigOperation.getServiceConfig().bootstrap().adminUser().alias();
        final var password = getServiceConfigOperation.getServiceConfig().bootstrap().adminUser().password();
        if (password.isPresent()) {
            final var request = new BootstrapDefaultUserRequest(alias, password.get(), UserRoleEnum.ADMIN);
            return bootstrapService.execute(request)
                    .map(BootstrapDefaultUserResponse::getCreated);
        } else {
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> bootstrapSupportUser() {
        final var alias = getServiceConfigOperation.getServiceConfig().bootstrap().supportUser().alias();
        final var password = getServiceConfigOperation.getServiceConfig().bootstrap().supportUser().password();
        if (password.isPresent()) {
            final var request = new BootstrapDefaultUserRequest(alias, password.get(), UserRoleEnum.SUPPORT);
            return bootstrapService.execute(request)
                    .map(BootstrapDefaultUserResponse::getCreated);
        } else {
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> bootstrapRegistryUser() {
        final var alias = getServiceConfigOperation.getServiceConfig().bootstrap().registryUser().alias();
        final var password = getServiceConfigOperation.getServiceConfig().bootstrap().registryUser().password();
        if (password.isPresent()) {
            final var request = new BootstrapDefaultUserRequest(alias, password.get(), UserRoleEnum.REGISTRY);
            return bootstrapService.execute(request)
                    .map(BootstrapDefaultUserResponse::getCreated);
        } else {
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> bootstrapBuilderUser() {
        final var alias = getServiceConfigOperation.getServiceConfig().bootstrap().builderUser().alias();
        final var password = getServiceConfigOperation.getServiceConfig().bootstrap().builderUser().password();
        if (password.isPresent()) {
            final var request = new BootstrapDefaultUserRequest(alias, password.get(), UserRoleEnum.BUILDER);
            return bootstrapService.execute(request)
                    .map(BootstrapDefaultUserResponse::getCreated);
        } else {
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> bootstrapServiceUser() {
        final var alias = getServiceConfigOperation.getServiceConfig().bootstrap().serviceUser().alias();
        final var password = getServiceConfigOperation.getServiceConfig().bootstrap().serviceUser().password();
        if (password.isPresent()) {
            final var request = new BootstrapDefaultUserRequest(alias, password.get(), UserRoleEnum.SERVICE);
            return bootstrapService.execute(request)
                    .map(BootstrapDefaultUserResponse::getCreated);
        } else {
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> bootstrapDispatcherUser() {
        final var alias = getServiceConfigOperation.getServiceConfig().bootstrap().dispatcherUser().alias();
        final var password = getServiceConfigOperation.getServiceConfig().bootstrap().dispatcherUser().password();
        if (password.isPresent()) {
            final var request = new BootstrapDefaultUserRequest(alias, password.get(), UserRoleEnum.DISPATCHER);
            return bootstrapService.execute(request)
                    .map(BootstrapDefaultUserResponse::getCreated);
        } else {
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> bootstrapDefaultPool() {
        return bootstrapService.execute(new BootstrapDefaultPoolRequest())
                .map(BootstrapDefaultPoolResponse::getCreated);
    }
}
