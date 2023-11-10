package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doMulticastMessage;

import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.model.dto.user.RespondClientRequest;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeIdAndEntityIds.SelectRuntimeGrantsByRuntimeIdAndEntityIdsOperation;
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

    final SelectRuntimeGrantsByRuntimeIdAndEntityIdsOperation selectRuntimeGrantsByRuntimeIdAndEntityIdsOperation;
    final CheckShardOperation checkShardOperation;

    final MessageModelFactory messageModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<DoMulticastMessageResponse> doMulticastMessage(final DoMulticastMessageRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var recipients = request.getRecipients();
        final var message = request.getMessage();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var grant = RuntimeGrantTypeEnum.MATCH_CLIENT;
                    final var clientIds = recipients.stream().map(Recipient::clientId).toList();

                    return pgPool.withTransaction(sqlConnection -> selectRuntimeGrantsByRuntimeIdAndEntityIdsOperation
                            .selectRuntimeGrantsByRuntimeIdAndEntityIds(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    clientIds)
                            .flatMap(runtimeGrants -> {
                                if (checkGrants(recipients, runtimeGrants, grant)) {
                                    return doMulticastMessage(recipients, message);
                                } else {
                                    throw new ServerSideForbiddenException(
                                            String.format("grants were not found, " +
                                                            "runtimeId=%s, recipients=%s, grant=%s",
                                                    runtimeId, recipients, grant));
                                }
                            })
                    );
                })
                .replaceWith(new DoMulticastMessageResponse(true));
    }


    boolean checkGrants(final List<Recipient> recipients,
                        final List<RuntimeGrantModel> runtimeGrants,
                        final RuntimeGrantTypeEnum grant) {
        final var grantSet = runtimeGrants.stream()
                .filter(runtimeGrant -> runtimeGrant.getType().equals(grant))
                .map(RuntimeGrantModel::getEntityId)
                .collect(Collectors.toSet());

        return recipients.stream()
                .allMatch(recipient -> grantSet.contains(recipient.clientId()));
    }

    Uni<Void> doMulticastMessage(final List<Recipient> recipients,
                                 final Object message) {
        return Multi.createFrom().iterable(recipients)
                .onItem().transformToUniAndConcatenate(recipient -> {
                    final var userId = recipient.userId();
                    final var clientId = recipient.clientId();
                    return respondClient(userId, clientId, message);
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
