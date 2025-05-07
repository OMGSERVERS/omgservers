package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateStageDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageConfigDto;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CreateTenantStagePermissionOperation;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.operation.authz.AuthorizeTenantProjectRequestOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.tenant.TenantShard;
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

    final AuthorizeTenantProjectRequestOperation authorizeTenantProjectRequestOperation;
    final CreateTenantStagePermissionOperation createTenantStagePermissionOperation;

    final TenantStageModelFactory tenantStageModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateStageDeveloperResponse> execute(final CreateStageDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var userId = securityIdentity.<Long>getAttribute(
                SecurityAttributesEnum.USER_ID.getAttributeName());
        final var permissionQualifier = TenantProjectPermissionQualifierEnum.STAGE_MANAGER;

        return authorizeTenantProjectRequestOperation.execute(tenant, project, userId, permissionQualifier)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    final var tenantProjectId = authorization.tenantProjectId();
                    return createTenantStage(tenantId, tenantProjectId, userId)
                            .map(tenantStage -> {
                                final var tenantStageId = tenantStage.getId();

                                log.info("Created new stage \"{}\" in tenant \"{}\"",
                                        tenantStageId, tenantId);

                                return tenantStageId;
                            });
                })
                .map(CreateStageDeveloperResponse::new);
    }

    Uni<TenantStageModel> createTenantStage(final Long tenantId,
                                            final Long tenantProjectId,
                                            final Long userId) {
        final var tenantStage = tenantStageModelFactory.create(tenantId,
                tenantProjectId,
                TenantStageConfigDto.create());
        final var tenantStageId = tenantStage.getId();
        final var request = new SyncTenantStageRequest(tenantStage);
        return tenantShard.getService().execute(request)
                .flatMap(response -> createTenantStagePermissionOperation.execute(tenantId, tenantStageId, userId,
                        TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER))
                .flatMap(response -> createTenantStagePermissionOperation.execute(tenantId, tenantStageId, userId,
                        TenantStagePermissionQualifierEnum.STAGE_VIEWER))
                .replaceWith(tenantStage);
    }
}
