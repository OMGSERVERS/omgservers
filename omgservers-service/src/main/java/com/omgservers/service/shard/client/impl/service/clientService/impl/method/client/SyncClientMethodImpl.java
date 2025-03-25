package com.omgservers.service.shard.client.impl.service.clientService.impl.method.client;

import com.omgservers.schema.module.client.client.SyncClientRequest;
import com.omgservers.schema.module.client.client.SyncClientResponse;
import com.omgservers.service.shard.client.impl.operation.client.UpsertClientOperation;
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
class SyncClientMethodImpl implements SyncClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertClientOperation upsertClientOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncClientResponse> execute(final SyncClientRequest request) {
        log.trace("{}", request);

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