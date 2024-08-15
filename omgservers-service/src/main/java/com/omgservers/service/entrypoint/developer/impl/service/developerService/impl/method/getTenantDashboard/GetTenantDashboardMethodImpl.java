package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getTenantDashboard;

import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.schema.module.tenant.HasTenantPermissionRequest;
import com.omgservers.schema.module.tenant.HasTenantPermissionResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.module.tenant.tenant.dto.TenantDataDto;
import com.omgservers.service.entrypoint.developer.impl.operation.mapTenantDataToDashboard.MapTenantDataToDashboardOperation;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.server.security.ServiceSecurityAttributes;
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

    final MapTenantDataToDashboardOperation mapTenantDataToDashboardOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(
            final GetTenantDashboardDeveloperRequest request) {
        log.debug("Get tenant dashboard, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        return checkGetDashboardPermission(tenantId, userId)
                .flatMap(voidItem -> getTenantData(tenantId))
                .map(mapTenantDataToDashboardOperation::mapTenantDataToDashboard)
                .map(GetTenantDashboardDeveloperResponse::new);
    }

    Uni<Void> checkGetDashboardPermission(final Long tenantId, final Long userId) {
        // TODO: move to new operation
        final var permission = TenantPermissionEnum.GETTING_DASHBOARD;
        final var hasTenantPermissionServiceRequest = new HasTenantPermissionRequest(tenantId, userId, permission);
        return tenantModule.getTenantService().hasTenantPermission(hasTenantPermissionServiceRequest)
                .map(HasTenantPermissionResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, tenantId=%d, userId=%d, permission=%s",
                                        tenantId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<TenantDataDto> getTenantData(Long id) {
        final var request = new GetTenantDataRequest(id);
        return tenantModule.getTenantService().getTenantData(request)
                .map(GetTenantDataResponse::getTenantData);
    }
}
