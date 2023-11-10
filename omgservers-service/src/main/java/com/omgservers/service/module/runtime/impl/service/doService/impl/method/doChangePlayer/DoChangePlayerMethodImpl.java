package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doChangePlayer;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.runtime.DoChangePlayerRequest;
import com.omgservers.model.dto.runtime.DoChangePlayerResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.runtimeCommand.body.ChangePlayerRuntimeCommandBodyModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.runtime.impl.operation.hasRuntimeGrant.HasRuntimeGrantOperation;
import com.omgservers.service.module.system.SystemModule;
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
class DoChangePlayerMethodImpl implements DoChangePlayerMethod {

    final RuntimeModule runtimeModule;
    final SystemModule systemModule;
    final UserModule userModule;

    final HasRuntimeGrantOperation hasRuntimeGrantOperation;
    final CheckShardOperation checkShardOperation;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DoChangePlayerResponse> doChangePlayer(final DoChangePlayerRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var userId = request.getUserId();
        final var clientId = request.getClientId();
        final var message = request.getMessage();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var permission = RuntimeGrantTypeEnum.MATCH_CLIENT;
                    return pgPool.withTransaction(sqlConnection -> hasRuntimeGrantOperation.hasRuntimeGrant(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    userId,
                                    clientId,
                                    permission)
                            .flatMap(has -> {
                                if (has) {
                                    return changePlayer(userId, clientId, message);
                                } else {
                                    throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                                    "runtimeId=%s, client_id=%s, permission=%s",
                                            runtimeId, clientId, permission));
                                }
                            })
                    );
                })
                .replaceWith(new DoChangePlayerResponse(true));
    }

    Uni<Boolean> changePlayer(final Long userId, final Long clientId, final Object message) {
        return getClient(userId, clientId)
                .flatMap(client -> {
                    final var runtimeId = client.getDefaultRuntimeId();
                    return syncChangePlayerRuntimeCommand(runtimeId, client, message);
                });
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var getClientServiceRequest = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(getClientServiceRequest)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> syncChangePlayerRuntimeCommand(final Long runtimeId,
                                                final ClientModel client,
                                                final Object message) {
        final var userId = client.getUserId();
        final var clientId = client.getId();
        final var runtimeCommandBody = new ChangePlayerRuntimeCommandBodyModel(userId, clientId, message);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        final var syncRuntimeCommandShardedRequest = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(syncRuntimeCommandShardedRequest)
                .map(SyncRuntimeCommandResponse::getCreated);
    }
}
