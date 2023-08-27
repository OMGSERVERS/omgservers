package com.omgservers.module.internal.impl.service.eventShardedService.impl;

import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.dto.internal.FireEventShardedResponse;
import com.omgservers.module.internal.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.module.internal.impl.operation.getInternalModuleClient.InternalModuleClient;
import com.omgservers.module.internal.impl.service.eventShardedService.EventShardedService;
import com.omgservers.module.internal.impl.service.eventShardedService.impl.method.fireEvent.FireEventMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EventShardedServiceImpl implements EventShardedService {

    final FireEventMethod fireEventMethod;

    final GetInternalModuleClientOperation getInternalModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<FireEventShardedResponse> fireEvent(FireEventShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                FireEventShardedRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::fireEvent,
                fireEventMethod::fireEvent);
    }
}
