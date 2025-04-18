package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectDataResponse;
import com.omgservers.schema.shard.tenant.tenantProject.dto.TenantProjectDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantProjectMapper;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
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
class GetTenantProjectDetailsMethodImpl implements GetTenantProjectDetailsMethod {

    final TenantShard tenantShard;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantProjectMapper tenantProjectMapper;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetProjectDetailsDeveloperResponse> execute(
            final GetProjectDetailsDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> {
                                final var permissionQualifier = TenantProjectPermissionQualifierEnum.PROJECT_VIEWER;
                                return checkTenantProjectPermissionOperation.execute(tenantId, tenantProjectId, userId,
                                                permissionQualifier)
                                        .flatMap(voidItem -> getTenantProjectData(tenantId, tenantProjectId))
                                        .map(tenantProjectMapper::dataToDetails);
                            });
                })
                .map(GetProjectDetailsDeveloperResponse::new);
    }

    Uni<TenantProjectDataDto> getTenantProjectData(final Long tenantId, final Long tenantProjectId) {
        final var request = new GetTenantProjectDataRequest(tenantId, tenantProjectId);
        return tenantShard.getService().execute(request)
                .map(GetTenantProjectDataResponse::getTenantProjectData);
    }
}
