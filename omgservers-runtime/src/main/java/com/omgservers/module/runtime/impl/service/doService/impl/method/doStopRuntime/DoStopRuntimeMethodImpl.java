package com.omgservers.module.runtime.impl.service.doService.impl.method.doStopRuntime;

import com.omgservers.dto.internal.SyncEventRequest;
import com.omgservers.dto.internal.SyncEventResponse;
import com.omgservers.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.model.event.body.StopApprovedEventBodyModel;
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
class DoStopRuntimeMethodImpl implements DoStopRuntimeMethod {

    final SystemModule systemModule;

    final CheckShardOperation checkShardOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public Uni<DoStopRuntimeResponse> doStopRuntime(final DoStopRuntimeRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var reason = request.getReason();
                    return syncEvent(runtimeId, reason);
                })
                .replaceWith(new DoStopRuntimeResponse());
    }

    Uni<Boolean> syncEvent(final Long runtimeId,
                           final String reason) {
        final var eventBody = new StopApprovedEventBodyModel(runtimeId, reason);
        final var eventModel = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(request)
                .map(SyncEventResponse::getCreated);
    }
}
