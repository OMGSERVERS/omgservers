package com.omgservers.module.tenant.impl.service.tenantService.impl.method.getTenant;

import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetTenantMethod {
    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    default GetTenantResponse getTenant(long timeout, GetTenantRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
