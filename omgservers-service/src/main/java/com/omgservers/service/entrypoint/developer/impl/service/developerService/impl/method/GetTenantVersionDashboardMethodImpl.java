package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.dto.TenantVersionDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantVersionMapper;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getIdByTenant.GetIdByTenantOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetTenantVersionDashboardMethodImpl implements GetTenantVersionDashboardMethod {

    final TenantModule tenantModule;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantVersionMapper tenantVersionMapper;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetTenantVersionDashboardDeveloperResponse> execute(
            final GetTenantVersionDashboardDeveloperRequest request) {
        log.debug("Requested, {}, principal={}",
                request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var tenantVersionId = request.getVersionId();
                    return getTenantVersion(tenantId, tenantVersionId)
                            .flatMap(tenantVersion -> {
                                final var versionProjectId = tenantVersion.getProjectId();
                                final var permissionQualifier = TenantProjectPermissionQualifierEnum
                                        .PROJECT_VIEWER;
                                return checkTenantProjectPermissionOperation.execute(tenantId,
                                                versionProjectId,
                                                userId,
                                                permissionQualifier)
                                        .flatMap(voidItem -> getTenantVersionData(tenantId, tenantVersionId))
                                        .map(tenantVersionMapper::dataToDashboard);

                            });
                })
                .map(GetTenantVersionDashboardDeveloperResponse::new);
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<TenantVersionDataDto> getTenantVersionData(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionDataRequest(tenantId, tenantVersionId);
        return tenantModule.getService().getTenantVersionData(request)
                .map(GetTenantVersionDataResponse::getTenantVersionData);
    }
}
