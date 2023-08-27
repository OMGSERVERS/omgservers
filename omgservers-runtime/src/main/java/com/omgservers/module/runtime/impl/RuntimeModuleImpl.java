package com.omgservers.module.runtime.impl;

import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.impl.service.runtimeShardedService.RuntimeShardedService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RuntimeModuleImpl implements RuntimeModule {

    final RuntimeShardedService runtimeShardedService;

    public RuntimeShardedService getRuntimeShardedService() {
        return runtimeShardedService;
    }
}
