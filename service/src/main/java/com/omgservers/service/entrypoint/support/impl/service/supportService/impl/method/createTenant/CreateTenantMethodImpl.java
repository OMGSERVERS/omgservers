package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createTenant;

import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import com.omgservers.model.dto.tenant.SyncTenantRequest;
import com.omgservers.service.factory.tenant.TenantModelFactory;
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
    public Uni<CreateTenantSupportResponse> createTenant(final CreateTenantSupportRequest request) {
        log.debug("Create tenant, request={}", request);

        final var tenant = tenantModelFactory.create();
        final var syncTenantInternalRequest = new SyncTenantRequest(tenant);
        return tenantModule.getTenantService().syncTenant(syncTenantInternalRequest)
                .replaceWith(new CreateTenantSupportResponse(tenant.getId()));
    }
}
