package com.omgservers.service.entrypoint.runtime.impl;

import com.omgservers.service.entrypoint.runtime.RuntimeEntrypoint;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.RuntimeService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RuntimeEntrypointImpl implements RuntimeEntrypoint {

    final RuntimeService runtimeService;

    public RuntimeService getService() {
        return runtimeService;
    }
}
