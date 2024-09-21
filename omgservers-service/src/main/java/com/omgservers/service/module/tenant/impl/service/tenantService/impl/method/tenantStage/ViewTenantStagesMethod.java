package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantStagesMethod {
    Uni<ViewTenantStagesResponse> execute(ViewTenantStagesRequest request);
}
