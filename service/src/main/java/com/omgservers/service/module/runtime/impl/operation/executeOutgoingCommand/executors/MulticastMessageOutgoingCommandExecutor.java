package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerOutgoingMessageBodyModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.MulticastMessageOutgoingCommandBodyModel;
import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
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
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MulticastMessageOutgoingCommandExecutor implements OutgoingCommandExecutor {

    final ClientModule clientModule;
    final UserModule userModule;

    final SelectActiveRuntimeAssignmentsByRuntimeIdOperation selectActiveRuntimeAssignmentsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final ClientMessageModelFactory clientMessageModelFactory;
    final MessageModelFactory messageModelFactory;

    final PgPool pgPool;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.MULTICAST_MESSAGE;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        log.debug("Execute multicast message outgoing command, outgoingCommand={}", outgoingCommand);

        final var commandBody = (MulticastMessageOutgoingCommandBodyModel) outgoingCommand.getBody();
        final var clients = commandBody.getClients();
        final var message = commandBody.getMessage();

        return checkShardOperation.checkShard(runtimeId.toString())
                .flatMap(shardModel -> pgPool.withTransaction(
                        sqlConnection -> selectActiveRuntimeAssignmentsByRuntimeIdOperation
                                .selectActiveRuntimeAssignmentsByRuntimeId(
                                        sqlConnection,
                                        shardModel.shard(),
                                        runtimeId)
                                .flatMap(runtimeAssignments -> {
                                    if (checkClients(clients, runtimeAssignments)) {
                                        return multicastMessage(clients, message);
                                    } else {
                                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.CLIENT_ID_WRONG,
                                                String.format("wrong clientIds, runtimeId=%s, clients=%s",
                                                        runtimeId, clients));
                                    }
                                }))
                );
    }

    boolean checkClients(final List<Long> clients,
                         final List<RuntimeAssignmentModel> runtimeAssignments) {
        final var runtimeAssignmentsSet = runtimeAssignments.stream()
                .map(RuntimeAssignmentModel::getClientId)
                .collect(Collectors.toSet());

        return runtimeAssignmentsSet.containsAll(clients);
    }

    Uni<Void> multicastMessage(final List<Long> clients,
                               final Object message) {
        return Multi.createFrom().iterable(clients)
                .onItem().transformToUniAndConcatenate(clientId -> syncClientMessage(clientId, message)
                        .onFailure()
                        .recoverWithItem(t -> {
                            log.warn("Multicast message failed, " +
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
