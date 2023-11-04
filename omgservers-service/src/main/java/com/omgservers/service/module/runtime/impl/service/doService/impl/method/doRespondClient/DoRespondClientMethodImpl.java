package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doRespondClient;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.runtime.DoRespondClientRequest;
import com.omgservers.model.dto.runtime.DoRespondClientResponse;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.model.event.body.RespondApprovedEventBodyModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.module.runtime.impl.operation.hasRuntimeGrant.HasRuntimeGrantOperation;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoRespondClientMethodImpl implements DoRespondClientMethod {

    final SystemModule systemModule;

    final HasRuntimeGrantOperation hasRuntimeGrantOperation;
    final CheckShardOperation checkShardOperation;

    final EventModelFactory eventModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DoRespondClientResponse> doRespondClient(final DoRespondClientRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var grant = RuntimeGrantTypeEnum.CLIENT;
                    final var runtimeId = request.getRuntimeId();
                    final var userId = request.getUserId();
                    final var clientId = request.getClientId();
                    final var message = request.getMessage();
                    return pgPool.withTransaction(sqlConnection -> hasRuntimeGrantOperation.hasRuntimeGrant(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    userId,
                                    clientId,
                                    grant)
                            .flatMap(has -> {
                                if (has) {
                                    return syncApprove(runtimeId, userId, clientId, message);
                                } else {
                                    throw new ServerSideForbiddenException(String.format("lack of grant, " +
                                                    "runtimeId=%s, userId=%s, clientId=%s, grant=%s",
                                            runtimeId, userId, clientId, grant));
                                }
                            })
                    );
                })
                .replaceWith(new DoRespondClientResponse(true));
    }

    Uni<Boolean> syncApprove(final Long runtimeId,
                             final Long userId,
                             final Long clientId,
                             final Object message) {
        final var eventBody = new RespondApprovedEventBodyModel(runtimeId, userId, clientId, message);
        final var eventModel = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(request)
                .map(SyncEventResponse::getCreated);
    }
}
