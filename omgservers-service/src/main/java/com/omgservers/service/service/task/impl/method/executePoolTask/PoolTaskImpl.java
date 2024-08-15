package com.omgservers.service.service.task.impl.method.executePoolTask;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerConfigModel;
import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersRequest;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.pool.PoolServerContainerModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolTaskImpl {

    final PoolModule poolModule;

    final GetConfigOperation getConfigOperation;

    final PoolServerContainerModelFactory poolServerContainerModelFactory;
    final EventModelFactory eventModelFactory;

    public Uni<Boolean> executeTask(final Long poolId) {
        return getPool(poolId)
                .flatMap(pool -> handlePool(pool)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<PoolModel> getPool(final Long id) {
        final var request = new GetPoolRequest(id);
        return poolModule.getPoolService().getPool(request)
                .map(GetPoolResponse::getPool);
    }

    Uni<Void> handlePool(final PoolModel pool) {
        final var poolId = pool.getId();
        return viewPoolRequests(poolId)
                .flatMap(poolRequests -> viewPoolServers(poolId)
                        .flatMap(poolServers -> viewPoolServerContainers(poolId)
                                .flatMap(poolServerContainers -> Multi.createFrom().iterable(poolRequests)
                                        .onItem().transformToUniAndConcatenate(poolRequest -> {
                                            if (poolServers.isEmpty()) {
                                                log.warn("server was not selected, poolId={}", poolId);
                                                return Uni.createFrom().voidItem();
                                            }

                                            // TODO: implement smart logic

                                            final var selectedServer = poolServers.get(0);
                                            return syncPoolServerContainer(poolId, selectedServer.getId(), poolRequest);
                                        })
                                        .collect().asList()
                                        .replaceWithVoid()
                                )
                        )
                        .flatMap(voidItem -> Multi.createFrom().iterable(poolRequests)
                                .onItem().transformToUniAndConcatenate(this::deletePoolRequest)
                                .collect().asList()
                                .replaceWithVoid())
                );
    }

    Uni<List<PoolRequestModel>> viewPoolRequests(final Long poolId) {
        final var request = new ViewPoolRequestsRequest(poolId);
        return poolModule.getPoolService().viewPoolRequests(request)
                .map(ViewPoolRequestsResponse::getPoolRequests);
    }

    Uni<List<PoolServerModel>> viewPoolServers(final Long poolId) {
        final var request = new ViewPoolServersRequest(poolId);
        return poolModule.getPoolService().viewPoolServers(request)
                .map(ViewPoolServerResponse::getPoolServers);
    }

    Uni<List<PoolServerContainerModel>> viewPoolServerContainers(final Long poolId) {
        final var request = new ViewPoolServerContainersRequest(poolId, null);
        return poolModule.getPoolService().viewPoolServerContainers(request)
                .map(ViewPoolServerContainersResponse::getPoolServerContainers);
    }

    Uni<Boolean> syncPoolServerContainer(final Long poolId,
                                         final Long serverId,
                                         final PoolRequestModel poolRequest) {
        final var runtimeId = poolRequest.getRuntimeId();
        final var config = PoolServerContainerConfigModel.create();
        config.setImageId(poolRequest.getConfig().getServerContainerConfig().getImageId());
        config.setCpuLimitInMilliseconds(poolRequest.getConfig()
                .getServerContainerConfig().getCpuLimitInMilliseconds());
        config.setMemoryLimitInMegabytes(poolRequest.getConfig()
                .getServerContainerConfig().getMemoryLimitInMegabytes());
        config.setEnvironment(poolRequest.getConfig().getServerContainerConfig().getEnvironment());
        final var poolServerContainer = poolServerContainerModelFactory.create(poolId, serverId, runtimeId, config);
        final var request = new SyncPoolServerContainerRequest(poolServerContainer);
        return poolModule.getPoolService().syncPoolServerContainer(request)
                .map(SyncPoolServerContainerResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", poolServerContainer, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Boolean> deletePoolRequest(final PoolRequestModel poolRequest) {
        final var poolId = poolRequest.getPoolId();
        final var id = poolRequest.getId();
        final var request = new DeletePoolRequestRequest(poolId, id);
        return poolModule.getPoolService().deletePoolRequest(request)
                .map(DeletePoolRequestResponse::getDeleted);
    }
}
