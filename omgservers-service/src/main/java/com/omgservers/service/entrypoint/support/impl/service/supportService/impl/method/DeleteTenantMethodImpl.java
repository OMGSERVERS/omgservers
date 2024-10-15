package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import com.omgservers.schema.module.tenant.tenant.DeleteTenantRequest;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantMethodImpl implements DeleteTenantMethod {

    final TenantModule tenantModule;

    final GenerateIdOperation generateIdOperation;

    final TenantModelFactory tenantModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteTenantSupportResponse> execute(final DeleteTenantSupportRequest request) {
        log.info("Delete tenant, request={}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var tenantId = request.getTenantId();
        final var deleteTenantRequest = new DeleteTenantRequest(tenantId);
        return tenantModule.getService().deleteTenant(deleteTenantRequest)
                .map(deleteTenantResponse -> {
                    final var deleted = deleteTenantResponse.getDeleted();
                    return new DeleteTenantSupportResponse(deleted);
                });
    }
}
