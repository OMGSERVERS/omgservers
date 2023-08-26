package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.getTenant;

import com.omgservers.dto.tenantModule.GetTenantShardRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetTenantMethod {
    Uni<GetTenantResponse> getTenant(GetTenantShardRequest request);

    default GetTenantResponse getTenant(long timeout, GetTenantShardRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
