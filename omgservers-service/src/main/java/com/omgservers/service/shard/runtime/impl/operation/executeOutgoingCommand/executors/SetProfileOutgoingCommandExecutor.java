package com.omgservers.service.shard.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.body.SetProfileOutgoingCommandBodyDto;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.module.user.UpdatePlayerProfileResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
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
public class SetProfileOutgoingCommandExecutor implements OutgoingCommandExecutor {

    final ClientShard clientShard;
    final UserShard userShard;

    final HasRuntimeAssignmentOperation hasRuntimeAssignmentOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.SET_PROFILE;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        log.debug("Execute set profile outgoing command, outgoingCommand={}", outgoingCommand);

        final var commandBody = (SetProfileOutgoingCommandBodyDto) outgoingCommand.getBody();
        final var clientId = commandBody.getClientId();
        final var profile = commandBody.getProfile();

        return checkShardOperation.checkShard(runtimeId.toString())
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection ->
                        hasRuntimeAssignmentOperation.execute(
                                        sqlConnection,
                                        shardModel.shard(),
                                        runtimeId,
                                        clientId)
                                .flatMap(has -> {
                                    if (has) {
                                        return setProfile(clientId, profile)
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

    Uni<Boolean> setProfile(final Long clientId,
                            final Object profile) {
        return getClient(clientId)
                .flatMap(client -> {
                    final var userId = client.getUserId();
                    final var playerId = client.getPlayerId();
                    return updatePlayerProfile(userId, playerId, profile);
                });
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> updatePlayerProfile(final Long userId,
                                     final Long playerId,
                                     final Object profile) {
        final var request = new UpdatePlayerProfileRequest(userId, playerId, profile);
        return userShard.getService().updatePlayerProfile(request)
                .map(UpdatePlayerProfileResponse::getUpdated);
    }
}
