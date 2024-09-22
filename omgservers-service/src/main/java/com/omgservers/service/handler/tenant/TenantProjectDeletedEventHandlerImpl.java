package com.omgservers.service.handler.tenant;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantProjectDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
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
public class TenantProjectDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_PROJECT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantProjectDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantProject(tenantId, id)
                .flatMap(tenantProject -> {
                    log.info("Tenant project was deleted, tenantProject={}/{}", tenantId, id);

                    return deleteProjectPermissions(tenantId, id)
                            .flatMap(voidItem -> deleteTenantStages(tenantId, id));
                })
                .replaceWithVoid();
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantProject(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }

    Uni<Void> deleteProjectPermissions(final Long tenantId, final Long projectId) {
        return viewProjectPermissions(tenantId, projectId)
                .flatMap(projectPermissions -> Multi.createFrom().iterable(projectPermissions)
                        .onItem().transformToUniAndConcatenate(projectPermission ->
                                deleteProjectPermission(tenantId, projectPermission.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete project permission failed, " +
                                                            "project={}/{}, " +
                                                            "projectPermissionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    projectId,
                                                    projectPermission.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantProjectPermissionModel>> viewProjectPermissions(final Long tenantId, final Long projectId) {
        final var request = new ViewTenantProjectPermissionsRequest(tenantId, projectId);
        return tenantModule.getTenantService().viewTenantProjectPermissions(request)
                .map(ViewTenantProjectPermissionsResponse::getTenantProjectPermissions);
    }

    Uni<Boolean> deleteProjectPermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantProjectPermissionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantProjectPermission(request)
                .map(DeleteTenantProjectPermissionResponse::getDeleted);
    }

    Uni<Void> deleteTenantStages(final Long tenantId, final Long tenantProjectId) {
        return viewTenantStages(tenantId, tenantProjectId)
                .flatMap(tenantStages -> Multi.createFrom().iterable(tenantStages)
                        .onItem().transformToUniAndConcatenate(tenantStage ->
                                deleteTenantStage(tenantId, tenantStage.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete tenant stage failed, " +
                                                            "tenantProject={}/{}, " +
                                                            "tenantStageId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantProjectId,
                                                    tenantStage.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantStageModel>> viewTenantStages(final Long tenantId, final Long tenantProjectId) {
        final var request = new ViewTenantStagesRequest(tenantId, tenantProjectId);
        return tenantModule.getTenantService().viewTenantStages(request)
                .map(ViewTenantStagesResponse::getTenantStages);
    }

    Uni<Boolean> deleteTenantStage(final Long tenantId, final Long id) {
        final var request = new DeleteTenantStageRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantStage(request)
                .map(DeleteTenantStageResponse::getDeleted);
    }
}
