package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.shard.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.SyncTenantStagePermissionResponse;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.service.factory.tenant.TenantStagePermissionModelFactory;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByStageOperation;
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
class CreateTenantStagePermissionsMethodImpl implements CreateTenantStagePermissionsMethod {

    final TenantShard tenantShard;
    final UserShard userShard;

    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByStageOperation getIdByStageOperation;
    final GetIdByUserOperation getIdByUserOperation;

    final TenantStagePermissionModelFactory tenantStagePermissionModelFactory;

    @Override
    public Uni<CreateTenantStagePermissionsSupportResponse> execute(
            final CreateTenantStagePermissionsSupportRequest request) {
        log.info("Requested, {}", request);

        return getIdByTenantOperation.execute(request.getTenant())
                .flatMap(tenantId -> getIdByProjectOperation.execute(tenantId, request.getProject())
                        .flatMap(tenantProjectId -> getIdByStageOperation.execute(tenantId,
                                        tenantProjectId,
                                        request.getStage())
                                .flatMap(tenantStageId -> getIdByUserOperation.execute(request.getUser())
                                        .flatMap(userId -> getUser(userId)
                                                .flatMap(user -> getTenant(tenantId)
                                                        .flatMap(project -> getTenantStage(tenantId,
                                                                tenantStageId))
                                                        .flatMap(stage -> {
                                                            final var permissionsToCreate =
                                                                    request.getPermissionsToCreate();
                                                            return Multi.createFrom()
                                                                    .iterable(permissionsToCreate)
                                                                    .onItem()
                                                                    .transformToUniAndConcatenate(permission ->
                                                                            createStagePermission(tenantId,
                                                                                    tenantStageId,
                                                                                    userId, permission)
                                                                                    .map(created -> Tuple2.of(
                                                                                            permission,
                                                                                            created)))
                                                                    .collect().asList();
                                                        }))
                                                .map(results -> results.stream().filter(Tuple2::getItem2)
                                                        .map(Tuple2::getItem1).toList())
                                                .invoke(createdPermissions -> {
                                                    if (createdPermissions.size() > 0) {
                                                        log.info("Created \"{}\" stage permissions " +
                                                                        "in tenant \"{}\" for user \"{}\"",
                                                                createdPermissions.size(), tenantId, userId);
                                                    }
                                                })))))
                .map(CreateTenantStagePermissionsSupportResponse::new);
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userShard.getService().execute(request)
                .map(GetUserResponse::getUser);
    }

    Uni<TenantModel> getTenant(final Long tenantId) {
        final var getTenantRequest = new GetTenantRequest(tenantId);
        return tenantShard.getService().execute(getTenantRequest)
                .map(GetTenantResponse::getTenant);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Boolean> createStagePermission(final Long tenantId,
                                       final Long tenantStageId,
                                       final Long userId,
                                       final TenantStagePermissionQualifierEnum permission) {
        return syncStagePermission(tenantId,
                tenantStageId,
                userId,
                permission)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Failed to sync stage permission, " +
                                    "tenantId={}, tenantStageId={}, userId={}, permission={}, {}:{}",
                            tenantId,
                            tenantStageId,
                            userId,
                            permission,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                });
    }

    Uni<Boolean> syncStagePermission(final Long tenantId,
                                     final Long tenantStageId,
                                     final Long userId,
                                     final TenantStagePermissionQualifierEnum permission) {
        final var stagePermission = tenantStagePermissionModelFactory
                .create(tenantId, tenantStageId, userId, permission);

        final var request = new SyncTenantStagePermissionRequest(stagePermission);
        return tenantShard.getService().execute(request)
                .map(SyncTenantStagePermissionResponse::getCreated);
    }
}
