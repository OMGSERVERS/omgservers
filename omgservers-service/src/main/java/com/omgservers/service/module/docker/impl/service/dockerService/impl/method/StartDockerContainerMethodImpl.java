package com.omgservers.service.module.docker.impl.service.dockerService.impl.method;

import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.LogConfig;
import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.service.module.docker.impl.service.dockerService.impl.operation.GetDockerDaemonClientOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class StartDockerContainerMethodImpl implements StartDockerContainerMethod {

    final GetDockerDaemonClientOperation getDockerDaemonClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<StartDockerContainerResponse> execute(final StartDockerContainerRequest request) {
        log.debug("Requested, {}", request);

        final var poolServer = request.getPoolServer();
        final var poolContainer = request.getPoolContainer();

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> {
                    final var imageId = poolContainer.getConfig().getImageId();
                    final var containerName = poolContainer.getContainerName();
                    final var environment = poolContainer.getConfig().getEnvironment().entrySet().stream()
                            .map(entry -> entry.getKey() + "=" + entry.getValue())
                            .toList();

                    final var dockerDaemonUri = poolServer.getConfig().getDockerHostConfig().getDockerDaemonUri();
                    final var dockerDaemonClient = getDockerDaemonClientOperation
                            .getClient(dockerDaemonUri);
                    final var dockerNetwork = getConfigOperation.getServiceConfig().runtimes().dockerNetwork();

                    try {
                        // Convert milliseconds -> microseconds
                        final var cpuQuotaInMicroseconds = poolContainer.getConfig()
                                .getCpuLimitInMilliseconds() * 1000L;
                        // Convert megabytes -> bytes
                        final var memoryLimitInBytes = poolContainer.getConfig()
                                .getMemoryLimitInMegabytes() * 1024L * 1024L;

                        final var logConfig = new LogConfig();
                        logConfig.setType(LogConfig.LoggingType.JSON_FILE);
                        logConfig.setConfig(Map.of(
                                "max-size", "10m",
                                "max-file", "8"
                        ));

                        final var hostConfig = HostConfig.newHostConfig()
                                .withLogConfig(logConfig)
                                .withNetworkMode(dockerNetwork)
                                .withCpuQuota(cpuQuotaInMicroseconds)
                                .withMemory(memoryLimitInBytes);

                        final var createContainerResponse = dockerDaemonClient.createContainerCmd(imageId)
                                .withName(containerName)
                                .withEnv(environment)
                                .withHostConfig(hostConfig)
                                .exec();
                        log.info("Docker container was created, " +
                                        "containerName={}, dockerNetwork={}, cpuQuota={}, memoryLimit={}, response={}",
                                containerName,
                                dockerNetwork,
                                cpuQuotaInMicroseconds,
                                memoryLimitInBytes,
                                createContainerResponse);

                        final var inspectContainerResponse = dockerDaemonClient.inspectContainerCmd(containerName)
                                .exec();
                        log.info("Docker container was inspected, response={}", inspectContainerResponse);

                        final var startContainerResponse = dockerDaemonClient.startContainerCmd(containerName)
                                .exec();

                        log.info("Docker container was started, response={}", startContainerResponse);
                        return new StartDockerContainerResponse(Boolean.TRUE);

                    } catch (DockerException e) {
                        log.error("Failed to start docker container, {}:{}", e.getClass().getSimpleName(),
                                e.getMessage());
                        return new StartDockerContainerResponse(Boolean.FALSE);
                    }
                });
    }
}
