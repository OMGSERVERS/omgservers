package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteProject;

import com.omgservers.schema.entrypoint.support.DeleteProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectSupportResponse;
import com.omgservers.schema.module.tenant.DeleteProjectRequest;
import com.omgservers.schema.module.tenant.DeleteProjectResponse;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteProjectMethodImpl implements DeleteProjectMethod {

    final TenantModule tenantModule;

    @Override
    public Uni<DeleteProjectSupportResponse> deleteProject(final DeleteProjectSupportRequest request) {
        log.debug("Delete project, request={}", request);

        final var tenantId = request.getTenantId();
        final var projectId = request.getProjectId();
        final var deleteTenantRequest = new DeleteProjectRequest(tenantId, projectId);
        return tenantModule.getProjectService().deleteProject(deleteTenantRequest)
                .map(DeleteProjectResponse::getDeleted)
                .map(DeleteProjectSupportResponse::new);
    }
}
