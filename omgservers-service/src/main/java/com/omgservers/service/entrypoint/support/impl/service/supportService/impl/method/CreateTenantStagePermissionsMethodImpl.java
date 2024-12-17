package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.service.factory.tenant.TenantStagePermissionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
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

    final TenantModule tenantModule;
    final UserModule userModule;

    final TenantStagePermissionModelFactory tenantStagePermissionModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantStagePermissionsSupportResponse> execute(
            final CreateTenantStagePermissionsSupportRequest request) {
        log.debug("Requested, {}, principal={}", request,
                securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var forUserId = request.getUserId();
        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getStageId();
        return getUser(forUserId)
                .flatMap(user -> getTenant(tenantId)
                        .flatMap(project -> getTenantStage(tenantId, tenantStageId))
                        .flatMap(stage -> {
                            final var permissionsToCreate = request.getPermissionsToCreate();
                            return Multi.createFrom().iterable(permissionsToCreate)
                                    .onItem().transformToUniAndConcatenate(permission ->
                                            createStagePermission(tenantId, tenantStageId, forUserId, permission)
                                                    .map(created -> Tuple2.of(permission, created)))
                                    .collect().asList();
                        }))
                .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1).toList())
                .invoke(createdPermissions -> {
                    if (createdPermissions.size() > 0) {
                        log.info("The {} stage permissions in tenant {} for user {} were created by user {}",
                                createdPermissions.size(), tenantId, forUserId, userId);
                    }
                })
                .map(CreateTenantStagePermissionsSupportResponse::new);
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

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getService().getTenantStage(request)
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
                    log.warn("Sync stage permission was failed, " +
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
        return tenantModule.getService().syncTenantStagePermission(request)
                .map(SyncTenantStagePermissionResponse::getCreated);
    }
}
