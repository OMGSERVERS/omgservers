package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantBuildRequest;

import com.omgservers.schema.module.tenant.tenantBuildRequest.DeleteTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.DeleteTenantBuildRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantBuildRequestMethod {
    Uni<DeleteTenantBuildRequestResponse> execute(DeleteTenantBuildRequestRequest request);
}
