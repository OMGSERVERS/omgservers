package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef.syncClientRuntimeRef;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.client.SyncClientRuntimeRefRequest;
import com.omgservers.schema.module.client.SyncClientRuntimeRefResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.shard.client.impl.operation.client.hasClient.HasClientOperation;
import com.omgservers.service.shard.client.impl.operation.clientRuntimeRef.upsertClientRuntimeRef.UpsertClientRuntimeRefOperation;
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
class SyncClientRuntimeRefMethodImpl implements SyncClientRuntimeRefMethod {

    final UpsertClientRuntimeRefOperation upsertClientRuntimeRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasClientOperation hasClientOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(final SyncClientRuntimeRefRequest request) {
        log.trace("{}", request);

        final var clientRuntimeRef = request.getClientRuntimeRef();
        final var clientId = clientRuntimeRef.getClientId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> hasClientOperation
                                        .hasClient(sqlConnection, shardModel.shard(), clientId)
                                        .flatMap(has -> {
                                            if (has) {
                                                return upsertClientRuntimeRefOperation.upsertClientRuntimeRef(
                                                        changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        clientRuntimeRef);
                                            } else {
                                                throw new ServerSideNotFoundException(
                                                        ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                        "client does not exist or was deleted, id=" + clientId);
                                            }
                                        })
                        )
                        .map(ChangeContext::getResult))
                .map(SyncClientRuntimeRefResponse::new);
    }
}
