package com.omgservers.service.module.system.impl.service.eventService.impl;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.service.module.system.impl.service.eventService.EventService;
import com.omgservers.service.module.system.impl.service.eventService.impl.method.syncEvent.SyncEventMethod;
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

    final SyncEventMethod syncEventMethod;

    @Override
    public Uni<SyncEventResponse> syncEvent(@Valid final SyncEventRequest request) {
        return syncEventMethod.syncEvent(request);
    }
}
