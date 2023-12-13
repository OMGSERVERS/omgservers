package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doMulticastMessage;

import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.model.dto.user.RespondClientRequest;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.runtime.impl.operation.selectActiveRuntimeClientsByRuntimeId.SelectActiveRuntimeClientsByRuntimeIdOperation;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoMulticastMessageMethodImpl implements DoMulticastMessageMethod {

    final UserModule userModule;

    final SelectActiveRuntimeClientsByRuntimeIdOperation selectActiveRuntimeClientsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final MessageModelFactory messageModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DoMulticastMessageResponse> doMulticastMessage(final DoMulticastMessageRequest request) {
        log.debug("Do multicast message, request={}", request);

        final var runtimeId = request.getRuntimeId();
        final var recipients = request.getRecipients();
        final var message = request.getMessage();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> pgPool.withTransaction(
                        sqlConnection -> selectActiveRuntimeClientsByRuntimeIdOperation
                                .selectActiveRuntimeClientsByRuntimeId(
                                        sqlConnection,
                                        shardModel.shard(),
                                        runtimeId)
                                .flatMap(runtimeClients -> {
                                    if (checkRecipients(recipients, runtimeClients)) {
                                        return doMulticastMessage(recipients, message);
                                    } else {
                                        throw new ServerSideForbiddenException(
                                                String.format(
                                                        "not all runtime clients for recipients were found, " +
                                                                "runtimeId=%s, recipients=%s",
                                                        runtimeId, recipients));
                                    }
                                })
                ))
                .replaceWith(new DoMulticastMessageResponse(true));
    }


    boolean checkRecipients(final List<Recipient> recipients,
                            final List<RuntimeClientModel> runtimeClients) {
        final var runtimeClientSet = runtimeClients.stream()
                .map(RuntimeClientModel::getClientId)
                .collect(Collectors.toSet());

        return recipients.stream()
                .allMatch(recipient -> runtimeClientSet.contains(recipient.clientId()));
    }

    Uni<Void> doMulticastMessage(final List<Recipient> recipients,
                                 final Object message) {
        return Multi.createFrom().iterable(recipients)
                .onItem().transformToUniAndConcatenate(recipient -> {
                    final var userId = recipient.userId();
                    final var clientId = recipient.clientId();
                    return respondClient(userId, clientId, message)
                            .onFailure()
                            .recoverWithItem(t -> {
                                log.warn("Do multicast message failed, " +
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
