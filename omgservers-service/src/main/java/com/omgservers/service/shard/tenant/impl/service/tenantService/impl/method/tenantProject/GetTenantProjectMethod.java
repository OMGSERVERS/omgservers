package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantProjectMethod {
    Uni<GetTenantProjectResponse> execute(GetTenantProjectRequest request);
}
