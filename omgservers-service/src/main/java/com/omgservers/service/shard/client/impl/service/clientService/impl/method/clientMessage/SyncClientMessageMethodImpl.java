package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageRequest;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageResponse;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.client.impl.operation.clientMessage.UpsertClientMessageOperation;
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

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncClientMessageResponse> execute(final ShardModel shardModel,
                                                  final SyncClientMessageRequest request) {
        log.trace("{}", request);

        final var clientMessage = request.getClientMessage();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertClientMessageOperation.upsertClientMessage(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                clientMessage))
                .map(ChangeContext::getResult)
                .map(SyncClientMessageResponse::new);
    }
}
