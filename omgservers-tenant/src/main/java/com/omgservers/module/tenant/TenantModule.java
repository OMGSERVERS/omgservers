package com.omgservers.module.tenant;

import com.omgservers.module.tenant.impl.service.projectShardedService.ProjectShardedService;
import com.omgservers.module.tenant.impl.service.stageService.StageService;
import com.omgservers.module.tenant.impl.service.stageShardedService.StageShardedService;
import com.omgservers.module.tenant.impl.service.tenantShardedService.TenantShardedService;
import com.omgservers.module.tenant.impl.service.versionService.VersionService;
import com.omgservers.module.tenant.impl.service.versionShardedService.VersionShardedService;

public interface TenantModule {

    VersionShardedService getVersionShardedService();

    ProjectShardedService getProjectShardedService();

    TenantShardedService getTenantShardedService();

    StageShardedService getStageShardedService();

    VersionService getVersionService();

    StageService getStageService();
}
