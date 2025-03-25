package com.omgservers.testDataFactory;

import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolRequest.PoolRequestConfigDto;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerConfigDto;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolServer.PoolServerQualifierEnum;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerConfigDto;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.poolContainer.SyncPoolContainerRequest;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.pool.PoolContainerModelFactory;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.pool.PoolRequestModelFactory;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.shard.pool.service.testInterface.PoolServiceTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.HashMap;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolTestDataFactory {

    final PoolServiceTestInterface poolService;

    final GetServiceConfigOperation getServiceConfigOperation;

    final PoolContainerModelFactory poolContainerModelFactory;
    final PoolRequestModelFactory poolRequestModelFactory;
    final PoolServerModelFactory poolServerModelFactory;
    final PoolModelFactory poolModelFactory;

    final GenerateIdOperation generateIdOperation;

    public PoolModel createDefaultPool() {
        final var defaultPoolId = generateIdOperation.generateId();

        try {
            final var getPoolRequest = new GetPoolRequest(defaultPoolId);
            log.info("Default pool was already created, defaultPoolId={}", defaultPoolId);
            return poolService.execute(getPoolRequest).getPool();
        } catch (ServerSideNotFoundException e) {
            final var pool = poolModelFactory.create(defaultPoolId);
            final var syncPoolRequest = new SyncPoolRequest(pool);
            poolService.execute(syncPoolRequest);
            return pool;
        }
    }

    public PoolModel createPool() {
        final var pool = poolModelFactory.create();
        final var syncPoolRequest = new SyncPoolRequest(pool);
        poolService.execute(syncPoolRequest);
        return pool;
    }

    public PoolRequestModel createPoolRequest(final PoolModel pool, final RuntimeModel runtime) {
        final var poolId = pool.getId();
        final var runtimeId = runtime.getId();
        final var runtimeQualifier = runtime.getQualifier();
        final var config = PoolRequestConfigDto.create();

        final var poolRequest = poolRequestModelFactory.create(poolId,
                runtimeId,
                runtimeQualifier,
                config);
        final var syncPoolRequestRequest = new SyncPoolRequestRequest(poolRequest);
        poolService.execute(syncPoolRequestRequest);
        return poolRequest;
    }

    public PoolServerModel createPoolServer(final PoolModel pool) {
        final var poolId = pool.getId();
        final var config = PoolServerConfigDto.create();
        config.setDockerHostConfig(PoolServerConfigDto.DockerHostConfig.builder()
                .dockerDaemonUri(URI.create("unix:///var/run/docker.sock"))
                .cpuCount(1000)
                .memorySize(2048)
                .maxContainers(16)
                .build());

        final var poolServer = poolServerModelFactory.create(poolId,
                PoolServerQualifierEnum.DOCKER_HOST, config);
        final var syncPoolServerRequest = new SyncPoolServerRequest(poolServer);
        poolService.execute(syncPoolServerRequest);
        return poolServer;
    }

    public PoolContainerModel createPoolContainer(final PoolServerModel poolServer,
                                                  final RuntimeModel runtime) {
        final var poolId = poolServer.getPoolId();
        final var serverId = poolServer.getId();
        final var runtimeId = runtime.getId();
        final var runtimeQualifier = runtime.getQualifier();

        final var dockerImage = "ubuntu:latest";
        final var config = PoolContainerConfigDto.create();
        config.setImageId(dockerImage);
        config.setCpuLimitInMilliseconds(100L);
        config.setMemoryLimitInMegabytes(200L);
        config.setEnvironment(new HashMap<>());

        final var poolContainer = poolContainerModelFactory.create(poolId,
                serverId,
                runtimeId,
                runtimeQualifier,
                config);
        final var syncPoolContainerRequest = new SyncPoolContainerRequest(poolContainer);
        poolService.execute(syncPoolContainerRequest);
        return poolContainer;
    }
}
