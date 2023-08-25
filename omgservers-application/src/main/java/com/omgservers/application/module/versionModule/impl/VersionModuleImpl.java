package com.omgservers.application.module.versionModule.impl;

import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.VersionHelpService;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class VersionModuleImpl implements VersionModule {

    final VersionInternalService versionInternalService;
    final VersionHelpService versionHelpService;

    @Override
    public VersionInternalService getVersionInternalService() {
        return versionInternalService;
    }

    @Override
    public VersionHelpService getVersionHelpService() {
        return versionHelpService;
    }
}
