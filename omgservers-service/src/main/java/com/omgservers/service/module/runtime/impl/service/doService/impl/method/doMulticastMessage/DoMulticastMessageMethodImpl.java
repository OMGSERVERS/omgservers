package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doMulticastMessage;

import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.ClientMessageModelFactory;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.client.ClientModule;
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

    final ClientModule clientModule;
    final UserModule userModule;

    final SelectActiveRuntimeClientsByRuntimeIdOperation selectActiveRuntimeClientsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final ClientMessageModelFactory clientMessageModelFactory;
    final MessageModelFactory messageModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<DoMulticastMessageResponse> doMulticastMessage(final DoMulticastMessageRequest request) {
        log.debug("Do multicast message, request={}", request);

        final var runtimeId = request.getRuntimeId();
        final var clients = request.getClients();
        final var message = request.getMessage();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> pgPool.withTransaction(
                        sqlConnection -> selectActiveRuntimeClientsByRuntimeIdOperation
                                .selectActiveRuntimeClientsByRuntimeId(
                                        sqlConnection,
                                        shardModel.shard(),
                                        runtimeId)
                                .flatMap(runtimeClients -> {
                                    if (checkClients(clients, runtimeClients)) {
                                        return doMulticastMessage(clients, message);
                                    } else {
                                        throw new ServerSideForbiddenException(
                                                String.format(
                                                        "not all runtime clients for clients were found, " +
                                                                "runtimeId=%s, clients=%s",
                                                        runtimeId, clients));
                                    }
                                })
                ))
                .replaceWith(new DoMulticastMessageResponse(true));
    }


    boolean checkClients(final List<Long> clients,
                         final List<RuntimeClientModel> runtimeClients) {
        final var runtimeClientSet = runtimeClients.stream()
                .map(RuntimeClientModel::getClientId)
                .collect(Collectors.toSet());

        return runtimeClientSet.containsAll(clients);
    }

    Uni<Void> doMulticastMessage(final List<Long> clients,
                                 final Object message) {
        return Multi.createFrom().iterable(clients)
                .onItem().transformToUniAndConcatenate(clientId -> syncClientMessage(clientId, message)
                        .onFailure()
                        .recoverWithItem(t -> {
                            log.warn("Do multicast message failed, " +
                                            "clientId={}, " +
                                            "{}:{}",
                                    clientId,
                                    t.getClass().getSimpleName(),
                                    t.getMessage());
                            return null;
                        }))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Boolean> syncClientMessage(final Long clientId,
                                   final Object message) {
        final var messageBody = new ServerMessageBodyModel(message);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.SERVER_MESSAGE,
                messageBody);
        return clientModule.getShortcutService().syncClientMessage(clientMessage);
    }
}
