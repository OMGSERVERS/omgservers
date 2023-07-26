package com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl;

import com.omgservers.application.module.internalModule.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.fireEventMethod.FireEventMethod;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.insertEventMethod.InsertEventMethod;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.startEventDispatcherMethod.StartEventDispatcherMethod;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EventHelpServiceImpl implements EventHelpService {

    final StartEventDispatcherMethod startEventDispatcherMethod;
    final InsertEventMethod insertEventMethod;
    final FireEventMethod fireEventMethod;

    @Override
    public Uni<Void> startEventDispatcher() {
        return startEventDispatcherMethod.startEventDispatcher();
    }

    @Override
    public Uni<Void> insertEvent(InsertEventHelpRequest request) {
        return insertEventMethod.insertEvent(request);
    }

    @Override
    public Uni<Void> fireEvent(FireEventHelpRequest request) {
        return fireEventMethod.fireEvent(request);
    }
}
