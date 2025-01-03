package com.omgservers.dispatcher.module.impl;

import com.omgservers.dispatcher.module.DispatcherModule;
import com.omgservers.dispatcher.module.impl.service.dispatcherService.DispatcherService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DispatcherModuleImpl implements DispatcherModule {

    final DispatcherService dispatcherService;

    public DispatcherService getDispatcherService() {
        return dispatcherService;
    }
}
