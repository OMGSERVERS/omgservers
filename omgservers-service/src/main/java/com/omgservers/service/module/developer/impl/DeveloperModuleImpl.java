package com.omgservers.service.module.developer.impl;

import com.omgservers.service.module.developer.DeveloperModule;
import com.omgservers.service.module.developer.impl.service.developerService.DeveloperService;
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
