package com.omgservers.module.tenant.impl;

import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.tenant.impl.service.projectShardedService.ProjectShardedService;
import com.omgservers.module.tenant.impl.service.stageService.StageService;
import com.omgservers.module.tenant.impl.service.stageShardedService.StageShardedService;
import com.omgservers.module.tenant.impl.service.tenantShardedService.TenantShardedService;
import com.omgservers.module.tenant.impl.service.versionService.VersionService;
import com.omgservers.module.tenant.impl.service.versionShardedService.VersionShardedService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TenantModuleImpl implements TenantModule {

    final ProjectShardedService projectShardedService;
    final VersionShardedService versionShardedService;
    final TenantShardedService tenantShardedService;
    final StageShardedService stageShardedService;
    final VersionService versionService;
    final StageService stageService;

    public TenantShardedService getTenantShardedService() {
        return tenantShardedService;
    }

    public ProjectShardedService getProjectShardedService() {
        return projectShardedService;
    }

    public StageShardedService getStageShardedService() {
        return stageShardedService;
    }

    public StageService getStageService() {
        return stageService;
    }

    public VersionShardedService getVersionShardedService() {
        return versionShardedService;
    }

    @Override
    public VersionService getVersionService() {
        return versionService;
    }
}
