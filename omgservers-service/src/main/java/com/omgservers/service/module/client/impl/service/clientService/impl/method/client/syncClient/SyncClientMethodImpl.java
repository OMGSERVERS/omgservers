package com.omgservers.service.module.client.impl.service.clientService.impl.method.client.syncClient;

import com.omgservers.schema.module.client.SyncClientRequest;
import com.omgservers.schema.module.client.SyncClientResponse;
import com.omgservers.service.module.client.impl.operation.client.upsertClient.UpsertClientOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncClientMethodImpl implements SyncClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertClientOperation upsertClientOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncClientResponse> syncClient(final SyncClientRequest request) {
        log.debug("Sync client, request={}", request);

        final var client = request.getClient();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> upsertClientOperation
                                        .upsertClient(changeContext, sqlConnection, shardModel.shard(), client))
                        .map(ChangeContext::getResult))
                .map(SyncClientResponse::new);
    }
}