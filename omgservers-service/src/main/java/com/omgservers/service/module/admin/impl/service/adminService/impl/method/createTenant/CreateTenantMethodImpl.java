package com.omgservers.service.module.admin.impl.service.adminService.impl.method.createTenant;

import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import com.omgservers.model.dto.tenant.SyncTenantRequest;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantMethodImpl implements CreateTenantMethod {

    final TenantModule tenantModule;

    final TenantModelFactory tenantModelFactory;

    final GenerateIdOperation generateIdOperation;

    @Override
    public Uni<CreateTenantAdminResponse> createTenant(final CreateTenantAdminRequest request) {
        final var tenant = tenantModelFactory.create();
        final var syncTenantInternalRequest = new SyncTenantRequest(tenant);
        return tenantModule.getTenantService().syncTenant(syncTenantInternalRequest)
                .replaceWith(new CreateTenantAdminResponse(tenant.getId()));
    }
}
