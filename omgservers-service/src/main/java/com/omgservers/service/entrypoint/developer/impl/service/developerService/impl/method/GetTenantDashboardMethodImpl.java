package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.module.tenant.tenant.dto.TenantDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantMapper;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantPermissionOperation;
import com.omgservers.service.module.tenant.TenantModule;
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
class GetTenantDashboardMethodImpl implements GetTenantDashboardMethod {

    final TenantModule tenantModule;

    final CheckTenantPermissionOperation checkTenantPermissionOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantMapper tenantMapper;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> execute(
            final GetTenantDashboardDeveloperRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var permissionQualifier = TenantPermissionQualifierEnum
                            .GETTING_DASHBOARD;
                    return checkTenantPermissionOperation.execute(tenantId, userId, permissionQualifier)
                            .flatMap(voidItem -> getTenantData(tenantId))
                            .map(tenantMapper::dataToDashboard);
                })
                .map(GetTenantDashboardDeveloperResponse::new);
    }

    Uni<TenantDataDto> getTenantData(final Long tenantId) {
        final var request = new GetTenantDataRequest(tenantId);
        return tenantModule.getService().getTenantData(request)
                .map(GetTenantDataResponse::getTenantData);
    }
}
