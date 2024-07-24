package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.SetAttributesOutgoingCommandBodyModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.OutgoingCommandExecutor;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.hasRuntimeAssignment.HasRuntimeAssignmentOperation;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SetAttributesOutgoingCommandExecutor implements OutgoingCommandExecutor {

    final ClientModule clientModule;
    final UserModule userModule;

    final HasRuntimeAssignmentOperation hasRuntimeAssignmentOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.SET_ATTRIBUTES;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        log.debug("Execute set attributes outgoing command, outgoingCommand={}", outgoingCommand);

        final var commandBody = (SetAttributesOutgoingCommandBodyModel) outgoingCommand.getBody();
        final var clientId = commandBody.getClientId();
        final var attributes = commandBody.getAttributes();

        return checkShardOperation.checkShard(runtimeId.toString())
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection ->
                        hasRuntimeAssignmentOperation.hasRuntimeAssignment(
                                        sqlConnection,
                                        shardModel.shard(),
                                        runtimeId,
                                        clientId)
                                .flatMap(has -> {
                                    if (has) {
                                        return setAttributes(clientId, attributes)
                                                .replaceWithVoid();
                                    } else {
                                        throw new ServerSideBadRequestException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                String.format("runtime assignment was not found, " +
                                                                "runtimeId=%s, clientId=%s",
                                                        runtimeId, clientId));
                                    }
                                })
                ));
    }

    Uni<Boolean> setAttributes(final Long clientId,
                               final PlayerAttributesModel attributes) {
        return getClient(clientId)
                .flatMap(client -> {
                    final var userId = client.getUserId();
                    final var playerId = client.getPlayerId();
                    return updatePlayerAttributes(userId, playerId, attributes);
                })
                .replaceWith(Boolean.TRUE);
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> updatePlayerAttributes(final Long userId,
                                        final Long playerId,
                                        final PlayerAttributesModel attributes) {
        final var request = new UpdatePlayerAttributesRequest(userId, playerId, attributes);
        return userModule.getUserService().updatePlayerAttributes(request)
                .map(UpdatePlayerAttributesResponse::getUpdated);
    }
}
