package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantProjectDataMethod {
    Uni<GetTenantProjectDataResponse> execute(GetTenantProjectDataRequest request);
}
