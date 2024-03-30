package com.omgservers.service.module.server.impl.service.serverService.impl.method.syncServerContainer;

import com.omgservers.model.dto.server.SyncServerContainerRequest;
import com.omgservers.model.dto.server.SyncServerContainerResponse;
import com.omgservers.model.server.ServerModel;
import com.omgservers.model.serverContainer.ServerContainerModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.server.impl.operation.server.hasServer.HasServerOperation;
import com.omgservers.service.module.server.impl.operation.server.selectServerForUpdate.SelectServerForUpdateOperation;
import com.omgservers.service.module.server.impl.operation.server.udpateServerUsage.UpdateServerUsageOperation;
import com.omgservers.service.module.server.impl.operation.serverContainer.upsertServerContainer.UpsertServerContainerOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncServerContainerMethodImpl implements SyncServerContainerMethod {

    final SelectServerForUpdateOperation selectServerForUpdateOperation;
    final UpsertServerContainerOperation upsertServerContainerOperation;
    final UpdateServerUsageOperation updateServerUsageOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasServerOperation hasServerOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncServerContainerResponse> syncServerContainer(final SyncServerContainerRequest request) {
        log.debug("Sync server container, request={}", request);

        final var serverContainer = request.getServerContainer();
        final var serverId = serverContainer.getServerId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    hasServerOperation.hasServer(sqlConnection, shard, serverId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    // Lock server
                                                    return selectServerForUpdateOperation.selectServerForUpdate(
                                                                    sqlConnection,
                                                                    shard,
                                                                    serverId)
                                                            .map(server -> tryServerUpdate(server, serverContainer))
                                                            // Update server usage
                                                            .flatMap(updatedServer -> updateServerUsageOperation
                                                                    .updateServerUsage(
                                                                            changeContext,
                                                                            sqlConnection,
                                                                            shard,
                                                                            serverId,
                                                                            updatedServer.getCpuUsed(),
                                                                            updatedServer.getMemoryUsed()))
                                                            // Sync new container
                                                            .flatMap(updated -> upsertServerContainerOperation
                                                                    .upsertServerContainer(
                                                                            changeContext,
                                                                            sqlConnection,
                                                                            shard,
                                                                            serverContainer));
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "server does not exist or was deleted, id=" + serverId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncServerContainerResponse::new);
    }

    // TODO: Unit test it
    ServerModel tryServerUpdate(final ServerModel server, final ServerContainerModel serverContainer) {
        final var cpuLimit = serverContainer.getCpuLimit();
        final var cpuValue = server.getCpuUsed() + cpuLimit;

        if (cpuValue > server.getCpuCount()) {
            // TODO: add exception message
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.SERVER_OVERWEIGHT,
                    String.format("not enough server cpu, serverId=%d, count=%d, required=%d",
                            server.getId(), server.getCpuCount(), cpuValue));
        }

        final var memoryLimit = serverContainer.getMemoryLimit();
        final var memoryValue = server.getMemoryUsed() + memoryLimit;

        if (memoryValue > server.getMemorySize()) {
            // TODO: add exception message
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.SERVER_OVERWEIGHT,
                    String.format("not enough server memory, serverId=%d, size=%d, required=%d",
                            server.getId(), server.getMemorySize(), memoryValue));
        }

        server.setCpuUsed(cpuValue);
        server.setMemoryUsed(memoryValue);

        return server;
    }
}
