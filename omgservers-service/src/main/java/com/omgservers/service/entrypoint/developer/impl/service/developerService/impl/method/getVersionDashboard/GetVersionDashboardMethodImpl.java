package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getVersionDashboard;

import com.omgservers.schema.entrypoint.developer.GetVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetVersionDashboardDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.stagePermission.StagePermissionEnum;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.schema.module.tenant.HasStagePermissionRequest;
import com.omgservers.schema.module.tenant.HasStagePermissionResponse;
import com.omgservers.schema.module.tenant.version.GetVersionDataRequest;
import com.omgservers.schema.module.tenant.version.GetVersionDataResponse;
import com.omgservers.schema.module.tenant.version.dto.VersionDataDto;
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
        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    final var stageId = version.getStageId();
                    return checkGettingDashboardPermission(tenantId, stageId, userId)
                            .flatMap(voidItem -> getVersionData(tenantId, versionId))
                            .map(mapVersionDataToDashboardOperation::mapVersionDataToDashboard)
                            .map(GetVersionDashboardDeveloperResponse::new);
                });
    }

    Uni<VersionModel> getVersion(final Long tenantId, final Long versionId) {
        final var request = new GetVersionRequest(tenantId, versionId);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Void> checkGettingDashboardPermission(final Long tenantId, final Long stageId, final Long userId) {
        // TODO: move to new operation
        final var permission = StagePermissionEnum.GETTING_DASHBOARD;
        final var request = new HasStagePermissionRequest(tenantId, stageId, userId, permission);
        return tenantModule.getStageService().hasStagePermission(request)
                .map(HasStagePermissionResponse::getResult)
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

    Uni<VersionDataDto> getVersionData(final Long tenantId, final Long versionId) {
        final var request = new GetVersionDataRequest(tenantId, versionId);
        return tenantModule.getVersionService().getVersionData(request)
                .map(GetVersionDataResponse::getVersionData);
    }
}
