package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantStageDataMethod {
    Uni<GetTenantStageDataResponse> execute(GetTenantStageDataRequest request);
}
