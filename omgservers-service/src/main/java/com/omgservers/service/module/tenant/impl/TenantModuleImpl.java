package com.omgservers.service.module.tenant.impl;

import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.tenant.impl.service.projectService.ProjectService;
import com.omgservers.service.module.tenant.impl.service.shortcutService.ShortcutService;
import com.omgservers.service.module.tenant.impl.service.stageService.StageService;
import com.omgservers.service.module.tenant.impl.service.tenantService.TenantService;
import com.omgservers.service.module.tenant.impl.service.versionService.VersionService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TenantModuleImpl implements TenantModule {

    final ShortcutService shortcutService;
    final ProjectService projectService;
    final VersionService versionService;
    final TenantService tenantService;
    final StageService stageService;

    @Override
    public TenantService getTenantService() {
        return tenantService;
    }

    @Override
    public ProjectService getProjectService() {
        return projectService;
    }

    @Override
    public StageService getStageService() {
        return stageService;
    }

    @Override
    public VersionService getVersionService() {
        return versionService;
    }

    @Override
    public ShortcutService getShortcutService() {
        return shortcutService;
    }
}
