package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method;

import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import com.omgservers.service.operation.docker.GetDockerDaemonClientOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PingDockerHostMethodImpl implements PingDockerHostMethod {

    final GetDockerDaemonClientOperation getDockerDaemonClientOperation;
    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Uni<PingDockerHostAdminResponse> execute(final PingDockerHostAdminRequest request) {
        log.info("Requested, {}", request);

        final var dockerDaemonUri = request.getDockerDaemonUri();
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    final var dockerDaemonClient = getDockerDaemonClientOperation
                            .execute(dockerDaemonUri);

                    final var response = dockerDaemonClient.pingCmd().exec();
                    log.info("The Docker host \"{}\" was successfully pinged", dockerDaemonUri);
                })
                .replaceWith(new PingDockerHostAdminResponse(Boolean.TRUE));
    }
}
