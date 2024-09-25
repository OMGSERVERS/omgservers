package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectResponse;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantProjectMethodImpl implements DeleteTenantProjectMethod {

    final TenantModule tenantModule;

    @Override
    public Uni<DeleteTenantProjectSupportResponse> execute(final DeleteTenantProjectSupportRequest request) {
        log.debug("Delete tenant project, request={}", request);

        final var tenantId = request.getTenantId();
        final var projectId = request.getTenantProjectId();
        final var deleteTenantRequest = new DeleteTenantProjectRequest(tenantId, projectId);
        return tenantModule.getTenantService().deleteTenantProject(deleteTenantRequest)
                .map(DeleteTenantProjectResponse::getDeleted)
                .map(DeleteTenantProjectSupportResponse::new);
    }
}
