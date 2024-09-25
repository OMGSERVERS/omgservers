package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createTenantPermissions;

import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.tenantPermission.SyncTenantPermissionRequest;
import com.omgservers.schema.module.tenant.tenantPermission.SyncTenantPermissionResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.service.factory.tenant.TenantPermissionModelFactory;
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
class CreateTenantPermissionsMethodImpl implements CreateTenantPermissionsMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    final TenantPermissionModelFactory tenantPermissionModelFactory;

    @Override
    public Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(
            final CreateTenantPermissionsSupportRequest request) {
        log.debug("Create tenant permissions, request={}", request);

        final var userId = request.getUserId();
        final var tenantId = request.getTenantId();
        return getUser(userId)
                .flatMap(user -> getTenant(tenantId)
                        .flatMap(tenant -> {
                            final var permissionsToCreate = request.getPermissionsToCreate();
                            return Multi.createFrom().iterable(permissionsToCreate)
                                    .onItem().transformToUniAndConcatenate(permission ->
                                            createTenantPermission(tenantId, userId, permission)
                                                    .map(created -> Tuple2.of(permission, created)))
                                    .collect().asList();
                        }))
                .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1).toList())
                .map(CreateTenantPermissionsSupportResponse::new);
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

    Uni<Boolean> createTenantPermission(final Long tenantId,
                                        final Long userId,
                                        final TenantPermissionQualifierEnum permission) {
        return syncTenantPermission(tenantId,
                userId,
                permission)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Sync tenant permission was failed, " +
                                    "tenantId={}, userId={}, permission={}, {}:{}",
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
        return tenantModule.getTenantService().syncTenantPermission(syncTenantPermissionServiceRequest)
                .map(SyncTenantPermissionResponse::getCreated);
    }
}
