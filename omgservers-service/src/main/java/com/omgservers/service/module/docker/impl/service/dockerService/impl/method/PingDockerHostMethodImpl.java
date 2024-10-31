package com.omgservers.service.module.docker.impl.service.dockerService.impl.method;

import com.omgservers.schema.module.docker.PingDockerHostRequest;
import com.omgservers.schema.module.docker.PingDockerHostResponse;
import com.omgservers.service.module.docker.impl.service.dockerService.impl.operation.GetDockerDaemonClientOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class PingDockerHostMethodImpl implements PingDockerHostMethod {

    final GetDockerDaemonClientOperation getDockerDaemonClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<PingDockerHostResponse> execute(final PingDockerHostRequest request) {
        log.debug("Requested, {}", request);

        final var poolServer = request.getPoolServer();

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> {
                    final var dockerDaemonUri = poolServer.getConfig().getDockerHostConfig().getDockerDaemonUri();
                    final var dockerClient = getDockerDaemonClientOperation.getClient(dockerDaemonUri);

                    try {
                        dockerClient.pingCmd().exec();
                        return Boolean.TRUE;
                    } catch (Exception e) {
                        log.error("Docker host couldn't be reached, dockerDaemonUri={}, {}:{}",
                                dockerDaemonUri, e.getClass().getSimpleName(), e.getMessage());
                        return Boolean.FALSE;
                    }
                })
                .map(PingDockerHostResponse::new);
    }
}
