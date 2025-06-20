package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetVersionDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetVersionDetailsDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionDataResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.dto.TenantVersionDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantVersionMapper;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
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
class GetTenantVersionDetailsMethodImpl implements GetTenantVersionDetailsMethod {

    final TenantShard tenantShard;

    final AuthorizeTenantProjectRequestOperation authorizeTenantProjectRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantVersionMapper tenantVersionMapper;

    @Override
    public Uni<GetVersionDetailsDeveloperResponse> execute(final GetVersionDetailsDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = getSecurityAttributeOperation.getUserId();

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var tenantVersionId = request.getVersionId();
                    return getTenantVersion(tenantId, tenantVersionId)
                            .flatMap(tenantVersion -> {
                                final var tenantProjectId = tenantVersion.getProjectId();
                                final var permission = TenantProjectPermissionQualifierEnum.PROJECT_VIEWER;
                                return authorizeTenantProjectRequestOperation.execute(tenantId.toString(),
                                                tenantProjectId.toString(),
                                                userId,
                                                permission)
                                        .flatMap(authorization -> getTenantVersionData(tenantId, tenantVersionId)
                                                .map(tenantVersionMapper::dataToDetails));
                            });
                })
                .map(GetVersionDetailsDeveloperResponse::new);
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<TenantVersionDataDto> getTenantVersionData(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionDataRequest(tenantId, tenantVersionId);
        return tenantShard.getService().execute(request)
                .map(GetTenantVersionDataResponse::getTenantVersionData);
    }
}
