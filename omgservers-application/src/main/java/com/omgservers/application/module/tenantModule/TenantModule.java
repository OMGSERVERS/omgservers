package com.omgservers.application.module.tenantModule;

import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.ProjectInternalService;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.StageHelpService;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.StageInternalService;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.TenantInternalService;

public interface TenantModule {

    TenantInternalService getTenantInternalService();

    ProjectInternalService getProjectInternalService();

    StageInternalService getStageInternalService();

    StageHelpService getStageHelpService();
}
