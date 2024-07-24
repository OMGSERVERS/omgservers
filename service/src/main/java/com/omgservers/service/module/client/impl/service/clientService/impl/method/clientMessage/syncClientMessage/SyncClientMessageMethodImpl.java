package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.syncClientMessage;

import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.module.client.impl.operation.client.hasClient.HasClientOperation;
import com.omgservers.service.module.client.impl.operation.clientMessage.upsertClientMessage.UpsertClientMessageOperation;
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
class SyncClientMessageMethodImpl implements SyncClientMessageMethod {

    final SystemModule systemModule;

    final UpsertClientMessageOperation upsertClientMessageOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final HasClientOperation hasClientOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncClientMessageResponse> syncClientMessage(final SyncClientMessageRequest request) {
        log.debug("Sync client messages, request={}", request);

        final var clientMessage = request.getClientMessage();
        final var clientId = clientMessage.getClientId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> hasClientOperation
                                        .hasClient(sqlConnection, shardModel.shard(), clientId)
                                        .flatMap(has -> {
                                            if (has) {
                                                return upsertClientMessageOperation.upsertClientMessage(
                                                        changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        clientMessage);
                                            } else {
                                                throw new ServerSideNotFoundException(
                                                        ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                        "client does not exist or was deleted, id=" + clientId);
                                            }
                                        })
                        )
                        .map(ChangeContext::getResult))
                .map(SyncClientMessageResponse::new);
    }
}
