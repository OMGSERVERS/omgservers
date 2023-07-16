package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.getTenantServiceMethod;

import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.GetTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.GetTenantResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetTenantServiceMethod {
    Uni<GetTenantResponse> getTenant(GetTenantInternalRequest request);

    default GetTenantResponse getTenant(long timeout, GetTenantInternalRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
