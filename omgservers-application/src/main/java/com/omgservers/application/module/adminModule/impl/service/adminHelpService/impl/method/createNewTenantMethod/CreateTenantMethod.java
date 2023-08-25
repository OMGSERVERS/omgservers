package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewTenantMethod;

import com.omgservers.dto.adminModule.CreateTenantAdminRequest;
import com.omgservers.dto.adminModule.CreateTenantAdminResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateTenantMethod {
    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    default CreateTenantAdminResponse createTenant(long timeout, CreateTenantAdminRequest request) {
        return createTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
