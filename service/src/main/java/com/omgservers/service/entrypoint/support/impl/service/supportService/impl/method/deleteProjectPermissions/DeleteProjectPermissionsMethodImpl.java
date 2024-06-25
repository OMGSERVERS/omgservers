package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteProjectPermissions;

import com.omgservers.model.dto.support.DeleteProjectPermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteProjectPermissionsSupportResponse;
import com.omgservers.model.dto.tenant.DeleteProjectPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteProjectPermissionResponse;
import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsResponse;
import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteProjectPermissionsMethodImpl implements DeleteProjectPermissionsMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    @Override
    public Uni<DeleteProjectPermissionsSupportResponse> deleteProjectPermissions(
            final DeleteProjectPermissionsSupportRequest request) {
        log.debug("Delete project permissions, request={}", request);

        final var userId = request.getUserId();
        final var tenantId = request.getTenantId();
        final var projectId = request.getProjectId();

        return getUser(userId)
                .flatMap(user -> getTenant(tenantId)
                        .flatMap(tenant -> getProject(tenantId, projectId))
                        .flatMap(project -> viewProjectPermissions(tenantId, projectId)
                                .flatMap(projectPermissions -> {
                                    final var requestPermissionToDelete = request.getPermissionsToDelete();

                                    final var userPermissionsToDelete = projectPermissions.stream()
                                            .filter(permission -> permission.getUserId().equals(userId))
                                            .filter(permission -> requestPermissionToDelete.contains(
                                                    permission.getPermission()))
                                            .toList();

                                    return Multi.createFrom().iterable(userPermissionsToDelete)
                                            .onItem().transformToUniAndConcatenate(permission ->
                                                    deleteProjectPermission(tenantId, permission.getId())
                                                            .map(deleted -> Tuple2.of(permission.getPermission(),
                                                                    deleted)))
                                            .collect().asList();
                                })))
                .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1).toList())
                .map(DeleteProjectPermissionsSupportResponse::new);
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

    Uni<List<ProjectPermissionModel>> viewProjectPermissions(final Long tenantId,
                                                             final Long projectId) {
        final var request = new ViewProjectPermissionsRequest(tenantId, projectId);
        return tenantModule.getProjectService().viewProjectPermissions(request)
                .map(ViewProjectPermissionsResponse::getProjectPermissions);
    }

    Uni<Boolean> deleteProjectPermission(final Long tenantId, final Long id) {
        final var request = new DeleteProjectPermissionRequest(tenantId, id);
        return tenantModule.getProjectService().deleteProjectPermission(request)
                .map(DeleteProjectPermissionResponse::getDeleted);
    }
}
