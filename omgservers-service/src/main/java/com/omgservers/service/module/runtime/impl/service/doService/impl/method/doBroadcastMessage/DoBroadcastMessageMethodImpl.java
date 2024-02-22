package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doBroadcastMessage;

import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.model.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.factory.ClientMessageModelFactory;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.client.ClientModule;
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

    final ClientModule clientModule;
    final SystemModule systemModule;
    final UserModule userModule;

    final SelectActiveRuntimeClientsByRuntimeIdOperation selectActiveRuntimeClientsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final ClientMessageModelFactory clientMessageModelFactory;
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
                                .map(this::createClientList)
                                .flatMap(clients -> doBroadcastMessage(clients, message))
                ))
                .replaceWith(new DoBroadcastMessageResponse(true));
    }

    List<Long> createClientList(final List<RuntimeClientModel> runtimeClients) {
        return runtimeClients.stream()
                .map(RuntimeClientModel::getClientId)
                .toList();
    }

    Uni<Void> doBroadcastMessage(final List<Long> clients,
                                 final Object message) {
        return Multi.createFrom().iterable(clients)
                .onItem().transformToUniAndConcatenate(clientId -> {
                    return syncClientMessage(clientId, message)
                            .onFailure()
                            .recoverWithItem(t -> {
                                log.warn("Do broadcast message failed, " +
                                                "clientId={}, " +
                                                "{}:{}",
                                        clientId,
                                        t.getClass().getSimpleName(),
                                        t.getMessage());
                                return null;
                            });
                })
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Boolean> syncClientMessage(final Long clientId,
                                   final Object message) {
        final var messageBody = new ServerMessageBodyModel(message);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.SERVER_MESSAGE,
                messageBody);
        return syncClientMessage(clientMessage);
    }

    Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessage(request)
                .map(SyncClientMessageResponse::getCreated);
    }
}
