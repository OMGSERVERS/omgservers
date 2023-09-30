package com.omgservers.module.system.impl.service.eventService.impl;

import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.dto.internal.FireEventResponse;
import com.omgservers.dto.internal.GetEventRequest;
import com.omgservers.dto.internal.GetEventResponse;
import com.omgservers.dto.internal.UpdateEventsRelayedFlagRequest;
import com.omgservers.dto.internal.UpdateEventsRelayedFlagResponse;
import com.omgservers.dto.internal.UpdateEventsStatusRequest;
import com.omgservers.dto.internal.UpdateEventsStatusResponse;
import com.omgservers.dto.internal.ViewEventsForRelayRequest;
import com.omgservers.dto.internal.ViewEventsForRelayResponse;
import com.omgservers.module.system.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.module.system.impl.operation.getInternalModuleClient.SystemModuleClient;
import com.omgservers.module.system.impl.service.eventService.EventService;
import com.omgservers.module.system.impl.service.eventService.impl.method.fireEvent.FireEventMethod;
import com.omgservers.module.system.impl.service.eventService.impl.method.getEvent.GetEventMethod;
import com.omgservers.module.system.impl.service.eventService.impl.method.updateEventsRelayedFlag.UpdateEventsRelayedFlagMethod;
import com.omgservers.module.system.impl.service.eventService.impl.method.updateEventsStatus.UpdateEventsStatusMethod;
import com.omgservers.module.system.impl.service.eventService.impl.method.viewEventsForRelay.ViewEventsForRelayMethod;
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


    final UpdateEventsRelayedFlagMethod updateEventsRelayedFlagMethod;
    final UpdateEventsStatusMethod updateEventsStatusMethod;
    final ViewEventsForRelayMethod viewEventsForRelayMethod;
    final FireEventMethod fireEventMethod;
    final GetEventMethod getEventMethod;

    final GetInternalModuleClientOperation getInternalModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<GetEventResponse> getEvent(final @Valid GetEventRequest request) {
        return getEventMethod.getEvent(request);
    }

    @Override
    public Uni<UpdateEventsRelayedFlagResponse> updateEventsRelayedFlag(@Valid final UpdateEventsRelayedFlagRequest request) {
        return updateEventsRelayedFlagMethod.updateEventsRelayedFlag(request);
    }

    @Override
    public Uni<UpdateEventsStatusResponse> updateEventsStatus(@Valid final UpdateEventsStatusRequest request) {
        return updateEventsStatusMethod.updateEventsStatus(request);
    }

    @Override
    public Uni<ViewEventsForRelayResponse> viewEventsForRelay(@Valid final ViewEventsForRelayRequest request) {
        return viewEventsForRelayMethod.viewEventsForRelay(request);
    }

    @Override
    public Uni<FireEventResponse> fireEvent(@Valid final FireEventRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getInternalModuleClientOperation::getClient,
                SystemModuleClient::fireEvent,
                fireEventMethod::fireEvent);
    }
}
