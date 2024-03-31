package com.omgservers.service.module.server.impl.service.serverService.impl.method.deleteServerContainer;

import com.omgservers.model.dto.server.DeleteServerContainerRequest;
import com.omgservers.model.dto.server.DeleteServerContainerResponse;
import com.omgservers.model.server.ServerModel;
import com.omgservers.model.serverContainer.ServerContainerModel;
import com.omgservers.service.module.server.impl.operation.server.selectServerForUpdate.SelectServerForUpdateOperation;
import com.omgservers.service.module.server.impl.operation.server.udpateServerUsage.UpdateServerUsageOperation;
import com.omgservers.service.module.server.impl.operation.serverContainer.deleteServerContainer.DeleteServerContainerOperation;
import com.omgservers.service.module.server.impl.operation.serverContainer.selectServerContainer.SelectServerContainerOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteServerContainerMethodImpl implements DeleteServerContainerMethod {

    final SelectServerForUpdateOperation selectServerForUpdateOperation;
    final SelectServerContainerOperation selectServerContainerOperation;
    final DeleteServerContainerOperation deleteServerContainerOperation;
    final UpdateServerUsageOperation updateServerUsageOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteServerContainerResponse> deleteServerContainer(DeleteServerContainerRequest request) {
        log.debug("Delete server container, request={}", request);

        final var serverId = request.getServerId();
        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) -> {
                                // Lock server
                                return selectServerForUpdateOperation.selectServerForUpdate(
                                                sqlConnection,
                                                shard,
                                                serverId)
                                        .flatMap(server -> selectServerContainerOperation.selectServerContainer(
                                                        sqlConnection,
                                                        shard,
                                                        serverId,
                                                        id)
                                                // Update server usage
                                                .map(serverContainer -> updateServerUsage(server, serverContainer))
                                                .flatMap(updatedServer -> updateServerUsageOperation
                                                        .updateServerUsage(
                                                                changeContext,
                                                                sqlConnection,
                                                                shard,
                                                                serverId,
                                                                updatedServer.getCpuUsed(),
                                                                updatedServer.getMemoryUsed()))
                                                .flatMap(updated -> deleteServerContainerOperation
                                                        .deleteServerContainer(changeContext,
                                                                sqlConnection,
                                                                shardModel.shard(),
                                                                serverId,
                                                                id)
                                                )
                                        );
                            })
                            .map(ChangeContext::getResult);
                })
                .map(DeleteServerContainerResponse::new);
    }

    // TODO: Unit test it
    ServerModel updateServerUsage(final ServerModel server, final ServerContainerModel serverContainer) {
        final var cpuLimit = serverContainer.getCpuLimit();
        final var cpuValue = server.getCpuUsed() - cpuLimit;
        final var memoryLimit = serverContainer.getMemoryLimit();
        final var memoryValue = server.getMemoryUsed() - memoryLimit;

        server.setCpuUsed(cpuValue);
        server.setMemoryUsed(memoryValue);

        return server;
    }
}
