package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.getTenantServiceMethod;

import com.omgservers.dto.tenantModule.GetTenantRoutedRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GetTenantServiceMethod {
    Uni<GetTenantResponse> getTenant(GetTenantRoutedRequest request);

    default GetTenantResponse getTenant(long timeout, GetTenantRoutedRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
