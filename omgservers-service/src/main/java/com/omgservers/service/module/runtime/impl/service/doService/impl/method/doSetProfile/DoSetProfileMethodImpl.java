package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doSetProfile;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.runtime.DoSetProfileRequest;
import com.omgservers.model.dto.runtime.DoSetProfileResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.UpdatePlayerProfileRequest;
import com.omgservers.model.dto.user.UpdatePlayerProfileResponse;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.module.runtime.impl.operation.hasRuntimeClient.HasRuntimeClientOperation;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoSetProfileMethodImpl implements DoSetProfileMethod {

    final UserModule userModule;

    final HasRuntimeClientOperation hasRuntimeClientOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<DoSetProfileResponse> doSetProfile(final DoSetProfileRequest request) {
        log.debug("Do set profile, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    final var userId = request.getUserId();
                    final var clientId = request.getClientId();
                    final var profile = request.getProfile();
                    return pgPool.withTransaction(sqlConnection -> hasRuntimeClientOperation.hasRuntimeClient(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    userId,
                                    clientId)
                            .flatMap(has -> {
                                if (has) {
                                    return doSetProfile(userId, clientId, profile);
                                } else {
                                    throw new ServerSideForbiddenException(
                                            String.format("runtime client was not found, " +
                                                            "runtimeId=%s, userId=%s, clientId=%s",
                                                    runtimeId, userId, clientId));
                                }
                            })
                    );
                })
                .replaceWith(new DoSetProfileResponse(true));
    }

    Uni<Boolean> doSetProfile(final Long userId,
                              final Long clientId,
                              final Object profile) {
        return getClient(userId, clientId)
                .flatMap(client -> {
                    final var playerId = client.getPlayerId();
                    return updatePlayerProfile(userId, playerId, profile);
                });
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var getClientServiceRequest = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(getClientServiceRequest)
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
