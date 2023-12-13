package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doRespondClient;

import com.omgservers.model.dto.runtime.DoRespondClientRequest;
import com.omgservers.model.dto.runtime.DoRespondClientResponse;
import com.omgservers.model.dto.user.RespondClientRequest;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.runtime.impl.operation.hasRuntimeClient.HasRuntimeClientOperation;
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
class DoRespondClientMethodImpl implements DoRespondClientMethod {

    final UserModule userModule;

    final HasRuntimeClientOperation hasRuntimeClientOperation;
    final CheckShardOperation checkShardOperation;

    final MessageModelFactory messageModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DoRespondClientResponse> doRespondClient(final DoRespondClientRequest request) {
        log.debug("Do respond client, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    final var userId = request.getUserId();
                    final var clientId = request.getClientId();
                    final var message = request.getMessage();
                    return pgPool.withTransaction(sqlConnection -> hasRuntimeClientOperation.hasRuntimeClient(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    userId,
                                    clientId)
                            .flatMap(has -> {
                                if (has) {
                                    return respondClient(userId, clientId, message);
                                } else {
                                    throw new ServerSideForbiddenException(
                                            String.format("runtime client was not found, " +
                                                            "runtimeId=%s, userId=%s, clientId=%s",
                                                    runtimeId, userId, clientId));
                                }
                            })
                    );
                })
                .replaceWith(new DoRespondClientResponse(true));
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
