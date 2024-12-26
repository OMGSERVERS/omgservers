package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperResponse;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantStagePermissionOperation;
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
class DeleteTenantDeploymentMethodImpl implements DeleteTenantDeploymentMethod {

    final TenantModule tenantModule;

    final CheckTenantStagePermissionOperation checkTenantStagePermissionOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteTenantDeploymentDeveloperResponse> execute(final DeleteTenantDeploymentDeveloperRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantDeploymentId = request.getId();

        return getTenantDeployment(tenantId, tenantDeploymentId)
                .flatMap(tenantDeployment -> {
                    final var tenantStageId = tenantDeployment.getStageId();

                    final var permissionQualifier =
                            TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER;
                    return checkTenantStagePermissionOperation.execute(tenantId,
                                    tenantStageId,
                                    userId,
                                    permissionQualifier)
                            .flatMap(voidItem -> deleteTenantDeployment(tenantId, tenantDeploymentId))
                            .invoke(deleted -> {
                                if (deleted) {
                                    log.info("Deployment \"{}\" was deleted in tenant \"{}\" by the user {}",
                                            tenantDeploymentId, tenantId, userId);
                                }
                            })
                            .map(DeleteTenantDeploymentDeveloperResponse::new);
                });
    }

    Uni<TenantDeploymentModel> getTenantDeployment(Long tenantId, Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<Boolean> deleteTenantDeployment(final Long tenantId,
                                        final Long id) {
        final var request = new DeleteTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().deleteTenantDeployment(request)
                .map(DeleteTenantDeploymentResponse::getDeleted);
    }
}
