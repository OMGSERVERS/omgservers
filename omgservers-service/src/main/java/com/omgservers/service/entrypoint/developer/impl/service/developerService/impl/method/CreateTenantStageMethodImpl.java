package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateStageDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CreateTenantStagePermissionOperation;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTenantStageMethodImpl implements CreateTenantStageMethod {

    final TenantShard tenantShard;
    final UserShard userShard;

    final GenerateIdOperation generateIdOperation;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final CreateTenantStagePermissionOperation createTenantStagePermissionOperation;
    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantStageModelFactory tenantStageModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateStageDeveloperResponse> execute(final CreateStageDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> {
                                final var permissionQualifier =
                                        TenantProjectPermissionQualifierEnum.STAGE_MANAGER;
                                return checkTenantProjectPermissionOperation.execute(tenantId,
                                                tenantProjectId,
                                                userId,
                                                permissionQualifier)
                                        .flatMap(voidItem -> createTenantStage(tenantId, tenantProjectId, userId)
                                                .map(tenantStage -> {
                                                    final var tenantStageId = tenantStage.getId();

                                                    log.info("New stage \"{}\" created in tenant \"{}\"",
                                                            tenantStageId, tenantId);

                                                    return new CreateStageDeveloperResponse(tenantStageId);
                                                }));
                            });
                });
    }

    Uni<TenantStageModel> createTenantStage(final Long tenantId,
                                            final Long tenantProjectId,
                                            final Long userId) {
        final var tenantStage = tenantStageModelFactory.create(tenantId, tenantProjectId);
        final var tenantStageId = tenantStage.getId();
        final var request = new SyncTenantStageRequest(tenantStage);
        return tenantShard.getService().execute(request)
                .flatMap(response -> createTenantStagePermissionOperation.execute(tenantId, tenantStageId, userId,
                        TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER))
                .replaceWith(tenantStage);
    }
}
