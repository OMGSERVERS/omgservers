package com.omgservers.module.runtime.impl.service.doService.impl.method.doUnicastMessage;

import com.omgservers.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.runtime.impl.operation.hasRuntimeGrant.HasRuntimeGrantOperation;
import com.omgservers.module.user.UserModule;
import com.omgservers.operation.checkShard.CheckShardOperation;
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
        final var runtimeId = request.getRuntimeId();
        final var userId = request.getUserId();
        final var clientId = request.getClientId();
        final var message = request.getMessage();

        log.info("Do unicast for message, runtimeId={}, userId={}, clientId={}", runtimeId, userId, clientId);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var permission = RuntimeGrantTypeEnum.CLIENT;
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

    Uni<Void> respondClient(Long userId, Long clientId, Object message) {
        final var messageBody = new ServerMessageBodyModel(message);
        final var messageModel = messageModelFactory.create(MessageQualifierEnum.SERVER_MESSAGE, messageBody);

        final var respondClientRequest = RespondClientRequest.builder()
                .userId(userId)
                .clientId(clientId)
                .message(messageModel)
                .build();
        return userModule.getUserService().respondClient(respondClientRequest);
    }
}
