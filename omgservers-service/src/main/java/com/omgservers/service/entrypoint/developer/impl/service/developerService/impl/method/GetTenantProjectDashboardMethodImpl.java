package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataResponse;
import com.omgservers.schema.module.tenant.tenantProject.dto.TenantProjectDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantProjectMapper;
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
class GetTenantProjectDashboardMethodImpl implements GetTenantProjectDashboardMethod {

    final TenantModule tenantModule;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final TenantProjectMapper tenantProjectMapper;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetTenantProjectDashboardDeveloperResponse> execute(
            final GetTenantProjectDashboardDeveloperRequest request) {
        log.debug("Get tenant project dashboard, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getTenantProjectId();
        final var permissionQualifier = TenantProjectPermissionQualifierEnum.GETTING_DASHBOARD;
        return checkTenantProjectPermissionOperation.execute(tenantId, tenantProjectId, userId, permissionQualifier)
                .flatMap(voidItem -> getTenantProjectData(tenantId, tenantProjectId))
                .map(tenantProjectMapper::dataToDashboard)
                .map(GetTenantProjectDashboardDeveloperResponse::new);
    }

    Uni<TenantProjectDataDto> getTenantProjectData(final Long tenantId, final Long tenantProjectId) {
        final var request = new GetTenantProjectDataRequest(tenantId, tenantProjectId);
        return tenantModule.getService().getTenantProjectData(request)
                .map(GetTenantProjectDataResponse::getTenantProjectData);
    }
}
