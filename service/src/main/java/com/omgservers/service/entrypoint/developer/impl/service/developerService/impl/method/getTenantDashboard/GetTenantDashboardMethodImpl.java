package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getTenantDashboard;

import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.model.dto.tenant.GetTenantDashboardRequest;
import com.omgservers.model.dto.tenant.GetTenantDashboardResponse;
import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.model.tenantDashboard.TenantDashboardModel;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.module.tenant.TenantModule;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetTenantDashboardMethodImpl implements GetTenantDashboardMethod {

    final TenantModule tenantModule;

    final JsonWebToken jwt;

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(
            final GetTenantDashboardDeveloperRequest request) {
        log.debug("Get tenant dashboard, request={}", request);

        final var userId = Long.valueOf(jwt.getClaim(Claims.upn));
        final var tenantId = request.getTenantId();
        return checkGetDashboardPermission(tenantId, userId)
                .flatMap(voidItem -> getTenantDashboard(tenantId))
                .map(GetTenantDashboardDeveloperResponse::new);
    }

    Uni<TenantDashboardModel> getTenantDashboard(Long id) {
        final var request = new GetTenantDashboardRequest(id);
        return tenantModule.getTenantService().getTenantDashboard(request)
                .map(GetTenantDashboardResponse::getTenantDashboard);
    }

    Uni<Void> checkGetDashboardPermission(final Long tenantId, final Long userId) {
        // TODO: move to new operation
        final var permission = TenantPermissionEnum.GET_DASHBOARD;
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
}
