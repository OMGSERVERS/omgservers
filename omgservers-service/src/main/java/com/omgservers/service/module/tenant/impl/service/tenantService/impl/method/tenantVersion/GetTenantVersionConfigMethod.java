package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantVersionConfigMethod {
    Uni<GetTenantVersionConfigResponse> execute(GetTenantVersionConfigRequest request);
}
