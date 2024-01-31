package com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClientRuntime;

import com.omgservers.model.dto.client.SyncClientRuntimeRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.client.impl.operation.hasClient.HasClientOperation;
import com.omgservers.service.module.client.impl.operation.upsertClientRuntime.UpsertClientRuntimeOperation;
import com.omgservers.service.module.system.SystemModule;
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
class SyncClientRuntimeMethodImpl implements SyncClientRuntimeMethod {

    final SystemModule systemModule;

    final UpsertClientRuntimeOperation upsertClientRuntimeOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasClientOperation hasClientOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncClientRuntimeResponse> syncClientRuntime(final SyncClientRuntimeRequest request) {
        log.debug("Sync client match, request={}", request);

        final var clientMatch = request.getClientRuntime();
        final var clientId = clientMatch.getClientId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> hasClientOperation
                                        .hasClient(sqlConnection, shardModel.shard(), clientId)
                                        .flatMap(has -> {
                                            if (has) {
                                                return upsertClientRuntimeOperation.upsertClientRuntime(
                                                        changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        clientMatch);
                                            } else {
                                                throw new ServerSideNotFoundException(
                                                        "client does not exist or was deleted, id=" + clientId);
                                            }
                                        })
                        )
                        .map(ChangeContext::getResult))
                .map(SyncClientRuntimeResponse::new);
    }
}
