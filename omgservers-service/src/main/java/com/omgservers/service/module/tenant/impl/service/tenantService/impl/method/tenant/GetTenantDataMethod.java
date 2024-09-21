package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.module.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDataMethod {
    Uni<GetTenantDataResponse> getTenantData(GetTenantDataRequest request);
}
