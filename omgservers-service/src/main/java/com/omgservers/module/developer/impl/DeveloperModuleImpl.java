package com.omgservers.module.developer.impl;

import com.omgservers.module.developer.DeveloperModule;
import com.omgservers.module.developer.impl.service.developerService.DeveloperService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperModuleImpl implements DeveloperModule {

    final DeveloperService developerService;

    @Override
    public DeveloperService getDeveloperService() {
        return developerService;
    }
}
