package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantStageMethod {
    Uni<SyncTenantStageResponse> execute(SyncTenantStageRequest request);
}