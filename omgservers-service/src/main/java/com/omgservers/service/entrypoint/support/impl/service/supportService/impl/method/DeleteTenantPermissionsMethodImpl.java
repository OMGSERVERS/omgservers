package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.shard.tenant.tenantPermission.DeleteTenantPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.DeleteTenantPermissionResponse;
import com.omgservers.schema.shard.tenant.tenantPermission.ViewTenantPermissionsRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.ViewTenantPermissionsResponse;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
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
class DeleteTenantPermissionsMethodImpl implements DeleteTenantPermissionsMethod {

    final TenantShard tenantShard;
    final UserShard userShard;

    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByUserOperation getIdByUserOperation;

    @Override
    public Uni<DeleteTenantPermissionsSupportResponse> execute(
            final DeleteTenantPermissionsSupportRequest request) {
        log.info("Requested, {}", request);

        return getIdByTenantOperation.execute(request.getTenant())
                .flatMap(tenantId -> {
                    return getIdByUserOperation.execute(request.getUser())
                            .flatMap(userId -> getUser(userId)
                                    .flatMap(user -> getTenant(tenantId)
                                            .flatMap(tenant -> viewTenantPermissions(tenantId)
                                                    .flatMap(tenantPermissions -> {
                                                        final var requestPermissionToDelete =
                                                                request.getPermissionsToDelete();

                                                        final var userPermissionsToDelete =
                                                                tenantPermissions.stream()
                                                                        .filter(permission -> permission.getUserId()
                                                                                .equals(userId))
                                                                        .filter(permission -> requestPermissionToDelete
                                                                                .contains(
                                                                                        permission.getPermission()))
                                                                        .toList();

                                                        return Multi.createFrom().iterable(userPermissionsToDelete)
                                                                .onItem().transformToUniAndConcatenate(permission ->
                                                                        deleteTenantPermission(tenantId,
                                                                                permission.getId())
                                                                                .map(deleted -> Tuple2.of(
                                                                                        permission.getPermission(),
                                                                                        deleted)))
                                                                .collect().asList();
                                                    })))
                                    .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1)
                                            .toList())
                                    .invoke(deletedPermissions -> {
                                        if (deletedPermissions.size() > 0) {
                                            log.info("Deleted \"{}\" tenant permissions " +
                                                            "in tenant \"{}\" for user \"{}\"",
                                                    deletedPermissions.size(), tenantId, userId);
                                        }
                                    }));
                })
                .map(DeleteTenantPermissionsSupportResponse::new);
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

    Uni<List<TenantPermissionModel>> viewTenantPermissions(final Long tenantId) {
        final var request = new ViewTenantPermissionsRequest(tenantId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantPermissionsResponse::getTenantPermissions);
    }

    Uni<Boolean> deleteTenantPermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantPermissionRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantPermissionResponse::getDeleted);
    }
}
