package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantBuildRequest;

import com.omgservers.schema.module.tenant.tenantBuildRequest.ViewTenantBuildRequestsRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.ViewTenantBuildRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantBuildRequestsMethod {
    Uni<ViewTenantBuildRequestsResponse> execute(ViewTenantBuildRequestsRequest request);
}
