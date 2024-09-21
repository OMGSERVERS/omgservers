package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsRequest;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantProjectsMethod {
    Uni<ViewTenantProjectsResponse> execute(ViewTenantProjectsRequest request);
}
