package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantMatchmakerRefMethod {
    Uni<FindTenantMatchmakerRefResponse> execute(FindTenantMatchmakerRefRequest request);
}
