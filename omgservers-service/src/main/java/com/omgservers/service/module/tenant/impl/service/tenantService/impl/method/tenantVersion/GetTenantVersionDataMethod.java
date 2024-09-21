package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantVersionDataMethod {
    Uni<GetTenantVersionDataResponse> execute(GetTenantVersionDataRequest request);
}
