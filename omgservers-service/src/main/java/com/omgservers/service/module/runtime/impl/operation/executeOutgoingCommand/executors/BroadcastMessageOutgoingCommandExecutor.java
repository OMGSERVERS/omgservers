package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerOutgoingMessageBodyModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.BroadcastMessageOutgoingCommandBodyModel;
import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.factory.ClientMessageModelFactory;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.OutgoingCommandExecutor;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.selectActiveRuntimeAssignmentsByRuntimeId.SelectActiveRuntimeAssignmentsByRuntimeIdOperation;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BroadcastMessageOutgoingCommandExecutor implements OutgoingCommandExecutor {

    final ClientModule clientModule;
    final SystemModule systemModule;
    final UserModule userModule;

    final SelectActiveRuntimeAssignmentsByRuntimeIdOperation selectActiveRuntimeAssignmentsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final ClientMessageModelFactory clientMessageModelFactory;
    final MessageModelFactory messageModelFactory;
    final PgPool pgPool;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.BROADCAST_MESSAGE;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        log.debug("Execute broadcast message outgoing command, outgoingCommand={}", outgoingCommand);

        final var commandBody = (BroadcastMessageOutgoingCommandBodyModel) outgoingCommand.getBody();
        final var message = commandBody.getMessage();

        return checkShardOperation.checkShard(runtimeId.toString())
                .flatMap(shardModel -> pgPool.withTransaction(
                        sqlConnection -> selectActiveRuntimeAssignmentsByRuntimeIdOperation
                                .selectActiveRuntimeAssignmentsByRuntimeId(sqlConnection,
                                        shardModel.shard(),
                                        runtimeId)
                                .map(this::createClientList)
                                .flatMap(clients -> broadcastMessage(clients, message)))
                );
    }

    List<Long> createClientList(final List<RuntimeAssignmentModel> runtimeAssignments) {
        return runtimeAssignments.stream()
                .map(RuntimeAssignmentModel::getClientId)
                .toList();
    }

    Uni<Void> broadcastMessage(final List<Long> clients,
                               final Object message) {
        return Multi.createFrom().iterable(clients)
                .onItem().transformToUniAndConcatenate(clientId ->
                        syncClientMessage(clientId, message)
                                .replaceWithVoid()
                                .onFailure()
                                .recoverWithUni(t -> {
                                    log.warn("Broadcast message failed, " +
                                                    "clientId={}, " +
                                                    "{}:{}",
                                            clientId,
                                            t.getClass().getSimpleName(),
                                            t.getMessage());
                                    return Uni.createFrom().voidItem();
                                }))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Boolean> syncClientMessage(final Long clientId,
                                   final Object message) {
        final var messageBody = new ServerOutgoingMessageBodyModel(message);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                messageBody);
        return syncClientMessage(clientMessage);
    }

    Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessage(request)
                .map(SyncClientMessageResponse::getCreated);
    }
}
