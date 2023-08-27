package com.omgservers.module.handler.impl;

import com.omgservers.module.handler.HandlerModule;
import com.omgservers.module.handler.impl.service.handlerService.HandlerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandlerModuleImpl implements HandlerModule {

    final HandlerService handlerService;

    public HandlerService getHandlerService() {
        return handlerService;
    }
}
