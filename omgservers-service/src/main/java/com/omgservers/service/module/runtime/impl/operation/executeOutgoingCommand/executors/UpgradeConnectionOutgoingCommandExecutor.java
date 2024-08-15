package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.ConnectionUpgradeMessageBodyModel;
import com.omgservers.schema.model.message.body.ConnectionUpgradeQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.body.UpgradeConnectionOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.UpgradeConnectionQualifierEnum;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.OutgoingCommandExecutor;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.hasRuntimeAssignment.HasRuntimeAssignmentOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import com.omgservers.service.operation.issueJwtToken.IssueJwtTokenOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UpgradeConnectionOutgoingCommandExecutor implements OutgoingCommandExecutor {

    final ClientModule clientModule;

    final HasRuntimeAssignmentOperation hasRuntimeAssignmentOperation;
    final IssueJwtTokenOperation issueJwtTokenOperation;
    final CheckShardOperation checkShardOperation;


    final ClientMessageModelFactory clientMessageModelFactory;
    final PgPool pgPool;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.UPGRADE_CONNECTION;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        log.debug("Execute upgrade connection outgoing command, outgoingCommand={}", outgoingCommand);

        final var commandBody = (UpgradeConnectionOutgoingCommandBodyModel) outgoingCommand.getBody();
        final var clientId = commandBody.getClientId();
        final var protocol = commandBody.getProtocol();

        return checkShardOperation.checkShard(runtimeId.toString())
                .flatMap(shardModel -> pgPool
                        .withTransaction(sqlConnection -> hasRuntimeAssignmentOperation
                                .hasRuntimeAssignment(
                                        sqlConnection,
                                        shardModel.shard(),
                                        runtimeId,
                                        clientId)
                                .flatMap(has -> {
                                    if (has) {
                                        return upgradeConnection(runtimeId, clientId, protocol);
                                    } else {
                                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_CLIENT_ID,
                                                String.format("wrong clientId, runtimeId=%s, clientId=%s",
                                                        runtimeId, clientId));
                                    }
                                })
                        )
                );
    }

    Uni<Void> upgradeConnection(final Long runtimeId,
                                final Long clientId,
                                final UpgradeConnectionQualifierEnum protocol) {
        return switch (protocol) {
            case WEBSOCKET -> {
                final var wsToken = issueJwtTokenOperation.issueWsJwtToken(clientId, runtimeId, UserRoleEnum.PLAYER);
                final var webSocketConfig = new ConnectionUpgradeMessageBodyModel.WebSocketConfig(wsToken);
                final var messageBody = ConnectionUpgradeMessageBodyModel.builder()
                        .clientId(clientId)
                        .protocol(ConnectionUpgradeQualifierEnum.WEBSOCKET)
                        .webSocketConfig(webSocketConfig)
                        .build();
                final var clientMessage = clientMessageModelFactory.create(clientId,
                        MessageQualifierEnum.CONNECTION_UPGRADE_MESSAGE,
                        messageBody);
                yield syncClientMessage(clientMessage)
                        .replaceWithVoid();
            }
        };
    }

    Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessage(request)
                .map(SyncClientMessageResponse::getCreated);
    }
}
