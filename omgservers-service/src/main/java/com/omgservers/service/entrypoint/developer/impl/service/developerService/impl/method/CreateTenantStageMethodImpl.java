package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CreateTenantStagePermissionOperation;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.service.operation.getIdByProject.GetIdByProjectOperation;
import com.omgservers.service.operation.getIdByTenant.GetIdByTenantOperation;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
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

    final TenantModule tenantModule;
    final UserModule userModule;

    final GenerateIdOperation generateIdOperation;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final CreateTenantStagePermissionOperation createTenantStagePermissionOperation;
    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantStageModelFactory tenantStageModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantStageDeveloperResponse> execute(final CreateTenantStageDeveloperRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> {
                                final var permissionQualifier =
                                        TenantProjectPermissionQualifierEnum.STAGE_MANAGEMENT;
                                return checkTenantProjectPermissionOperation.execute(tenantId,
                                                tenantProjectId,
                                                userId,
                                                permissionQualifier)
                                        .flatMap(voidItem -> createTenantStage(tenantId, tenantProjectId, userId)
                                                .map(tenantStage -> {
                                                    final var tenantStageId = tenantStage.getId();
                                                    final var tenantStageSecret = tenantStage.getSecret();

                                                    log.info("The new stage \"{}\" was created in tenant \"{}\" " +
                                                            "by the user {}", tenantStageId, tenantId, userId);

                                                    return new CreateTenantStageDeveloperResponse(tenantStageId,
                                                            tenantStageSecret);
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
        return tenantModule.getService().syncTenantStage(request)
                .flatMap(response -> createTenantStagePermissionOperation.execute(tenantId, tenantStageId, userId,
                        TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGEMENT))
                .replaceWith(tenantStage);
    }
}
