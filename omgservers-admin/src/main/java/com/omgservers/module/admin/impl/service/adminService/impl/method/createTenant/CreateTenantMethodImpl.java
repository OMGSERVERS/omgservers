package com.omgservers.module.admin.impl.service.adminService.impl.method.createTenant;

import com.omgservers.dto.admin.CreateTenantAdminRequest;
import com.omgservers.dto.admin.CreateTenantAdminResponse;
import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.module.tenant.factory.TenantModelFactory;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.operation.generateId.GenerateIdOperation;
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
        CreateTenantAdminRequest.validate(request);

        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        final var syncTenantInternalRequest = new SyncTenantShardedRequest(tenant);
        return tenantModule.getTenantShardedService().syncTenant(syncTenantInternalRequest)
                .replaceWith(new CreateTenantAdminResponse(tenant.getId()));
    }
}
