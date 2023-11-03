package com.omgservers.module.runtime.impl.service.doService.impl.method.doSetAttributes;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.runtime.DoSetAttributesRequest;
import com.omgservers.model.dto.runtime.DoSetAttributesResponse;
import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.model.event.body.SetAttributesApprovedEventBodyModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.module.runtime.impl.operation.hasRuntimeGrant.HasRuntimeGrantOperation;
import com.omgservers.module.system.SystemModule;
import com.omgservers.factory.EventModelFactory;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoSetAttributesMethodImpl implements DoSetAttributesMethod {

    final SystemModule systemModule;

    final HasRuntimeGrantOperation hasRuntimeGrantOperation;
    final CheckShardOperation checkShardOperation;

    final EventModelFactory eventModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DoSetAttributesResponse> doSetAttributes(final DoSetAttributesRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var grant = RuntimeGrantTypeEnum.CLIENT;
                    final var runtimeId = request.getRuntimeId();
                    final var userId = request.getUserId();
                    final var clientId = request.getClientId();
                    final var attributes = request.getAttributes();
                    return pgPool.withTransaction(sqlConnection -> hasRuntimeGrantOperation.hasRuntimeGrant(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    userId,
                                    clientId,
                                    grant)
                            .flatMap(has -> {
                                if (has) {
                                    return syncApprove(runtimeId, userId, clientId, attributes);
                                } else {
                                    throw new ServerSideForbiddenException(String.format("lack of grant, " +
                                                    "runtimeId=%s, userId=%s, clientId=%s, grant=%s",
                                            runtimeId, userId, clientId, grant));
                                }
                            })
                    );
                })
                .replaceWith(new DoSetAttributesResponse(true));
    }

    Uni<Boolean> syncApprove(final Long runtimeId,
                             final Long userId,
                             final Long clientId,
                             final PlayerAttributesModel attributes) {
        final var eventBody = new SetAttributesApprovedEventBodyModel(runtimeId, userId, clientId, attributes);
        final var eventModel = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(request)
                .map(SyncEventResponse::getCreated);
    }
}
