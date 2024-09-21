package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImageRef;

import com.omgservers.schema.module.tenant.tenantImageRef.GetTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.GetTenantImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantImageRefMethod {
    Uni<GetTenantImageRefResponse> execute(GetTenantImageRefRequest request);
}
