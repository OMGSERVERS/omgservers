package com.omgservers.module.tenant;

import com.omgservers.module.tenant.impl.service.projectShardedService.ProjectShardedService;
import com.omgservers.module.tenant.impl.service.stageService.StageService;
import com.omgservers.module.tenant.impl.service.stageShardedService.StageShardedService;
import com.omgservers.module.tenant.impl.service.tenantShardedService.TenantShardedService;

public interface TenantModule {

    ProjectShardedService getProjectShardedService();

    TenantShardedService getTenantShardedService();

    StageShardedService getStageShardedService();

    StageService getStageService();
}
