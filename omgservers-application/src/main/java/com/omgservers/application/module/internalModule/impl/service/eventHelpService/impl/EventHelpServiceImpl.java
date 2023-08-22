package com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl;

import com.omgservers.application.module.internalModule.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.fireEventMethod.FireEventMethod;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.startEventDispatcherMethod.StartEventDispatcherMethod;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.response.FireEventHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EventHelpServiceImpl implements EventHelpService {

    final StartEventDispatcherMethod startEventDispatcherMethod;
    final FireEventMethod fireEventMethod;

    @Override
    public Uni<Void> startEventDispatcher() {
        return startEventDispatcherMethod.startEventDispatcher();
    }

    @Override
    public Uni<FireEventHelpResponse> fireEvent(FireEventHelpRequest request) {
        return fireEventMethod.fireEvent(request);
    }
}
