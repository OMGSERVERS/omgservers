package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantStageMapper;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getIdByProject.GetIdByProjectOperation;
import com.omgservers.service.operation.getIdByStage.GetIdByStageOperation;
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
class GetTenantStageDashboardMethodImpl implements GetTenantStageDashboardMethod {

    final TenantModule tenantModule;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByStageOperation getIdByStageOperation;

    final TenantStageMapper tenantStageMapper;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetTenantStageDashboardDeveloperResponse> execute(
            final GetTenantStageDashboardDeveloperRequest request) {
        log.debug("Requested, {}, principal={}",
                request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> {
                                final var stage = request.getStage();
                                return getIdByStageOperation.execute(tenantProjectId, stage)
                                        .flatMap(tenantStageId -> getTenantStage(tenantId, tenantStageId)
                                                .flatMap(tenantStage -> {
                                                    final var stageProjectId = tenantStage.getProjectId();
                                                    final var permissionQualifier =
                                                            TenantProjectPermissionQualifierEnum
                                                                    .GETTING_DASHBOARD;
                                                    return checkTenantProjectPermissionOperation.execute(tenantId,
                                                                    stageProjectId,
                                                                    userId,
                                                                    permissionQualifier)
                                                            .flatMap(voidItem -> getTenantStageData(tenantId,
                                                                    tenantStageId))
                                                            .map(tenantStageMapper::dataToDashboard);
                                                }));
                            });
                })
                .map(GetTenantStageDashboardDeveloperResponse::new);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageRequest(tenantId, tenantStageId);
        return tenantModule.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<TenantStageDataDto> getTenantStageData(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageDataRequest(tenantId, tenantStageId);
        return tenantModule.getService().getTenantStageData(request)
                .map(GetTenantStageDataResponse::getTenantStageData);
    }
}
