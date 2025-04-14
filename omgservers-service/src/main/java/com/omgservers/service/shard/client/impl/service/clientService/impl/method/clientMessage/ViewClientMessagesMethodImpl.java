package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientMessage.ViewClientMessagesRequest;
import com.omgservers.schema.shard.client.clientMessage.ViewClientMessagesResponse;
import com.omgservers.service.shard.client.impl.operation.clientMessage.SelectActiveClientMessagesByClientIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewClientMessagesMethodImpl implements ViewClientMessagesMethod {

    final SelectActiveClientMessagesByClientIdOperation selectActiveClientMessagesByClientIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewClientMessagesResponse> execute(final ShardModel shardModel,
                                                   final ViewClientMessagesRequest request) {
        log.trace("{}", request);

        final var clientId = request.getClientId();
        return pgPool.withTransaction(sqlConnection -> selectActiveClientMessagesByClientIdOperation
                        .selectActiveClientMessagesByClientId(sqlConnection,
                                shardModel.shard(),
                                clientId
                        ))
                .map(ViewClientMessagesResponse::new);

    }
}
