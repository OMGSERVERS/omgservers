package com.omgservers.application.module.bootstrapModule.impl;

import com.omgservers.application.module.bootstrapModule.BootstrapModule;
import com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.BootstrapHelpService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapModuleImpl implements BootstrapModule {

    final BootstrapHelpService bootstrapHelpService;

    @Override
    public BootstrapHelpService getBootstrapHelpService() {
        return bootstrapHelpService;
    }
}
