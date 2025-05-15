package com.omgservers.service.shard.docker.impl.service.dockerService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.docker.PingDockerHostRequest;
import com.omgservers.schema.shard.docker.PingDockerHostResponse;
import com.omgservers.service.operation.docker.GetDockerDaemonClientOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
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
    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Uni<PingDockerHostResponse> execute(final ShardModel shardModel,
                                               final PingDockerHostRequest request) {
        log.debug("{}", request);

        final var poolServer = request.getPoolServer();

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> {
                    final var dockerDaemonUri = poolServer.getConfig().getDockerHostConfig().getDockerDaemonUri();
                    final var dockerClient = getDockerDaemonClientOperation.execute(dockerDaemonUri);

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
