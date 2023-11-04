package com.omgservers.service.module.system.impl.service.eventService.impl;

import com.omgservers.model.dto.system.GetEventRequest;
import com.omgservers.model.dto.system.GetEventResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.system.UpdateEventsRelayedFlagRequest;
import com.omgservers.model.dto.system.UpdateEventsRelayedFlagResponse;
import com.omgservers.model.dto.system.UpdateEventsStatusRequest;
import com.omgservers.model.dto.system.UpdateEventsStatusResponse;
import com.omgservers.model.dto.system.ViewEventsForRelayRequest;
import com.omgservers.model.dto.system.ViewEventsForRelayResponse;
import com.omgservers.service.module.system.impl.service.eventService.impl.method.getEvent.GetEventMethod;
import com.omgservers.service.module.system.impl.service.eventService.impl.method.updateEventsRelayedFlag.UpdateEventsRelayedFlagMethod;
import com.omgservers.service.module.system.impl.service.eventService.EventService;
import com.omgservers.service.module.system.impl.service.eventService.impl.method.syncEvent.SyncEventMethod;
import com.omgservers.service.module.system.impl.service.eventService.impl.method.updateEventsStatus.UpdateEventsStatusMethod;
import com.omgservers.service.module.system.impl.service.eventService.impl.method.viewEventsForRelay.ViewEventsForRelayMethod;
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
    final SyncEventMethod syncEventMethod;
    final GetEventMethod getEventMethod;

    @Override
    public Uni<GetEventResponse> getEvent(final @Valid GetEventRequest request) {
        return getEventMethod.getEvent(request);
    }

    @Override
    public Uni<UpdateEventsRelayedFlagResponse> updateEventsRelayedFlag(
            @Valid final UpdateEventsRelayedFlagRequest request) {
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
    public Uni<SyncEventResponse> syncEvent(@Valid final SyncEventRequest request) {
        return syncEventMethod.syncEvent(request);
    }
}
