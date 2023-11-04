package com.omgservers.service.module.runtime.impl;

import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.runtime.impl.service.doService.DoService;
import com.omgservers.service.module.runtime.impl.service.runtimeService.RuntimeService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RuntimeModuleImpl implements RuntimeModule {

    final RuntimeService runtimeService;
    final DoService doService;

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    @Override
    public DoService getDoService() {
        return doService;
    }
}
