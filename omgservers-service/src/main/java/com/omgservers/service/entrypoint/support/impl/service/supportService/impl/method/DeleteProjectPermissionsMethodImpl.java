package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportResponse;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
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
    public Uni<DeleteProjectPermissionsSupportResponse> execute(
            final DeleteProjectPermissionsSupportRequest request) {
        log.debug("Delete project permissions, request={}", request);

        final var userId = request.getUserId();
        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getProjectId();

        return getUser(userId)
                .flatMap(user -> getTenant(tenantId)
                        .flatMap(tenant -> getTenantProject(tenantId, tenantProjectId))
                        .flatMap(project -> viewTenantProjectPermissions(tenantId, tenantProjectId)
                                .flatMap(projectPermissions -> {
                                    final var requestPermissionToDelete = request.getPermissionsToDelete();

                                    final var userPermissionsToDelete = projectPermissions.stream()
                                            .filter(permission -> permission.getUserId().equals(userId))
                                            .filter(permission -> requestPermissionToDelete.contains(
                                                    permission.getPermission()))
                                            .toList();

                                    return Multi.createFrom().iterable(userPermissionsToDelete)
                                            .onItem().transformToUniAndConcatenate(permission ->
                                                    deleteTenantProjectPermission(tenantId, permission.getId())
                                                            .map(deleted -> Tuple2.of(permission.getPermission(),
                                                                    deleted)))
                                            .collect().asList();
                                })))
                .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1).toList())
                .map(DeleteProjectPermissionsSupportResponse::new);
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userModule.getService().getUser(request)
                .map(GetUserResponse::getUser);
    }

    Uni<TenantModel> getTenant(Long tenantId) {
        final var getTenantRequest = new GetTenantRequest(tenantId);
        return tenantModule.getService().getTenant(getTenantRequest)
                .map(GetTenantResponse::getTenant);
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantModule.getService().getTenantProject(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }

    Uni<List<TenantProjectPermissionModel>> viewTenantProjectPermissions(final Long tenantId,
                                                                         final Long tenantProjectId) {
        final var request = new ViewTenantProjectPermissionsRequest(tenantId, tenantProjectId);
        return tenantModule.getService().viewTenantProjectPermissions(request)
                .map(ViewTenantProjectPermissionsResponse::getTenantProjectPermissions);
    }

    Uni<Boolean> deleteTenantProjectPermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantProjectPermissionRequest(tenantId, id);
        return tenantModule.getService().deleteTenantProjectPermission(request)
                .map(DeleteTenantProjectPermissionResponse::getDeleted);
    }
}
