package com.omgservers.service.module.root.impl;

import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.module.root.impl.service.rootService.RootService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RootModuleImpl implements RootModule {

    final RootService rootService;

    @Override
    public RootService getService() {
        return rootService;
    }
}
