package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createStagePermissions;

import com.omgservers.schema.entrypoint.support.CreateStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateStagePermissionsSupportResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.service.factory.tenant.TenantStagePermissionModelFactory;
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
class CreateStagePermissionsMethodImpl implements CreateStagePermissionsMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    final TenantStagePermissionModelFactory tenantStagePermissionModelFactory;

    @Override
    public Uni<CreateStagePermissionsSupportResponse> createStagePermissions(
            final CreateStagePermissionsSupportRequest request) {
        log.debug("Create stage permissions, request={}", request);

        final var userId = request.getUserId();
        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        return getUser(userId)
                .flatMap(user -> getTenant(tenantId)
                        .flatMap(project -> getStage(tenantId, stageId))
                        .flatMap(stage -> {
                            final var permissionsToCreate = request.getPermissionsToCreate();
                            return Multi.createFrom().iterable(permissionsToCreate)
                                    .onItem().transformToUniAndConcatenate(permission ->
                                            createStagePermission(tenantId, stageId, userId, permission)
                                                    .map(created -> Tuple2.of(permission, created)))
                                    .collect().asList();
                        }))
                .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1).toList())
                .map(CreateStagePermissionsSupportResponse::new);
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

    Uni<TenantStageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getTenantService().getStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Boolean> createStagePermission(final Long tenantId,
                                       final Long stageId,
                                       final Long userId,
                                       final TenantStagePermissionEnum permission) {
        return syncStagePermission(tenantId,
                stageId,
                userId,
                permission)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Sync stage permission was failed, " +
                                    "tenantId={}, stageId={}, userId={}, permission={}, {}:{}",
                            tenantId,
                            stageId,
                            userId,
                            permission,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                });
    }

    Uni<Boolean> syncStagePermission(final Long tenantId,
                                     final Long stageId,
                                     final Long userId,
                                     final TenantStagePermissionEnum permission) {
        final var stagePermission = tenantStagePermissionModelFactory
                .create(tenantId, stageId, userId, permission);

        final var request = new SyncTenantStagePermissionRequest(stagePermission);
        return tenantModule.getTenantService().syncTenantStagePermission(request)
                .map(SyncTenantStagePermissionResponse::getCreated);
    }
}
