package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl;

import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.HandlerHelpService;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.method.HandleEventMethod;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.request.HandleEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.response.HandleEventHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandlerHelpServiceImpl implements HandlerHelpService {

    final HandleEventMethod handleEventMethod;

    @Override
    public Uni<HandleEventHelpResponse> handleEvent(HandleEventHelpRequest request) {
        return handleEventMethod.handleEvent(request);
    }
}
