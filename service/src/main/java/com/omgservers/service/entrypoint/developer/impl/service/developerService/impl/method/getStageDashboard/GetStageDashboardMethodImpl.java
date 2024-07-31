package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getStageDashboard;

import com.omgservers.schema.entrypoint.developer.GetStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetStageDashboardDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.stagePermission.StagePermissionEnum;
import com.omgservers.schema.module.tenant.HasStagePermissionRequest;
import com.omgservers.schema.module.tenant.HasStagePermissionResponse;
import com.omgservers.schema.module.tenant.stage.GetStageDataRequest;
import com.omgservers.schema.module.tenant.stage.GetStageDataResponse;
import com.omgservers.schema.module.tenant.stage.dto.StageDataDto;
import com.omgservers.service.entrypoint.developer.impl.operation.mapStageDataToDashboard.MapStageDataToDashboardOperation;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.module.tenant.TenantModule;
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
class GetStageDashboardMethodImpl implements GetStageDashboardMethod {

    final TenantModule tenantModule;

    final MapStageDataToDashboardOperation mapStageDataToDashboardOperation;

    final JsonWebToken jwt;

    @Override
    public Uni<GetStageDashboardDeveloperResponse> getStageDashboard(
            final GetStageDashboardDeveloperRequest request) {
        log.debug("Get stage dashboard, request={}", request);

        final var userId = Long.valueOf(jwt.getClaim(Claims.sub));
        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        return checkGettingDashboardPermission(tenantId, stageId, userId)
                .flatMap(voidItem -> getStageData(tenantId, stageId))
                .map(mapStageDataToDashboardOperation::mapStageDataToDashboard)
                .map(GetStageDashboardDeveloperResponse::new);
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

    Uni<StageDataDto> getStageData(final Long tenantId, final Long stageId) {
        final var request = new GetStageDataRequest(tenantId, stageId);
        return tenantModule.getStageService().getStageData(request)
                .map(GetStageDataResponse::getStageData);
    }
}
