package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportResponse;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.operation.alias.GetIdByUserOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
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

    final TenantShard tenantShard;
    final UserShard userShard;

    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByUserOperation getIdByUserOperation;

    @Override
    public Uni<DeleteTenantProjectPermissionsSupportResponse> execute(
            final DeleteTenantProjectPermissionsSupportRequest request) {
        log.info("Requested, {}", request);

        return getIdByTenantOperation.execute(request.getTenant())
                .flatMap(tenantId -> getIdByProjectOperation.execute(tenantId, request.getProject())
                        .flatMap(tenantProjectId -> getIdByUserOperation.execute(request.getUser())
                                .flatMap(userId -> getUser(userId)
                                        .flatMap(user -> getTenant(tenantId)
                                                .flatMap(tenant -> getTenantProject(tenantId, tenantProjectId))
                                                .flatMap(project -> viewTenantProjectPermissions(tenantId,
                                                        tenantProjectId)
                                                        .flatMap(projectPermissions -> {
                                                            final var requestPermissionToDelete =
                                                                    request.getPermissionsToDelete();

                                                            final var userPermissionsToDelete =
                                                                    projectPermissions.stream()
                                                                            .filter(permission -> permission.getUserId()
                                                                                    .equals(userId))
                                                                            .filter(permission -> requestPermissionToDelete.contains(
                                                                                    permission.getPermission()))
                                                                            .toList();

                                                            return Multi.createFrom()
                                                                    .iterable(userPermissionsToDelete)
                                                                    .onItem()
                                                                    .transformToUniAndConcatenate(permission ->
                                                                            deleteTenantProjectPermission(
                                                                                    tenantId,
                                                                                    permission.getId())
                                                                                    .map(deleted -> Tuple2.of(
                                                                                            permission.getPermission(),
                                                                                            deleted)))
                                                                    .collect().asList();
                                                        })))
                                        .map(results -> results.stream().filter(Tuple2::getItem2)
                                                .map(Tuple2::getItem1)
                                                .toList())
                                        .invoke(deletedPermissions -> {
                                            if (deletedPermissions.size() > 0) {
                                                log.info("Deleted \"{}\" project permissions " +
                                                                "in tenant \"{}\" for user \"{}\"",
                                                        deletedPermissions.size(), tenantId, userId);
                                            }
                                        }))))
                .map(DeleteTenantProjectPermissionsSupportResponse::new);
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userShard.getService().execute(request)
                .map(GetUserResponse::getUser);
    }

    Uni<TenantModel> getTenant(Long tenantId) {
        final var getTenantRequest = new GetTenantRequest(tenantId);
        return tenantShard.getService().execute(getTenantRequest)
                .map(GetTenantResponse::getTenant);
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }

    Uni<List<TenantProjectPermissionModel>> viewTenantProjectPermissions(final Long tenantId,
                                                                         final Long tenantProjectId) {
        final var request = new ViewTenantProjectPermissionsRequest(tenantId, tenantProjectId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantProjectPermissionsResponse::getTenantProjectPermissions);
    }

    Uni<Boolean> deleteTenantProjectPermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantProjectPermissionRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantProjectPermissionResponse::getDeleted);
    }
}
