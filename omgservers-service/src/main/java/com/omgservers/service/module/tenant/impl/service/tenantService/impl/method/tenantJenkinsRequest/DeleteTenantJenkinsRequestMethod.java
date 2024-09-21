package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantJenkinsRequest;

import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantJenkinsRequestMethod {
    Uni<DeleteTenantJenkinsRequestResponse> execute(DeleteTenantJenkinsRequestRequest request);
}
