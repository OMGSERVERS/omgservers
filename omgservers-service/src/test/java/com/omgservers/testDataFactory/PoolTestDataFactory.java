package com.omgservers.testDataFactory;

import com.omgservers.model.dto.pool.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.pool.SyncPoolRequest;
import com.omgservers.model.dto.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.SyncPoolServerContainerRequest;
import com.omgservers.model.pool.PoolModel;
import com.omgservers.model.poolRequest.PoolRequestConfigModel;
import com.omgservers.model.poolRequest.PoolRequestModel;
import com.omgservers.model.poolServer.PoolServerConfigModel;
import com.omgservers.model.poolServer.PoolServerModel;
import com.omgservers.model.poolServer.PoolServerQualifierEnum;
import com.omgservers.model.poolSeverContainer.PoolServerContainerConfigModel;
import com.omgservers.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.model.root.RootModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.pool.PoolRequestModelFactory;
import com.omgservers.service.factory.pool.PoolServerContainerModelFactory;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.module.pool.impl.service.poolService.testInterface.PoolServiceTestInterface;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
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

    final GetConfigOperation getConfigOperation;

    final PoolServerContainerModelFactory poolServerContainerModelFactory;
    final PoolRequestModelFactory poolRequestModelFactory;
    final PoolServerModelFactory poolServerModelFactory;
    final PoolModelFactory poolModelFactory;

    public PoolModel createDefaultPool(final RootModel root) {
        final var rootId = root.getId();
        final var defaultPoolId = root.getDefaultPoolId();

        try {
            final var getPoolRequest = new GetPoolRequest(defaultPoolId);
            log.info("Default pool was already created, defaultPoolId={}", defaultPoolId);
            return poolService.getPool(getPoolRequest).getPool();
        } catch (ServerSideNotFoundException e) {
            final var pool = poolModelFactory.create(defaultPoolId, rootId);
            final var syncPoolRequest = new SyncPoolRequest(pool);
            poolService.syncPool(syncPoolRequest);
            return pool;
        }
    }

    public PoolModel createPool(final RootModel root) {
        final var rootId = root.getId();

        final var pool = poolModelFactory.create(rootId);
        final var syncPoolRequest = new SyncPoolRequest(pool);
        poolService.syncPool(syncPoolRequest);
        return pool;
    }

    public PoolRequestModel createPoolRequest(final PoolModel pool, final RuntimeModel runtime) {
        final var poolId = pool.getId();
        final var runtimeId = runtime.getId();
        final var config = PoolRequestConfigModel.create();

        final var poolRequest = poolRequestModelFactory.create(poolId, runtimeId, config);
        final var syncPoolRequestRequest = new SyncPoolRequestRequest(poolRequest);
        poolService.syncPoolRequest(syncPoolRequestRequest);
        return poolRequest;
    }

    public PoolServerModel createPoolServer(final PoolModel pool) {
        final var poolId = pool.getId();
        final var config = PoolServerConfigModel.create();
        config.setDockerHostConfig(PoolServerConfigModel.DockerHostConfig.builder()
                .dockerDaemonUri(URI.create("unix:///var/run/docker.sock"))
                .cpuCount(1000)
                .memorySize(2048)
                .maxContainers(16)
                .build());

        final var poolServer = poolServerModelFactory.create(poolId,
                PoolServerQualifierEnum.DOCKER_HOST, config);
        final var syncPoolServerRequest = new SyncPoolServerRequest(poolServer);
        poolService.syncPoolServer(syncPoolServerRequest);
        return poolServer;
    }

    public PoolServerContainerModel createPoolServerContainer(final PoolServerModel poolServer,
                                                              final RuntimeModel runtime) {
        final var poolId = poolServer.getPoolId();
        final var serverId = poolServer.getId();
        final var runtimeId = runtime.getId();

        final var dockerImage = getConfigOperation.getServiceConfig().workers().dockerImage();
        final var config = PoolServerContainerConfigModel.create();
        config.setImageId(dockerImage);
        config.setCpuLimit(1000);
        config.setMemoryLimit(2048);
        config.setEnvironment(new HashMap<>());

        final var poolServerContainer = poolServerContainerModelFactory.create(poolId,
                serverId,
                runtimeId,
                config);
        final var syncPoolServerContainerRequest = new SyncPoolServerContainerRequest(poolServerContainer);
        poolService.syncPoolServerContainer(syncPoolServerContainerRequest);
        return poolServerContainer;
    }
}
