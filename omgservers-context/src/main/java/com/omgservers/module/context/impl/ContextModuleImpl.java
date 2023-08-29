package com.omgservers.module.context.impl;

import com.omgservers.module.context.ContextModule;
import com.omgservers.module.context.impl.service.contextService.ContextService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ContextModuleImpl implements ContextModule {

    final ContextService contextService;

    public ContextService getContextService() {
        return contextService;
    }
}
