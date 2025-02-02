package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperResponse;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.dto.TenantDeploymentDataDto;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantDeploymentMapper;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.shard.tenant.TenantShard;
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
class GetTenantDeploymentDetailsMethodImpl implements GetTenantDeploymentDetailsMethod {

    final TenantShard tenantShard;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantDeploymentMapper tenantDeploymentMapper;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetTenantDeploymentDetailsDeveloperResponse> execute(
            final GetTenantDeploymentDetailsDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var tenantDeploymentId = request.getDeploymentId();
                    return getTenantDeployment(tenantId, tenantDeploymentId)
                            .flatMap(tenantDeployment -> {
                                final var tenantStageId = tenantDeployment.getStageId();
                                return getTenantStage(tenantId, tenantStageId)
                                        .flatMap(tenantStage -> {
                                            final var tenantProjectId = tenantStage.getProjectId();
                                            final var permissionQualifier =
                                                    TenantProjectPermissionQualifierEnum.PROJECT_VIEWER;
                                            return checkTenantProjectPermissionOperation.execute(tenantId,
                                                            tenantProjectId,
                                                            userId,
                                                            permissionQualifier)
                                                    .flatMap(voidItem -> getTenantDeploymentData(tenantId,
                                                            tenantDeploymentId))
                                                    .map(tenantDeploymentMapper::dataToDetails)
                                                    .map(GetTenantDeploymentDetailsDeveloperResponse::new);
                                        });
                            });
                });
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantShard.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<TenantDeploymentDataDto> getTenantDeploymentData(final Long tenantId, final Long tenantDeploymentId) {
        final var request = new GetTenantDeploymentDataRequest(tenantId, tenantDeploymentId);
        return tenantShard.getService().getTenantDeploymentData(request)
                .map(GetTenantDeploymentDataResponse::getTenantDeploymentData);
    }
}
