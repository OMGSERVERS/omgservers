package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getVersionDashboard;

import com.omgservers.schema.entrypoint.developer.GetVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetVersionDashboardDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.dto.TenantVersionDataDto;
import com.omgservers.service.entrypoint.developer.impl.operation.mapVersionDataToDashboard.MapVersionDataToDashboardOperation;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.ServiceSecurityAttributes;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetVersionDashboardMethodImpl implements GetVersionDashboardMethod {

    final TenantModule tenantModule;

    final MapVersionDataToDashboardOperation mapVersionDataToDashboardOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetVersionDashboardDeveloperResponse> getVersionDashboard(
            final GetVersionDashboardDeveloperRequest request) {
        log.debug("Get version dashboard, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var versionId = request.getVersionId();
        return getTenantVersion(tenantId, versionId)
                .flatMap(version -> {
                    final var stageId = version.getProjectId();
                    return checkGettingDashboardPermission(tenantId, stageId, userId)
                            .flatMap(voidItem -> getTenantVersionData(tenantId, versionId))
                            .map(mapVersionDataToDashboardOperation::mapVersionDataToDashboard)
                            .map(GetVersionDashboardDeveloperResponse::new);
                });
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long versionId) {
        final var request = new GetTenantVersionRequest(tenantId, versionId);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Void> checkGettingDashboardPermission(final Long tenantId, final Long stageId, final Long userId) {
        // TODO: move to new operation
        final var permission = TenantStagePermissionEnum.GETTING_DASHBOARD;
        final var request = new VerifyTenantStagePermissionExistsRequest(tenantId, stageId, userId, permission);
        return tenantModule.getTenantService().verifyTenantStagePermissionExists(request)
                .map(VerifyTenantStagePermissionExistsResponse::getExists)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, " +
                                                "tenantId=%d, stageId=%d, userId=%d, permission=%s",
                                        tenantId, stageId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<TenantVersionDataDto> getTenantVersionData(final Long tenantId, final Long versionId) {
        final var request = new GetTenantVersionDataRequest(tenantId, versionId);
        return tenantModule.getTenantService().getTenantVersionData(request)
                .map(GetTenantVersionDataResponse::getTenantVersionData);
    }
}
