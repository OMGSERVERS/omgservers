package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetStageDetailsDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.shard.tenant.tenantStage.dto.TenantStageDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantStageMapper;
import com.omgservers.service.operation.alias.GetIdByStageOperation;
import com.omgservers.service.operation.authz.AuthorizeTenantProjectRequestOperation;
import com.omgservers.service.operation.security.GetSecurityAttributeOperation;
import com.omgservers.service.shard.tenant.TenantShard;
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

    final AuthorizeTenantProjectRequestOperation authorizeTenantProjectRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;
    final GetIdByStageOperation getIdByStageOperation;

    final TenantStageMapper tenantStageMapper;

    @Override
    public Uni<GetStageDetailsDeveloperResponse> execute(final GetStageDetailsDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var userId = getSecurityAttributeOperation.getUserId();
        final var permission = TenantProjectPermissionQualifierEnum.PROJECT_VIEWER;

        return authorizeTenantProjectRequestOperation.execute(tenant, project, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    final var tenantProjectId = authorization.tenantProjectId();
                    final var stage = request.getStage();
                    return getIdByStageOperation.execute(tenantId, tenantProjectId, stage)
                            .flatMap(tenantStageId -> getTenantStageData(tenantId, tenantStageId)
                                    .map(tenantStageMapper::dataToDetails));
                })
                .map(GetStageDetailsDeveloperResponse::new);
    }

    Uni<TenantStageDataDto> getTenantStageData(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageDataRequest(tenantId, tenantStageId);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageDataResponse::getTenantStageData);
    }
}
