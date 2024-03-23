package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createTenant;

import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantMethod {
    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);
}
