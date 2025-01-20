package com.omgservers.service.shard.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.ServerOutgoingMessageBodyDto;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.body.RespondClientOutgoingCommandBodyDto;
import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.client.MessageModelFactory;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.runtime.impl.operation.executeOutgoingCommand.OutgoingCommandExecutor;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.HasRuntimeAssignmentOperation;
import com.omgservers.service.shard.user.UserShard;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RespondClientOutgoingCommandExecutor implements OutgoingCommandExecutor {

    final ClientShard clientShard;
    final UserShard userShard;

    final HasRuntimeAssignmentOperation hasRuntimeAssignmentOperation;
    final CheckShardOperation checkShardOperation;

    final ClientMessageModelFactory clientMessageModelFactory;
    final MessageModelFactory messageModelFactory;
    final PgPool pgPool;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.RESPOND_CLIENT;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        log.debug("Execute respond client outgoing command, outgoingCommand={}", outgoingCommand);

        final var commandBody = (RespondClientOutgoingCommandBodyDto) outgoingCommand.getBody();
        final var clientId = commandBody.getClientId();
        final var message = commandBody.getMessage();

        return checkShardOperation.checkShard(runtimeId.toString())
                .flatMap(shardModel -> pgPool.withTransaction(
                        sqlConnection -> hasRuntimeAssignmentOperation.execute(
                                        sqlConnection,
                                        shardModel.shard(),
                                        runtimeId,
                                        clientId)
                                .flatMap(has -> {
                                    if (has) {
                                        return syncClientMessage(clientId, message)
                                                .replaceWithVoid();
                                    } else {
                                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                String.format(
                                                        "runtime assignment was not found, runtimeId=%s, clientId=%s",
                                                        runtimeId, clientId));
                                    }
                                }))
                );
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
        return clientShard.getService().syncClientMessage(request)
                .map(SyncClientMessageResponse::getCreated);
    }
}
