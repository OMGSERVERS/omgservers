package com.omgservers.application.module.developerModule.impl;

import com.omgservers.application.module.developerModule.DeveloperModule;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.DeveloperHelpService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

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
