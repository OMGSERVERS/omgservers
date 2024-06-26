package com.omgservers.service.module.tenant;

import com.omgservers.service.module.tenant.impl.service.projectService.ProjectService;
import com.omgservers.service.module.tenant.impl.service.stageService.StageService;
import com.omgservers.service.module.tenant.impl.service.tenantService.TenantService;
import com.omgservers.service.module.tenant.impl.service.versionService.VersionService;

public interface TenantModule {

    ProjectService getProjectService();

    TenantService getTenantService();

    StageService getStageService();

    VersionService getVersionService();
}
