package com.omgservers.application.module.internalModule.impl.service.eventInternalService.impl;

import com.omgservers.application.module.internalModule.impl.operation.getInternalsServiceApiClientOperation.GetInternalsServiceApiClientOperation;
import com.omgservers.application.module.internalModule.impl.operation.getInternalsServiceApiClientOperation.InternalsServiceApiClient;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.EventInternalService;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.impl.method.fireEventMethod.FireEventMethod;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.response.FireEventInternalResponse;
import com.omgservers.application.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EventInternalServiceImpl implements EventInternalService {

    final EventHelpService eventHelpService;
    final FireEventMethod fireEventMethod;

    final GetInternalsServiceApiClientOperation getInternalsServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<FireEventInternalResponse> fireEvent(FireEventInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                FireEventInternalRequest::validate,
                getInternalsServiceApiClientOperation::getClient,
                InternalsServiceApiClient::fireEvent,
                fireEventMethod::fireEvent);
    }
}
