package com.omgservers.module.internal.impl.service.eventShardedService.impl;

import com.omgservers.module.internal.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.module.internal.impl.operation.getInternalModuleClient.InternalModuleClient;
import com.omgservers.module.internal.impl.service.eventService.EventService;
import com.omgservers.module.internal.impl.service.eventShardedService.EventShardedService;
import com.omgservers.module.internal.impl.service.eventShardedService.impl.method.fireEvent.FireEventMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.internalModule.FireEventShardRequest;
import com.omgservers.dto.internalModule.FireEventShardedResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EventShardedServiceImpl implements EventShardedService {

    final EventService eventService;
    final FireEventMethod fireEventMethod;

    final GetInternalModuleClientOperation getInternalModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<FireEventShardedResponse> fireEvent(FireEventShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                FireEventShardRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::fireEvent,
                fireEventMethod::fireEvent);
    }
}
