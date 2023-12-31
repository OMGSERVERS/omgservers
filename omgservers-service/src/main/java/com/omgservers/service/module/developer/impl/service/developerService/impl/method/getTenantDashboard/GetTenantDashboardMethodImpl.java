package com.omgservers.service.module.developer.impl.service.developerService.impl.method.getTenantDashboard;

import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.module.tenant.TenantModule;
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

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(
            final GetTenantDashboardDeveloperRequest request) {
        log.debug("Get tenant dashboard, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var tenantId = request.getTenantId();
        return checkGetDashboardPermission(tenantId, userId)
                .flatMap(voidItem -> tenantModule.getShortcutService().getTenantDashboard(tenantId))
                .map(GetTenantDashboardDeveloperResponse::new);
    }

    Uni<Void> checkGetDashboardPermission(final Long tenantId, final Long userId) {
        // TODO: move to new operation
        final var permission = TenantPermissionEnum.GET_DASHBOARD;
        final var hasTenantPermissionServiceRequest = new HasTenantPermissionRequest(tenantId, userId, permission);
        return tenantModule.getTenantService().hasTenantPermission(hasTenantPermissionServiceRequest)
                .map(HasTenantPermissionResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                "tenantId=%s, userId=%s, permission=%s", tenantId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }
}
