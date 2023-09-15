package com.omgservers.module.system.impl.service.eventService.impl;

import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.dto.internal.FireEventResponse;
import com.omgservers.module.system.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.module.system.impl.operation.getInternalModuleClient.SystemModuleClient;
import com.omgservers.module.system.impl.service.eventService.EventService;
import com.omgservers.module.system.impl.service.eventService.impl.method.fireEvent.FireEventMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EventServiceImpl implements EventService {

    final FireEventMethod fireEventMethod;

    final GetInternalModuleClientOperation getInternalModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<FireEventResponse> fireEvent(@Valid final FireEventRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getInternalModuleClientOperation::getClient,
                SystemModuleClient::fireEvent,
                fireEventMethod::fireEvent);
    }
}
