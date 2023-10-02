package com.omgservers.module.runtime.impl.service.doService.impl.method.doMulticastMessage;

import com.omgservers.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeIdAndEntityIds.SelectRuntimeGrantsByRuntimeIdAndEntityIdsOperation;
import com.omgservers.module.user.UserModule;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
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
        final var event = request.getMessage();

        log.info("Do multicast for message, runtimeId={}, recipientsCount={}",
                runtimeId, recipients.size());

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var clientIds = recipients.stream().map(Recipient::clientId).toList();

                    return pgPool.withTransaction(sqlConnection -> selectRuntimeGrantsByRuntimeIdAndEntityIdsOperation
                            .selectRuntimeGrantsByRuntimeIdAndEntityIds(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    clientIds)
                            .flatMap(runtimeGrants -> doMulticast(runtimeId, recipients, runtimeGrants, event))
                    );
                })
                .replaceWith(new DoMulticastMessageResponse());
    }

    Uni<Void> doMulticast(final Long runtimeId,
                          final List<Recipient> recipients,
                          final List<RuntimeGrantModel> runtimeGrants,
                          final String event) {
        final var grantType = RuntimeGrantTypeEnum.CLIENT;
        final var grantMap = createGrantMap(runtimeGrants, grantType);

        return Multi.createFrom().iterable(recipients)
                .onItem().transformToUniAndMerge(recipient -> {
                    if (grantMap.containsKey(recipient.clientId())) {
                        return respondClient(recipient, event).map(voidItem -> new RespondResult(recipient, true));
                    } else {
                        return Uni.createFrom().item(new RespondResult(recipient, false));
                    }
                })
                .collect().asList()
                .invoke(results -> {
                    final var missingGrantsFor = results.stream()
                            .filter(result -> Boolean.FALSE.equals(result.wasSent))
                            .map(RespondResult::recipient)
                            .toList();
                    if (missingGrantsFor.size() > 0) {
                        if (missingGrantsFor.size() == recipients.size()) {
                            throw new ServerSideForbiddenException(
                                    String.format("grants were not found, " +
                                                    "runtimeId=%s, recipients=%s, grantType=%s",
                                            runtimeId, recipients, grantType));
                        } else {
                            log.warn("Not all grants were found, " +
                                            "missingGrantsFor={}, totalRecipients={}",
                                    missingGrantsFor.size(), recipients.size());
                        }
                    } else {
                        log.info("Multicast for message was finished, recipientCount={}", recipients.size());
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> respondClient(Recipient recipient, String event) {
        final var body = new ServerMessageBodyModel(event);
        final var message = messageModelFactory
                .create(MessageQualifierEnum.SERVER_MESSAGE, body);

        final var respondClientRequest = RespondClientRequest.builder()
                .userId(recipient.userId())
                .clientId(recipient.clientId())
                .message(message)
                .build();
        return userModule.getUserService().respondClient(respondClientRequest);
    }

    Map<Long, RuntimeGrantTypeEnum> createGrantMap(final List<RuntimeGrantModel> runtimeGrants,
                                                   final RuntimeGrantTypeEnum grantType) {
        return runtimeGrants.stream()
                .filter(runtimeGrant -> runtimeGrant.getType().equals(grantType))
                .collect(Collectors.toMap(RuntimeGrantModel::getEntityId, RuntimeGrantModel::getType));
    }

    record RespondResult(Recipient recipient, Boolean wasSent) {
    }
}
