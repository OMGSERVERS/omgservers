package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantStageMapper;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByStageOperation;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
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
class GetTenantStageDetailsMethodImpl implements GetTenantStageDetailsMethod {

    final TenantShard tenantShard;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByStageOperation getIdByStageOperation;

    final TenantStageMapper tenantStageMapper;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetTenantStageDetailsDeveloperResponse> execute(
            final GetTenantStageDetailsDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> {
                                final var stage = request.getStage();
                                return getIdByStageOperation.execute(tenantId, tenantProjectId, stage)
                                        .flatMap(tenantStageId -> getTenantStage(tenantId, tenantStageId)
                                                .flatMap(tenantStage -> {
                                                    final var stageProjectId = tenantStage.getProjectId();
                                                    final var permissionQualifier =
                                                            TenantProjectPermissionQualifierEnum
                                                                    .PROJECT_VIEWER;
                                                    return checkTenantProjectPermissionOperation.execute(tenantId,
                                                                    stageProjectId,
                                                                    userId,
                                                                    permissionQualifier)
                                                            .flatMap(voidItem -> getTenantStageData(tenantId,
                                                                    tenantStageId))
                                                            .map(tenantStageMapper::dataToDetails);
                                                }));
                            });
                })
                .map(GetTenantStageDetailsDeveloperResponse::new);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageRequest(tenantId, tenantStageId);
        return tenantShard.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<TenantStageDataDto> getTenantStageData(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageDataRequest(tenantId, tenantStageId);
        return tenantShard.getService().getTenantStageData(request)
                .map(GetTenantStageDataResponse::getTenantStageData);
    }
}
