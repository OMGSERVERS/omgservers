package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
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
class DeleteTenantStageMethodImpl implements DeleteTenantStageMethod {

    final TenantModule tenantModule;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteTenantStageDeveloperResponse> execute(final DeleteTenantStageDeveloperRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getId();

        return getTenantStage(tenantId, tenantStageId)
                .flatMap(tenantStage -> {
                    final var stageProjectId = tenantStage.getProjectId();

                    final var permissionQualifier =
                            TenantProjectPermissionQualifierEnum.STAGE_MANAGEMENT;
                    return checkTenantProjectPermissionOperation.execute(tenantId,
                                    stageProjectId,
                                    userId,
                                    permissionQualifier)
                            .flatMap(voidItem -> deleteTenantStage(tenantId, tenantStageId))
                            .map(DeleteTenantStageDeveloperResponse::new);
                });
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageRequest(tenantId, tenantStageId);
        return tenantModule.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Boolean> deleteTenantStage(final Long tenantId,
                                   final Long id) {
        final var request = new DeleteTenantStageRequest(tenantId, id);
        return tenantModule.getService().deleteTenantStage(request)
                .map(DeleteTenantStageResponse::getDeleted);
    }
}
