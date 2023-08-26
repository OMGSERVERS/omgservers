package com.omgservers.module.internal.impl.service.eventService.impl;

import com.omgservers.module.internal.impl.service.eventService.EventService;
import com.omgservers.module.internal.impl.service.eventService.impl.method.startEventDispatcher.StartEventDispatcherMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EventServiceImpl implements EventService {

    final StartEventDispatcherMethod startEventDispatcherMethod;

    @Override
    public Uni<Void> startEventDispatcher() {
        return startEventDispatcherMethod.startEventDispatcher();
    }
}
