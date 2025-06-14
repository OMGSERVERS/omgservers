package com.omgservers.service.server.task.impl.method.executeBootstrapTask;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.bootstrap.BootstrapService;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolResponse;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserResponse;
import com.omgservers.service.server.task.Task;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class BootstrapTaskImpl implements Task<BootstrapTaskArguments> {

    final BootstrapService bootstrapService;

    final GetServiceConfigOperation getServiceConfigOperation;

    public Uni<Boolean> execute(final BootstrapTaskArguments taskArguments) {
        log.info("Started bootstrap task");

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> bootstrapAdminUser())
                .flatMap(voidItem -> bootstrapSupportUser())
                .flatMap(voidItem -> bootstrapServiceUser())
                .flatMap(voidItem -> bootstrapConnectorUser())
                .flatMap(voidItem -> bootstrapDefaultPool())
                // TRUE - task is fully finished, job can be unscheduled
                .replaceWith(Boolean.TRUE)
                .onFailure().transform(
                        t -> new ServerSideInternalException(ExceptionQualifierEnum.BOOTSTRAP_FAILED,
                                t.getMessage(),
                                t));
    }

    Uni<Boolean> bootstrapAdminUser() {
        final var alias = getServiceConfigOperation.getServiceConfig().bootstrap().adminUser().alias();
        final var password = getServiceConfigOperation.getServiceConfig().bootstrap().adminUser().password();
        if (password.isPresent()) {
            final var request = new BootstrapDefaultUserRequest(alias, password.get(), UserRoleEnum.ADMIN);
            return bootstrapService.execute(request)
                    .map(BootstrapDefaultUserResponse::getCreated)
                    .invoke(created -> {
                        if (created) {
                            log.info("Admin user \"{}\" created", alias);
                        } else {
                            log.info("Admin user \"{}\" already created", alias);
                        }
                    });
        } else {
            log.warn("Default admin user password is not set, user won't be created");
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> bootstrapSupportUser() {
        final var alias = getServiceConfigOperation.getServiceConfig().bootstrap().supportUser().alias();
        final var password = getServiceConfigOperation.getServiceConfig().bootstrap().supportUser().password();
        if (password.isPresent()) {
            final var request = new BootstrapDefaultUserRequest(alias, password.get(), UserRoleEnum.SUPPORT);
            return bootstrapService.execute(request)
                    .map(BootstrapDefaultUserResponse::getCreated)
                    .invoke(created -> {
                        if (created) {
                            log.info("Support user \"{}\" created", alias);
                        } else {
                            log.info("Support user \"{}\" already created", alias);
                        }
                    });
        } else {
            log.warn("Default support user password is not set, user won't be created");
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> bootstrapServiceUser() {
        final var alias = getServiceConfigOperation.getServiceConfig().bootstrap().serviceUser().alias();
        final var password = getServiceConfigOperation.getServiceConfig().bootstrap().serviceUser().password();
        if (password.isPresent()) {
            final var request = new BootstrapDefaultUserRequest(alias, password.get(), UserRoleEnum.SERVICE);
            return bootstrapService.execute(request)
                    .map(BootstrapDefaultUserResponse::getCreated)
                    .invoke(created -> {
                        if (created) {
                            log.info("Service user \"{}\" created", alias);
                        } else {
                            log.info("Service user \"{}\" already created", alias);
                        }
                    });
        } else {
            log.warn("Default service user password is not set, user won't be created");
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> bootstrapConnectorUser() {
        final var alias = getServiceConfigOperation.getServiceConfig().bootstrap().connectorUser().alias();
        final var password = getServiceConfigOperation.getServiceConfig().bootstrap().connectorUser().password();
        if (password.isPresent()) {
            final var request = new BootstrapDefaultUserRequest(alias, password.get(), UserRoleEnum.CONNECTOR);
            return bootstrapService.execute(request)
                    .map(BootstrapDefaultUserResponse::getCreated)
                    .invoke(created -> {
                        if (created) {
                            log.info("Connector user \"{}\" created", alias);
                        } else {
                            log.info("Connector user \"{}\" already created", alias);
                        }
                    });
        } else {
            log.warn("Default connector user password is not set, user won't be created");
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> bootstrapDefaultPool() {
        return bootstrapService.execute(new BootstrapDefaultPoolRequest())
                .map(BootstrapDefaultPoolResponse::getCreated)
                .invoke(created -> {
                    if (created) {
                        log.info("Default pool created");
                    }
                });
    }
}
