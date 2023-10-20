package com.omgservers.module.runtime.impl.service.doService.impl.method.doKickClient;

import com.omgservers.dto.internal.SyncEventRequest;
import com.omgservers.dto.internal.SyncEventResponse;
import com.omgservers.dto.runtime.DoKickClientRequest;
import com.omgservers.dto.runtime.DoKickClientResponse;
import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.model.event.body.KickApprovedEventBodyModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.module.runtime.impl.operation.hasRuntimeGrant.HasRuntimeGrantOperation;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoKickClientMethodImpl implements DoKickClientMethod {

    final SystemModule systemModule;

    final HasRuntimeGrantOperation hasRuntimeGrantOperation;
    final CheckShardOperation checkShardOperation;

    final EventModelFactory eventModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DoKickClientResponse> doKickClient(final DoKickClientRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var permission = RuntimeGrantTypeEnum.MATCH_CLIENT;
                    final var runtimeId = request.getRuntimeId();
                    final var userId = request.getUserId();
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> hasRuntimeGrantOperation.hasRuntimeGrant(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    userId,
                                    clientId,
                                    permission)
                            .flatMap(has -> {
                                if (has) {
                                    return syncApprove(runtimeId, userId, clientId);
                                } else {
                                    throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                                    "runtimeId=%s, client_id=%s, permission=%s",
                                            runtimeId, clientId, permission));
                                }
                            })
                    );
                })
                .replaceWith(new DoKickClientResponse());
    }

    Uni<Boolean> syncApprove(final Long runtimeId,
                             final Long userId,
                             final Long clientId) {
        final var eventBody = new KickApprovedEventBodyModel(runtimeId, userId, clientId);
        final var eventModel = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(request)
                .map(SyncEventResponse::getCreated);
    }
}
