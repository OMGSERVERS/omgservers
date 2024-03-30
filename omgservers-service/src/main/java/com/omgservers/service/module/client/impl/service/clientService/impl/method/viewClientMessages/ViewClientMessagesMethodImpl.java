package com.omgservers.service.module.client.impl.service.clientService.impl.method.viewClientMessages;

import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import com.omgservers.service.module.client.impl.operation.clientMessage.selectActiveClientMessagesByClientId.SelectActiveClientMessagesByClientIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewClientMessagesResponse> viewClientMessages(final ViewClientMessagesRequest request) {
        log.debug("View client messages, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveClientMessagesByClientIdOperation
                            .selectActiveClientMessagesByClientId(sqlConnection,
                                    shard.shard(),
                                    clientId
                            ));
                })
                .map(ViewClientMessagesResponse::new);

    }
}
