package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectDataResponse;
import com.omgservers.schema.shard.tenant.tenantProject.dto.TenantProjectDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantProjectMapper;
import com.omgservers.service.operation.authz.AuthorizeTenantProjectRequestOperation;
import com.omgservers.service.operation.security.GetSecurityAttributeOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetTenantProjectDetailsMethodImpl implements GetTenantProjectDetailsMethod {

    final TenantShard tenantShard;

    final AuthorizeTenantProjectRequestOperation authorizeTenantProjectRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    final TenantProjectMapper tenantProjectMapper;

    @Override
    public Uni<GetProjectDetailsDeveloperResponse> execute(final GetProjectDetailsDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var userId = getSecurityAttributeOperation.getUserId();
        final var permission = TenantProjectPermissionQualifierEnum.PROJECT_VIEWER;

        return authorizeTenantProjectRequestOperation.execute(tenant, project, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    final var tenantProjectId = authorization.tenantProjectId();
                    return getTenantProjectData(tenantId, tenantProjectId)
                            .map(tenantProjectMapper::dataToDetails);
                })
                .map(GetProjectDetailsDeveloperResponse::new);
    }

    Uni<TenantProjectDataDto> getTenantProjectData(final Long tenantId, final Long tenantProjectId) {
        final var request = new GetTenantProjectDataRequest(tenantId, tenantProjectId);
        return tenantShard.getService().execute(request)
                .map(GetTenantProjectDataResponse::getTenantProjectData);
    }
}
