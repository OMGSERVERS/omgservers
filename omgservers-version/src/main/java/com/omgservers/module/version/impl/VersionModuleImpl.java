package com.omgservers.module.version.impl;

import com.omgservers.module.version.VersionModule;
import com.omgservers.module.version.impl.service.versionService.VersionService;
import com.omgservers.module.version.impl.service.versionShardedService.VersionShardedService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class VersionModuleImpl implements VersionModule {

    final VersionShardedService versionShardedService;
    final VersionService versionService;

    public VersionShardedService getVersionShardedService() {
        return versionShardedService;
    }

    public VersionService getVersionService() {
        return versionService;
    }
}
