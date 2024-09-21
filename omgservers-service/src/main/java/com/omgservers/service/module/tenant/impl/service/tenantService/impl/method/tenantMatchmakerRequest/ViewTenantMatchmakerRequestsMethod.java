package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest;

import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantMatchmakerRequestsMethod {
    Uni<ViewTenantMatchmakerRequestsResponse> execute(ViewTenantMatchmakerRequestsRequest request);
}
