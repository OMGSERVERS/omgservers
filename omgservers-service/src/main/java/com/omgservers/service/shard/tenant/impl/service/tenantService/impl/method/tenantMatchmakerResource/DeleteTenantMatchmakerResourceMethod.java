package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerResource;

import com.omgservers.schema.module.tenant.tenantMatchmakerResource.DeleteTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.DeleteTenantMatchmakerResourceResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMatchmakerResourceMethod {
    Uni<DeleteTenantMatchmakerResourceResponse> execute(DeleteTenantMatchmakerResourceRequest request);
}
