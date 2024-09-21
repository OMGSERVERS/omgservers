package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getStageDashboard;

import com.omgservers.schema.entrypoint.developer.GetStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetStageDashboardDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import com.omgservers.service.entrypoint.developer.impl.operation.mapStageDataToDashboard.MapStageDataToDashboardOperation;
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
class GetStageDashboardMethodImpl implements GetStageDashboardMethod {

    final TenantModule tenantModule;

    final MapStageDataToDashboardOperation mapStageDataToDashboardOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetStageDashboardDeveloperResponse> getStageDashboard(
            final GetStageDashboardDeveloperRequest request) {
        log.debug("Get stage dashboard, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        return checkGettingDashboardPermission(tenantId, stageId, userId)
                .flatMap(voidItem -> getStageData(tenantId, stageId))
                .map(mapStageDataToDashboardOperation::mapStageDataToDashboard)
                .map(GetStageDashboardDeveloperResponse::new);
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

    Uni<TenantStageDataDto> getStageData(final Long tenantId, final Long stageId) {
        final var request = new GetTenantStageDataRequest(tenantId, stageId);
        return tenantModule.getTenantService().getStageData(request)
                .map(GetTenantStageDataResponse::getTenantStageData);
    }
}
