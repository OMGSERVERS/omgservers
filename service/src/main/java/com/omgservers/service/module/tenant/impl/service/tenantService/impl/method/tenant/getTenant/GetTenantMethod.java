package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.getTenant;

import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetTenantMethod {
    Uni<GetTenantResponse> getTenant(GetTenantRequest request);
}
