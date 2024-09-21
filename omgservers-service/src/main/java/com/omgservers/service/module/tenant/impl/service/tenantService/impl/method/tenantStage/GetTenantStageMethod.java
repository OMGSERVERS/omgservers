package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantStageMethod {
    Uni<GetTenantStageResponse> execute(GetTenantStageRequest request);
}
