package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.getTenant;

import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.GetTenantShardedResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetTenantMethod {
    Uni<GetTenantShardedResponse> getTenant(GetTenantShardedRequest request);

    default GetTenantShardedResponse getTenant(long timeout, GetTenantShardedRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
