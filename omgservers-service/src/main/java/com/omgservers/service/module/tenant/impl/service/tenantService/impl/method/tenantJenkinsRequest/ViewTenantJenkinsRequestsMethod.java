package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantJenkinsRequest;

import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantJenkinsRequestsMethod {
    Uni<ViewTenantJenkinsRequestsResponse> execute(ViewTenantJenkinsRequestsRequest request);
}
