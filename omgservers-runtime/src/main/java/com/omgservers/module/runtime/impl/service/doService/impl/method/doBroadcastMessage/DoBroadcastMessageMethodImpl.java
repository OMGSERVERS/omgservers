package com.omgservers.module.runtime.impl.service.doService.impl.method.doBroadcastMessage;

import com.omgservers.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.EventMessageBodyModel;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeId.SelectRuntimeGrantsByRuntimeIdOperation;
import com.omgservers.module.user.UserModule;
import com.omgservers.operation.checkShard.CheckShardOperation;
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

    final UserModule userModule;

    final SelectRuntimeGrantsByRuntimeIdOperation selectRuntimeGrantsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final MessageModelFactory messageModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<DoBroadcastMessageResponse> doBroadcastMessage(final DoBroadcastMessageRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var event = request.getMessage();

        log.info("Do broadcast for message, runtimeId={}", runtimeId);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var grantType = RuntimeGrantTypeEnum.CLIENT;
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeGrantsByRuntimeIdOperation
                            .selectRuntimeGrantsByRuntimeId(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId)
                            .map(runtimeGrants -> createRecipientList(runtimeGrants, grantType))
                            .flatMap(recipients -> doBroadcast(recipients, event))
                    );
                })
                .replaceWith(new DoBroadcastMessageResponse());
    }

    List<Recipient> createRecipientList(final List<RuntimeGrantModel> runtimeGrants,
                                        final RuntimeGrantTypeEnum grantType) {
        return runtimeGrants.stream()
                .filter(runtimeGrant -> runtimeGrant.getType().equals(grantType))
                .map(runtimeGrant -> new Recipient(runtimeGrant.getShardKey(), runtimeGrant.getEntityId()))
                .toList();
    }

    Uni<Void> doBroadcast(final List<Recipient> recipients,
                          final String event) {
        return Multi.createFrom().iterable(recipients)
                .onItem().transformToUniAndMerge(recipient -> respondClient(recipient, event))
                .collect().asList().replaceWithVoid()
                .invoke(voidItem -> log.info("Broadcast for message was finished, " +
                        "recipientCount={}", recipients.size()))
                .replaceWithVoid();
    }

    Uni<Void> respondClient(Recipient recipient, String event) {
        final var body = new EventMessageBodyModel(event);
        final var message = messageModelFactory
                .create(MessageQualifierEnum.EVENT_MESSAGE, body);

        final var respondClientRequest = RespondClientRequest.builder()
                .userId(recipient.userId())
                .clientId(recipient.clientId())
                .message(message)
                .build();
        return userModule.getUserService().respondClient(respondClientRequest);
    }
}
