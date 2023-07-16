package com.omgservers.application.module.runtimeModule.impl;

import com.omgservers.application.module.runtimeModule.RuntimeModule;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.RuntimeInternalService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RuntimeModuleImpl implements RuntimeModule {

    final RuntimeInternalService runtimeInternalService;

    @Override
    public RuntimeInternalService getRuntimeInternalService() {
        return runtimeInternalService;
    }
}
