package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantMatchmakerRefsMethod {
    Uni<ViewTenantMatchmakerRefsResponse> execute(ViewTenantMatchmakerRefsRequest request);
}
