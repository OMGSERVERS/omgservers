package com.omgservers.service.shard.docker.impl.service.dockerService.impl.method;

import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.LogConfig;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.docker.StartDockerContainerRequest;
import com.omgservers.schema.shard.docker.StartDockerContainerResponse;
import com.omgservers.service.operation.docker.GetDockerDaemonClientOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class StartDockerContainerMethodImpl implements StartDockerContainerMethod {

    final GetDockerDaemonClientOperation getDockerDaemonClientOperation;
    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Uni<StartDockerContainerResponse> execute(final ShardModel shardModel,
                                                     final StartDockerContainerRequest request) {
        log.debug("{}", request);

        final var poolServer = request.getPoolServer();
        final var poolContainer = request.getPoolContainer();

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> {
                    final var registryURI = getServiceConfigOperation.getServiceConfig().registry().uri();
                    final var imageId = poolContainer.getConfig().getImageId();
                    final var containerName = poolContainer.getContainerName();
                    final var environment = poolContainer.getConfig().getEnvironment().entrySet().stream()
                            .map(entry -> entry.getKey().getVariable() + "=" + entry.getValue())
                            .toList();
                    final var labels = poolContainer.getConfig().getLabels()
                            .entrySet().stream()
                            .collect(Collectors.toMap(entry -> entry.getKey().getQualifier(),
                                    Map.Entry::getValue));

                    final var dockerDaemonUri = poolServer.getConfig().getDockerHostConfig().getDockerDaemonUri();
                    final var dockerDaemonClient = getDockerDaemonClientOperation
                            .execute(dockerDaemonUri);
                    final var dockerNetwork = getServiceConfigOperation.getServiceConfig().runtime().dockerNetwork();

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

                        final String imageUri;
                        if (registryURI.getPort() != -1) {
                            imageUri = registryURI.getHost() + ":" + registryURI.getPort() + "/" + imageId;
                        } else {
                            imageUri = registryURI.getHost() + "/" + imageId;
                        }

                        log.debug("Pull image, imageUri={}, dockerDaemonUri={}", imageUri, dockerDaemonUri);

                        final boolean pullCompleted;
                        try {
                            pullCompleted = dockerDaemonClient.pullImageCmd(imageUri).start()
                                    .awaitCompletion(1, TimeUnit.MINUTES);
                        } catch (InterruptedException e) {
                            log.error("Failed to pull docker image, dockerDaemonUri={}, {}:{}", dockerDaemonUri,
                                    e.getClass().getSimpleName(),
                                    e.getMessage());
                            return new StartDockerContainerResponse(Boolean.FALSE);
                        }

                        if (!pullCompleted) {
                            log.error("Failed to pull docker image, waiting time elapsed, {}", dockerDaemonUri);
                            return new StartDockerContainerResponse(Boolean.FALSE);
                        }

                        log.debug("The image pull has finished, imageUri={}, dockerDaemonUri={}",
                                imageUri, dockerDaemonUri);

                        try {
                            dockerDaemonClient.removeContainerCmd(containerName).exec();
                            log.debug("Container was removed before creation a new one, " +
                                    "containerName={}, dockerDaemonUri={}", containerName, dockerDaemonUri);
                        } catch (DockerException e) {
                            log.debug("Container was not removed before creation a new one, " +
                                            "containerName={}, dockerDaemonUri={}, {}",
                                    containerName, dockerDaemonUri, e.getMessage());
                        }

                        log.debug("Create container, imageUri={}, dockerDaemonUri={}", imageUri, dockerDaemonUri);

                        final var createContainerResponse = dockerDaemonClient
                                .createContainerCmd(imageUri)
                                .withName(containerName)
                                .withEnv(environment)
                                .withHostConfig(hostConfig)
                                .withLabels(labels)
                                .exec();

                        log.debug("Docker container was created, " +
                                        "containerName={}, dockerNetwork={}, cpuQuota={}, memoryLimit={}, dockerDaemonUri={}, response={}",
                                containerName,
                                dockerNetwork,
                                cpuQuotaInMicroseconds,
                                memoryLimitInBytes,
                                dockerDaemonUri,
                                createContainerResponse);

                        final var inspectContainerResponse = dockerDaemonClient.inspectContainerCmd(containerName)
                                .exec();
                        log.debug("Docker container was inspected, response={}", inspectContainerResponse);

                        final var startContainerResponse = dockerDaemonClient.startContainerCmd(containerName)
                                .exec();

                        log.info("The docker container \"{}\" was started on the server {}",
                                containerName, dockerDaemonUri);

                        return new StartDockerContainerResponse(Boolean.TRUE);

                    } catch (DockerException e) {
                        log.error("Failed to start docker container, {}:{}", e.getClass().getSimpleName(),
                                e.getMessage());
                        return new StartDockerContainerResponse(Boolean.FALSE);
                    }
                });
    }
}
