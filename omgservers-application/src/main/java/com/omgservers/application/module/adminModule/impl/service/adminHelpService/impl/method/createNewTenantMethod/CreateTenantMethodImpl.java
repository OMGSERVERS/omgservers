package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewTenantMethod;

import com.omgservers.base.factory.TenantModelFactory;
import com.omgservers.dto.adminModule.CreateTenantAdminRequest;
import com.omgservers.dto.adminModule.CreateTenantAdminResponse;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import com.omgservers.dto.tenantModule.SyncTenantInternalRequest;
import com.omgservers.model.tenant.TenantConfigModel;
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
        final var syncTenantInternalRequest = new SyncTenantInternalRequest(tenant);
        return tenantModule.getTenantInternalService().syncTenant(syncTenantInternalRequest)
                .replaceWith(new CreateTenantAdminResponse(tenant.getId()));
    }
}
