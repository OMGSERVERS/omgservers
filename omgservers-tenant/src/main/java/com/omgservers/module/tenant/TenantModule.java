package com.omgservers.module.tenant;

import com.omgservers.module.tenant.impl.service.projectService.ProjectService;
import com.omgservers.module.tenant.impl.service.stageService.StageService;
import com.omgservers.module.tenant.impl.service.tenantService.TenantService;
import com.omgservers.module.tenant.impl.service.versionService.VersionService;

public interface TenantModule {

    ProjectService getProjectService();

    TenantService getTenantService();

    StageService getStageService();

    VersionService getVersionService();
}
