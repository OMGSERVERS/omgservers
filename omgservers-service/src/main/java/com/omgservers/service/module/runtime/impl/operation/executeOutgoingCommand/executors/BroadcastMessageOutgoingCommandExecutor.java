package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.ServerOutgoingMessageBodyDto;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.body.BroadcastMessageOutgoingCommandBodyDto;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.client.MessageModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.OutgoingCommandExecutor;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.selectActiveRuntimeAssignmentsByRuntimeId.SelectActiveRuntimeAssignmentsByRuntimeIdOperation;
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

        final var commandBody = (BroadcastMessageOutgoingCommandBodyDto) outgoingCommand.getBody();
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
        final var messageBody = new ServerOutgoingMessageBodyDto(message);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                messageBody);
        return syncClientMessage(clientMessage);
    }

    Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getService().syncClientMessage(request)
                .map(SyncClientMessageResponse::getCreated);
    }
}
