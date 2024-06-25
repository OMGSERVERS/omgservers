package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createStagePermissions;

import com.omgservers.model.dto.support.CreateStagePermissionsSupportRequest;
import com.omgservers.model.dto.support.CreateStagePermissionsSupportResponse;
import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.model.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.service.factory.tenant.StagePermissionModelFactory;
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

    final StagePermissionModelFactory stagePermissionModelFactory;

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

    Uni<StageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetStageRequest(tenantId, id);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    Uni<Boolean> createStagePermission(final Long tenantId,
                                       final Long stageId,
                                       final Long userId,
                                       final StagePermissionEnum permission) {
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
                                     final StagePermissionEnum permission) {
        final var stagePermission = stagePermissionModelFactory
                .create(tenantId, stageId, userId, permission);

        final var request = new SyncStagePermissionRequest(stagePermission);
        return tenantModule.getStageService().syncStagePermission(request)
                .map(SyncStagePermissionResponse::getCreated);
    }
}
