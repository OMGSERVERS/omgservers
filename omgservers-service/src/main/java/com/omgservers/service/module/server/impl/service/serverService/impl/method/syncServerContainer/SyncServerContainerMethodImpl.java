package com.omgservers.service.module.server.impl.service.serverService.impl.method.syncServerContainer;

import com.omgservers.model.dto.server.SyncServerContainerRequest;
import com.omgservers.model.dto.server.SyncServerContainerResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.server.impl.operation.hasServer.HasServerOperation;
import com.omgservers.service.module.server.impl.operation.upsertServerContainer.UpsertServerContainerOperation;
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

    final UpsertServerContainerOperation upsertServerContainerOperation;
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
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> hasServerOperation
                                        .hasServer(sqlConnection, shardModel.shard(), serverId)
                                        .flatMap(has -> {
                                            if (has) {
                                                return upsertServerContainerOperation.upsertServerContainer(
                                                        changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        serverContainer);
                                            } else {
                                                throw new ServerSideNotFoundException(
                                                        ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                        "server does not exist or was deleted, id=" + serverId);
                                            }
                                        })
                        )
                        .map(ChangeContext::getResult))
                .map(SyncServerContainerResponse::new);
    }
}
