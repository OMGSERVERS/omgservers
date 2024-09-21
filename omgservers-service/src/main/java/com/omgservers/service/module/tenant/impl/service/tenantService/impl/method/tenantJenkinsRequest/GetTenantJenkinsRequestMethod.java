package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantJenkinsRequest;

import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantJenkinsRequestMethod {
    Uni<GetTenantJenkinsRequestResponse> execute(GetTenantJenkinsRequestRequest request);
}
