package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantBuildRequest;

import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantBuildRequestMethod {
    Uni<GetTenantBuildRequestResponse> execute(GetTenantBuildRequestRequest request);
}
