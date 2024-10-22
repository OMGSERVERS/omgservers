package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperResponse;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.dto.TenantDeploymentDataDto;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantDeploymentMapper;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
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
class GetTenantDeploymentDashboardMethodImpl implements GetTenantDeploymentDashboardMethod {

    final TenantModule tenantModule;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;

    final TenantDeploymentMapper tenantDeploymentMapper;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetTenantDeploymentDashboardDeveloperResponse> execute(
            final GetTenantDeploymentDashboardDeveloperRequest request) {
        log.info("Requested, {}, principal={}",
                request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantDeploymentId = request.getDeploymentId();
        return getTenantDeployment(tenantId, tenantDeploymentId)
                .flatMap(tenantDeployment -> {
                    final var tenantStageId = tenantDeployment.getStageId();
                    return getTenantStage(tenantId, tenantStageId)
                            .flatMap(tenantStage -> {
                                final var tenantProjectId = tenantStage.getProjectId();
                                final var permissionQualifier =
                                        TenantProjectPermissionQualifierEnum.GETTING_DASHBOARD;
                                return checkTenantProjectPermissionOperation.execute(tenantId,
                                                tenantProjectId,
                                                userId,
                                                permissionQualifier)
                                        .flatMap(voidItem -> getTenantDeploymentData(tenantId, tenantDeploymentId))
                                        .map(tenantDeploymentMapper::dataToDashboard)
                                        .map(GetTenantDeploymentDashboardDeveloperResponse::new);
                            });
                });
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<TenantDeploymentDataDto> getTenantDeploymentData(final Long tenantId, final Long tenantDeploymentId) {
        final var request = new GetTenantDeploymentDataRequest(tenantId, tenantDeploymentId);
        return tenantModule.getService().getTenantDeploymentData(request)
                .map(GetTenantDeploymentDataResponse::getTenantDeploymentData);
    }
}
