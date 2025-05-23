package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.shard.tenant.tenantPermission.SyncTenantPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.SyncTenantPermissionResponse;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.service.factory.tenant.TenantPermissionModelFactory;
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

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantPermissionsMethodImpl implements CreateTenantPermissionsMethod {

    final TenantShard tenantShard;
    final UserShard userShard;

    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByUserOperation getIdByUserOperation;

    final TenantPermissionModelFactory tenantPermissionModelFactory;

    @Override
    public Uni<CreateTenantPermissionsSupportResponse> execute(
            final CreateTenantPermissionsSupportRequest request) {
        log.info("Requested, {}", request);

        return getIdByTenantOperation.execute(request.getTenant())
                .flatMap(tenantId -> getIdByUserOperation.execute(request.getUser())
                        .flatMap(userId -> getUser(userId)
                                .flatMap(user -> getTenant(tenantId)
                                        .flatMap(tenant -> {
                                            final var permissionsToCreate = request
                                                    .getPermissionsToCreate();
                                            return Multi.createFrom().iterable(permissionsToCreate)
                                                    .onItem().transformToUniAndConcatenate(permission ->
                                                            createTenantPermission(tenantId, userId, permission)
                                                                    .map(created -> Tuple2.of(permission,
                                                                            created)))
                                                    .collect().asList();
                                        }))
                                .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1)
                                        .toList())
                                .invoke(createdPermissions -> {
                                    if (createdPermissions.size() > 0) {
                                        log.info("Created \"{}\" tenant permissions in tenant \"{}\" " +
                                                        "for user \"{}\"",
                                                createdPermissions.size(), tenantId, userId);
                                    }
                                })))
                .map(CreateTenantPermissionsSupportResponse::new);
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

    Uni<Boolean> createTenantPermission(final Long tenantId,
                                        final Long userId,
                                        final TenantPermissionQualifierEnum permission) {
        return syncTenantPermission(tenantId,
                userId,
                permission)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Failed to sync tenant permission, tenantId={}, userId={}, permission={}, {}:{}",
                            tenantId,
                            userId,
                            permission,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                });
    }

    Uni<Boolean> syncTenantPermission(final Long tenantId,
                                      final Long userId,
                                      final TenantPermissionQualifierEnum permission) {
        final var tenantPermission = tenantPermissionModelFactory
                .create(tenantId, userId, permission);

        final var syncTenantPermissionServiceRequest = new SyncTenantPermissionRequest(tenantPermission);
        return tenantShard.getService().execute(syncTenantPermissionServiceRequest)
                .map(SyncTenantPermissionResponse::getCreated);
    }
}
