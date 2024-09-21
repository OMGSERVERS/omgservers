package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest;

import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantMatchmakerRequestMethod {
    Uni<GetTenantMatchmakerRequestResponse> execute(GetTenantMatchmakerRequestRequest request);
}
