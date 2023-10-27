package com.omgservers.module.system.impl.service.eventService.impl;

import com.omgservers.dto.internal.GetEventRequest;
import com.omgservers.dto.internal.GetEventResponse;
import com.omgservers.dto.internal.SyncEventRequest;
import com.omgservers.dto.internal.SyncEventResponse;
import com.omgservers.dto.internal.UpdateEventsRelayedFlagRequest;
import com.omgservers.dto.internal.UpdateEventsRelayedFlagResponse;
import com.omgservers.dto.internal.UpdateEventsStatusRequest;
import com.omgservers.dto.internal.UpdateEventsStatusResponse;
import com.omgservers.dto.internal.ViewEventsForRelayRequest;
import com.omgservers.dto.internal.ViewEventsForRelayResponse;
import com.omgservers.module.system.impl.service.eventService.impl.method.getEvent.GetEventMethod;
import com.omgservers.module.system.impl.service.eventService.impl.method.updateEventsRelayedFlag.UpdateEventsRelayedFlagMethod;
import com.omgservers.module.system.impl.service.eventService.EventService;
import com.omgservers.module.system.impl.service.eventService.impl.method.syncEvent.SyncEventMethod;
import com.omgservers.module.system.impl.service.eventService.impl.method.updateEventsStatus.UpdateEventsStatusMethod;
import com.omgservers.module.system.impl.service.eventService.impl.method.viewEventsForRelay.ViewEventsForRelayMethod;
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
