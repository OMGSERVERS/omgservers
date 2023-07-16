package com.omgservers.application.module.tenantModule.impl;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.StageInternalService;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.StageHelpService;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.TenantInternalService;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.ProjectInternalService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TenantModuleImpl implements TenantModule {

    final TenantInternalService tenantInternalService;
    final ProjectInternalService projectInternalService;
    final StageInternalService stageInternalService;
    final StageHelpService stageHelpService;

    @Override
    public TenantInternalService getTenantInternalService() {
        return tenantInternalService;
    }

    @Override
    public ProjectInternalService getProjectInternalService() {
        return projectInternalService;
    }

    @Override
    public StageInternalService getStageInternalService() {
        return stageInternalService;
    }

    @Override
    public StageHelpService getStageHelpService() {
        return stageHelpService;
    }
}
