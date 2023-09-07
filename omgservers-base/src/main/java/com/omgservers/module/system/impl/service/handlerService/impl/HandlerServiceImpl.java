package com.omgservers.module.system.impl.service.handlerService.impl;

import com.omgservers.module.system.impl.service.handlerService.HandlerService;
import com.omgservers.module.system.impl.service.handlerService.impl.method.HandleEventMethod;
import com.omgservers.dto.internal.HandleEventRequest;
import com.omgservers.dto.internal.HandleEventResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandlerServiceImpl implements HandlerService {

    final HandleEventMethod handleEventMethod;

    @Override
    public Uni<HandleEventResponse> handleEvent(HandleEventRequest request) {
        return handleEventMethod.handleEvent(request);
    }
}
