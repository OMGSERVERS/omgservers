package com.omgservers.service.handler.operation;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsRequest;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteTenantProjectsOperationImpl implements DeleteTenantProjectsOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId) {
        return viewProjects(tenantId)
                .flatMap(tenantProjects -> Multi.createFrom().iterable(tenantProjects)
                        .onItem().transformToUniAndConcatenate(tenantProject ->
                                deleteProject(tenantId, tenantProject.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant project, " +
                                                            "tenantId={}, " +
                                                            "tenantProjectId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantProject.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantProjectModel>> viewProjects(final Long tenantId) {
        final var request = new ViewTenantProjectsRequest(tenantId);
        return tenantModule.getTenantService().viewTenantProjects(request)
                .map(ViewTenantProjectsResponse::getTenantProjects);
    }

    Uni<Boolean> deleteProject(final Long tenantId, final Long id) {
        final var request = new DeleteTenantProjectRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantProject(request)
                .map(DeleteTenantProjectResponse::getDeleted);
    }
}
