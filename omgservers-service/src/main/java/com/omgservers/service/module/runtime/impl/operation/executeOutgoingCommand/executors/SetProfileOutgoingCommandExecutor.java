package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.user.UpdatePlayerProfileRequest;
import com.omgservers.model.dto.user.UpdatePlayerProfileResponse;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.SetProfileOutgoingCommandBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.OutgoingCommandExecutor;
import com.omgservers.service.module.runtime.impl.operation.hasRuntimeClient.HasRuntimeClientOperation;
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
public class SetProfileOutgoingCommandExecutor implements OutgoingCommandExecutor {

    final ClientModule clientModule;
    final UserModule userModule;

    final HasRuntimeClientOperation hasRuntimeClientOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.SET_PROFILE;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        log.debug("Execute set profile outgoing command, outgoingCommand={}", outgoingCommand);

        final var commandBody = (SetProfileOutgoingCommandBodyModel) outgoingCommand.getBody();
        final var clientId = commandBody.getClientId();
        final var profile = commandBody.getProfile();

        return checkShardOperation.checkShard(runtimeId.toString())
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection ->
                        hasRuntimeClientOperation.hasRuntimeClient(
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
                                                String.format("runtime client was not found, " +
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
        return clientModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> updatePlayerProfile(final Long userId,
                                     final Long playerId,
                                     final Object profile) {
        final var request = new UpdatePlayerProfileRequest(userId, playerId, profile);
        return userModule.getPlayerService().updatePlayerProfile(request)
                .map(UpdatePlayerProfileResponse::getUpdated);
    }
}
