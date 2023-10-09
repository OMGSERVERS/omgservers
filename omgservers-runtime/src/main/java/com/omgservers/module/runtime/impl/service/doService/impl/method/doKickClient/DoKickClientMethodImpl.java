package com.omgservers.module.runtime.impl.service.doService.impl.method.doKickClient;

import com.omgservers.dto.internal.SyncEventRequest;
import com.omgservers.dto.internal.SyncEventResponse;
import com.omgservers.dto.runtime.DoKickClientRequest;
import com.omgservers.dto.runtime.DoKickClientResponse;
import com.omgservers.model.event.body.KickRequestedEventBodyModel;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoKickClientMethodImpl implements DoKickClientMethod {

    final CheckShardOperation checkShardOperation;
    final SystemModule systemModule;

    final EventModelFactory eventModelFactory;

    @Override
    public Uni<DoKickClientResponse> doKickClient(final DoKickClientRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var userId = request.getUserId();
                    final var clientId = request.getClientId();
                    return syncEvent(runtimeId, userId, clientId);
                })
                .replaceWith(new DoKickClientResponse());
    }

    Uni<Boolean> syncEvent(final Long runtimeId,
                           final Long userId,
                           final Long clientId) {
        final var eventBody = new KickRequestedEventBodyModel(runtimeId, userId, clientId);
        final var eventModel = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(request)
                .map(SyncEventResponse::getCreated);
    }
}
