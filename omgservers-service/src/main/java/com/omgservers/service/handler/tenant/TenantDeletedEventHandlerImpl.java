package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.DeleteProjectRequest;
import com.omgservers.model.dto.tenant.DeleteProjectResponse;
import com.omgservers.model.dto.tenant.DeleteTenantPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteTenantPermissionResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.ViewProjectsRequest;
import com.omgservers.model.dto.tenant.ViewProjectsResponse;
import com.omgservers.model.dto.tenant.ViewTenantPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewTenantPermissionsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.TenantDeletedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.system.SystemModule;
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
public class TenantDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getId();

        return getTenant(tenantId)
                .flatMap(tenant -> {
                    log.info("Tenant was deleted, tenant={}", tenantId);

                    return deleteTenantPermissions(tenantId)
                            .flatMap(voidItem -> deleteProjects(tenantId));
                })
                .replaceWithVoid();
    }

    Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantModule.getTenantService().getTenant(request)
                .map(GetTenantResponse::getTenant);
    }

    Uni<Void> deleteTenantPermissions(final Long tenantId) {
        return viewTenantPermissions(tenantId)
                .flatMap(tenantPermissions -> Multi.createFrom().iterable(tenantPermissions)
                        .onItem().transformToUniAndConcatenate(tenantPermission ->
                                deleteTenantPermission(tenantId, tenantPermission.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete tenant permission failed, " +
                                                            "tenantId={}, " +
                                                            "tenantPermissionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantPermission.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantPermissionModel>> viewTenantPermissions(final Long tenantId) {
        final var request = new ViewTenantPermissionsRequest(tenantId);
        return tenantModule.getTenantService().viewTenantPermissions(request)
                .map(ViewTenantPermissionsResponse::getTenantPermissions);
    }

    Uni<Boolean> deleteTenantPermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantPermissionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantPermission(request)
                .map(DeleteTenantPermissionResponse::getDeleted);
    }

    Uni<Void> deleteProjects(final Long tenantId) {
        return viewProjects(tenantId)
                .flatMap(projects -> Multi.createFrom().iterable(projects)
                        .onItem().transformToUniAndConcatenate(project ->
                                deleteProject(tenantId, project.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete project failed, " +
                                                            "tenantId={}, " +
                                                            "projectId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    project.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<ProjectModel>> viewProjects(final Long tenantId) {
        final var request = new ViewProjectsRequest(tenantId);
        return tenantModule.getProjectService().viewProjects(request)
                .map(ViewProjectsResponse::getProjects);
    }

    Uni<Boolean> deleteProject(final Long tenantId, final Long id) {
        final var request = new DeleteProjectRequest(tenantId, id);
        return tenantModule.getProjectService().deleteProject(request)
                .map(DeleteProjectResponse::getDeleted);
    }
}
