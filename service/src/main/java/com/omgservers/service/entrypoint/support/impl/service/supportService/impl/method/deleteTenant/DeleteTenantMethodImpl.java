package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteTenant;

import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantSupportResponse;
import com.omgservers.model.dto.tenant.DeleteTenantRequest;
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
class DeleteTenantMethodImpl implements DeleteTenantMethod {

    final TenantModule tenantModule;

    final TenantModelFactory tenantModelFactory;

    final GenerateIdOperation generateIdOperation;

    @Override
    public Uni<DeleteTenantSupportResponse> deleteTenant(final DeleteTenantSupportRequest request) {
        log.debug("Delete tenant, request={}", request);

        final var tenantId = request.getTenantId();
        final var deleteTenantRequest = new DeleteTenantRequest(tenantId);
        return tenantModule.getTenantService().deleteTenant(deleteTenantRequest)
                .map(deleteTenantResponse -> {
                    final var deleted = deleteTenantResponse.getDeleted();
                    return new DeleteTenantSupportResponse(deleted);
                });
    }
}
