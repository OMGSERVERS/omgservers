package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerResource;

import com.omgservers.schema.module.tenant.tenantMatchmakerResource.ViewTenantMatchmakerResourcesRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.ViewTenantMatchmakerResourcesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantMatchmakerResourcesMethod {
    Uni<ViewTenantMatchmakerResourcesResponse> execute(ViewTenantMatchmakerResourcesRequest request);
}
