package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doUnicastMessage;

import com.omgservers.model.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.model.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.model.dto.user.RespondClientRequest;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.runtime.impl.operation.hasRuntimeGrant.HasRuntimeGrantOperation;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoUnicastMessageMethodImpl implements DoUnicastMessageMethod {

    final UserModule userModule;

    final HasRuntimeGrantOperation hasRuntimeGrantOperation;
    final CheckShardOperation checkShardOperation;

    final MessageModelFactory messageModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<DoUnicastMessageResponse> doUnicastMessage(final DoUnicastMessageRequest request) {
        log.debug("Do unicast message, request={}", request);

        final var runtimeId = request.getRuntimeId();
        final var userId = request.getUserId();
        final var clientId = request.getClientId();
        final var message = request.getMessage();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var permission = RuntimeGrantTypeEnum.MATCH_CLIENT;
                    return pgPool.withTransaction(sqlConnection -> hasRuntimeGrantOperation.hasRuntimeGrant(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    userId,
                                    clientId,
                                    permission)
                            .flatMap(has -> {
                                if (has) {
                                    return respondClient(userId, clientId, message);
                                } else {
                                    throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                                    "runtimeId=%s, client_id=%s, permission=%s",
                                            runtimeId, clientId, permission));
                                }
                            })
                    );
                })
                .replaceWith(new DoUnicastMessageResponse());
    }

    Uni<Void> respondClient(final Long userId,
                            final Long clientId,
                            final Object message) {
        final var messageBody = new ServerMessageBodyModel(message);
        final var messageModel = messageModelFactory.create(MessageQualifierEnum.SERVER_MESSAGE, messageBody);

        final var request = new RespondClientRequest(userId, clientId, messageModel);
        return userModule.getUserService().respondClient(request);
    }
}
