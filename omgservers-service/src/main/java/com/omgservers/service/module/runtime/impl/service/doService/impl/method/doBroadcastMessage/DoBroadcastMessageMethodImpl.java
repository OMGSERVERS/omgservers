package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doBroadcastMessage;

import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.model.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.model.dto.user.RespondClientRequest;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.runtime.impl.operation.selectActiveRuntimeClientsByRuntimeId.SelectActiveRuntimeClientsByRuntimeIdOperation;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoBroadcastMessageMethodImpl implements DoBroadcastMessageMethod {

    final SystemModule systemModule;
    final UserModule userModule;

    final SelectActiveRuntimeClientsByRuntimeIdOperation selectActiveRuntimeClientsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final MessageModelFactory messageModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DoBroadcastMessageResponse> doBroadcastMessage(final DoBroadcastMessageRequest request) {
        log.debug("Do broadcast message, request={}", request);

        final var runtimeId = request.getRuntimeId();
        final var message = request.getMessage();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> pgPool.withTransaction(
                        sqlConnection -> selectActiveRuntimeClientsByRuntimeIdOperation
                                .selectActiveRuntimeClientsByRuntimeId(sqlConnection,
                                        shardModel.shard(),
                                        runtimeId)
                                .map(this::createRecipientList)
                                .flatMap(recipients -> doBroadcastMessage(recipients, message))
                ))
                .replaceWith(new DoBroadcastMessageResponse(true));
    }

    List<Recipient> createRecipientList(final List<RuntimeClientModel> runtimeClients) {
        return runtimeClients.stream()
                .map(runtimeClient -> new Recipient(runtimeClient.getUserId(), runtimeClient.getClientId()))
                .toList();
    }

    Uni<Void> doBroadcastMessage(final List<Recipient> recipients,
                                 final Object message) {
        return Multi.createFrom().iterable(recipients)
                .onItem().transformToUniAndConcatenate(recipient -> {
                    final var userId = recipient.userId();
                    final var clientId = recipient.clientId();
                    return respondClient(userId, clientId, message)
                            .onFailure()
                            .recoverWithItem(t -> {
                                log.warn("Do broadcast message failed, " +
                                                "client={}/{}, " +
                                                "{}:{}",
                                        userId,
                                        clientId,
                                        t.getClass().getSimpleName(),
                                        t.getMessage());
                                return null;
                            });
                })
                .collect().asList()
                .replaceWithVoid();
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
