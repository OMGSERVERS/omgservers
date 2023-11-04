package com.omgservers.module.admin.impl.service.adminService.impl.method.createTenant;

import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateTenantMethod {
    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    default CreateTenantAdminResponse createTenant(long timeout, CreateTenantAdminRequest request) {
        return createTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
