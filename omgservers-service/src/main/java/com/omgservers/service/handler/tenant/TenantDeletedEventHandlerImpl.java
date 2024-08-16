package com.omgservers.service.handler.tenant;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.model.project.ProjectModel;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.schema.module.tenant.DeleteProjectRequest;
import com.omgservers.schema.module.tenant.DeleteProjectResponse;
import com.omgservers.schema.module.tenant.DeleteTenantPermissionRequest;
import com.omgservers.schema.module.tenant.DeleteTenantPermissionResponse;
import com.omgservers.schema.module.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.ViewProjectsRequest;
import com.omgservers.schema.module.tenant.ViewProjectsResponse;
import com.omgservers.schema.module.tenant.ViewTenantPermissionsRequest;
import com.omgservers.schema.module.tenant.ViewTenantPermissionsResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.DeleteJobRequest;
import com.omgservers.service.service.job.dto.DeleteJobResponse;
import com.omgservers.service.service.job.dto.FindJobRequest;
import com.omgservers.service.service.job.dto.FindJobResponse;
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
    final RootModule rootModule;

    final JobService jobService;

    final GetConfigOperation getConfigOperation;

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
                            .flatMap(voidItem -> deleteProjects(tenantId))
                            .flatMap(voidItem -> findAndDeleteRootTenantRef(tenantId))
                            .flatMap(voidItem -> findAndDeleteJob(tenantId));
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

    Uni<Void> findAndDeleteRootTenantRef(final Long tenantId) {
        final var rootId = getConfigOperation.getServiceConfig().defaults().rootId();
        return findRootEntityRef(rootId, tenantId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(rootEntityRef ->
                        deleteRootEntityRef(rootId, rootEntityRef.getId()))
                .replaceWithVoid();
    }

    Uni<RootEntityRefModel> findRootEntityRef(final Long rootId,
                                              final Long tenantId) {
        final var request = new FindRootEntityRefRequest(rootId, tenantId);
        return rootModule.getRootService().findRootEntityRef(request)
                .map(FindRootEntityRefResponse::getRootEntityRef);
    }

    Uni<Boolean> deleteRootEntityRef(final Long rootId, final Long id) {
        final var request = new DeleteRootEntityRefRequest(rootId, id);
        return rootModule.getRootService().deleteRootEntityRef(request)
                .map(DeleteRootEntityRefResponse::getDeleted);
    }

    Uni<Void> findAndDeleteJob(final Long tenantId) {
        return findJob(tenantId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(job -> deleteJob(job.getId()))
                .replaceWithVoid();
    }

    Uni<JobModel> findJob(final Long tenantId) {
        final var request = new FindJobRequest(tenantId, tenantId);
        return jobService.findJob(request)
                .map(FindJobResponse::getJob);
    }

    Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteJobRequest(id);
        return jobService.deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
