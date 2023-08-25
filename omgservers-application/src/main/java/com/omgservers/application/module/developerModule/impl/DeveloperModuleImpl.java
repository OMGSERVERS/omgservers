package com.omgservers.application.module.developerModule.impl;

import com.omgservers.application.module.developerModule.DeveloperModule;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.DeveloperHelpService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperModuleImpl implements DeveloperModule {

    final DeveloperHelpService developerHelpService;

    @Override
    public DeveloperHelpService getDeveloperHelpService() {
        return developerHelpService;
    }
}
