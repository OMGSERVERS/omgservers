package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.syncClientMatchmakerRef;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.client.SyncClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.SyncClientMatchmakerRefResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.module.client.impl.operation.client.hasClient.HasClientOperation;
import com.omgservers.service.module.client.impl.operation.clientMatchmakerRef.upsertClientMatchmakerRef.UpsertClientMatchmakerRefOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncClientMatchmakerRefMethodImpl implements SyncClientMatchmakerRefMethod {

    final UpsertClientMatchmakerRefOperation upsertClientMatchmakerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasClientOperation hasClientOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncClientMatchmakerRefResponse> syncClientMatchmakerRef(final SyncClientMatchmakerRefRequest request) {
        log.trace("{}", request);

        final var clientMatchmakerRef = request.getClientMatchmakerRef();
        final var clientId = clientMatchmakerRef.getClientId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> hasClientOperation
                                        .hasClient(sqlConnection, shardModel.shard(), clientId)
                                        .flatMap(has -> {
                                            if (has) {
                                                return upsertClientMatchmakerRefOperation.upsertClientMatchmakerRef(
                                                        changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        clientMatchmakerRef);
                                            } else {
                                                throw new ServerSideNotFoundException(
                                                        ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                        "client does not exist or was deleted, id=" + clientId);
                                            }
                                        })
                        )
                        .map(ChangeContext::getResult))
                .map(SyncClientMatchmakerRefResponse::new);
    }
}
