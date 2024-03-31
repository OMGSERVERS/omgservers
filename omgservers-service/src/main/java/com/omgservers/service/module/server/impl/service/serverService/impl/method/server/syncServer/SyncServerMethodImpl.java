package com.omgservers.service.module.server.impl.service.serverService.impl.method.server.syncServer;

import com.omgservers.model.dto.server.SyncServerRequest;
import com.omgservers.model.dto.server.SyncServerResponse;
import com.omgservers.service.module.server.impl.operation.server.upsertServer.UpsertServerOperation;
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
class SyncServerMethodImpl implements SyncServerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertServerOperation upsertServerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncServerResponse> syncServer(final SyncServerRequest request) {
        log.debug("Sync server, request={}", request);

        final var server = request.getServer();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> upsertServerOperation
                                        .upsertServer(changeContext, sqlConnection, shardModel.shard(), server))
                        .map(ChangeContext::getResult))
                .map(SyncServerResponse::new);
    }
}