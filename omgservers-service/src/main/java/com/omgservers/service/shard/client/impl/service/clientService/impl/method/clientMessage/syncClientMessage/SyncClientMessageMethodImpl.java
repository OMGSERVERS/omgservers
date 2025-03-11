package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage.syncClientMessage;

import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.client.impl.operation.clientMessage.upsertClientMessage.UpsertClientMessageOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncClientMessageMethodImpl implements SyncClientMessageMethod {

    final UpsertClientMessageOperation upsertClientMessageOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncClientMessageResponse> syncClientMessage(final SyncClientMessageRequest request) {
        log.trace("{}", request);

        final var clientMessage = request.getClientMessage();
        final var clientId = clientMessage.getClientId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> upsertClientMessageOperation.upsertClientMessage(
                                        changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        clientMessage)
                        )
                        .map(ChangeContext::getResult))
                .map(SyncClientMessageResponse::new);
    }
}
