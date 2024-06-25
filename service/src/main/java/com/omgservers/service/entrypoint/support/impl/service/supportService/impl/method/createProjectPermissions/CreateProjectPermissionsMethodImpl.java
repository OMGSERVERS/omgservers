package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createProjectPermissions;

import com.omgservers.model.dto.support.CreateProjectPermissionsSupportRequest;
import com.omgservers.model.dto.support.CreateProjectPermissionsSupportResponse;
import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.model.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.service.factory.tenant.ProjectPermissionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateProjectPermissionsMethodImpl implements CreateProjectPermissionsMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    final ProjectPermissionModelFactory projectPermissionModelFactory;

    @Override
    public Uni<CreateProjectPermissionsSupportResponse> createProjectPermissions(
            final CreateProjectPermissionsSupportRequest request) {
        log.debug("Create project permissions, request={}", request);

        final var userId = request.getUserId();
        final var tenantId = request.getTenantId();
        final var projectId = request.getProjectId();
        return getUser(userId)
                .flatMap(user -> getTenant(tenantId)
                        .flatMap(tenant -> getProject(tenantId, projectId))
                        .flatMap(project -> {
                            final var permissionsToCreate = request.getPermissionsToCreate();
                            return Multi.createFrom().iterable(permissionsToCreate)
                                    .onItem().transformToUniAndConcatenate(permission ->
                                            createProjectPermission(tenantId, projectId, userId, permission)
                                                    .map(created -> Tuple2.of(permission, created)))
                                    .collect().asList();
                        }))
                .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1).toList())
                .map(CreateProjectPermissionsSupportResponse::new);
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userModule.getUserService().getUser(request)
                .map(GetUserResponse::getUser);
    }

    Uni<TenantModel> getTenant(Long tenantId) {
        final var getTenantRequest = new GetTenantRequest(tenantId);
        return tenantModule.getTenantService().getTenant(getTenantRequest)
                .map(GetTenantResponse::getTenant);
    }

    Uni<ProjectModel> getProject(final Long tenantId, final Long id) {
        final var request = new GetProjectRequest(tenantId, id);
        return tenantModule.getProjectService().getProject(request)
                .map(GetProjectResponse::getProject);
    }

    Uni<Boolean> createProjectPermission(final Long tenantId,
                                         final Long projectId,
                                         final Long userId,
                                         final ProjectPermissionEnum permission) {
        return syncProjectPermission(tenantId,
                projectId,
                userId,
                permission)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Sync project permission was failed, " +
                                    "tenantId={}, projectId={}, userId={}, permission={}, {}:{}",
                            tenantId,
                            projectId,
                            userId,
                            permission,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                });
    }

    Uni<Boolean> syncProjectPermission(final Long tenantId,
                                       final Long projectId,
                                       final Long userId,
                                       final ProjectPermissionEnum permission) {
        final var projectPermission = projectPermissionModelFactory
                .create(tenantId, projectId, userId, permission);

        final var request = new SyncProjectPermissionRequest(projectPermission);
        return tenantModule.getProjectService().syncProjectPermission(request)
                .map(SyncProjectPermissionResponse::getCreated);
    }
}
