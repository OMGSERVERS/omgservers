package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantMatchmakerRefMethod {
    Uni<GetTenantMatchmakerRefResponse> execute(GetTenantMatchmakerRefRequest request);
}
