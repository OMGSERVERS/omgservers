package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerResource;

import com.omgservers.schema.module.tenant.tenantMatchmakerResource.GetTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.GetTenantMatchmakerResourceResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantMatchmakerResourceMethod {
    Uni<GetTenantMatchmakerResourceResponse> execute(GetTenantMatchmakerResourceRequest request);
}
