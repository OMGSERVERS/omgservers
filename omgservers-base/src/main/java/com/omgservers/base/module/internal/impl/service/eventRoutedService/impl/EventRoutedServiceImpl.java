package com.omgservers.base.module.internal.impl.service.eventRoutedService.impl;

import com.omgservers.base.module.internal.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.base.module.internal.impl.operation.getInternalModuleClient.InternalModuleClient;
import com.omgservers.base.module.internal.impl.service.eventService.EventService;
import com.omgservers.base.module.internal.impl.service.eventRoutedService.EventRoutedService;
import com.omgservers.base.module.internal.impl.service.eventRoutedService.impl.method.fireEvent.FireEventMethod;
import com.omgservers.base.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.internalModule.FireEventRoutedRequest;
import com.omgservers.dto.internalModule.FireEventRoutedResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EventRoutedServiceImpl implements EventRoutedService {

    final EventService eventService;
    final FireEventMethod fireEventMethod;

    final GetInternalModuleClientOperation getInternalModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<FireEventRoutedResponse> fireEvent(FireEventRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                FireEventRoutedRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::fireEvent,
                fireEventMethod::fireEvent);
    }
}
