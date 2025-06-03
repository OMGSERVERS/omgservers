package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.shard.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
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
class DeleteTenantVersionMethodImpl implements DeleteTenantVersionMethod {

    final TenantShard tenantShard;

    final AuthorizeTenantProjectRequestOperation authorizeTenantProjectRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    @Override
    public Uni<DeleteVersionDeveloperResponse> execute(final DeleteVersionDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = getSecurityAttributeOperation.getUserId();

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var tenantVersionId = request.getId();
                    return getTenantVersion(tenantId, tenantVersionId)
                            .flatMap(tenantVersion -> {
                                final var tenantProjectId = tenantVersion.getProjectId();
                                final var permission = TenantProjectPermissionQualifierEnum.VERSION_MANAGER;

                                return authorizeTenantProjectRequestOperation.execute(tenantId.toString(),
                                                tenantProjectId.toString(),
                                                userId,
                                                permission)
                                        .flatMap(authorization -> deleteTenantVersion(tenantId, tenantVersionId)
                                                .invoke(deleted -> {
                                                    if (deleted) {
                                                        log.info("Deleted version \"{}\" in tenant \"{}\"",
                                                                tenantVersionId, tenantId);
                                                    }
                                                }));
                            });
                })
                .map(DeleteVersionDeveloperResponse::new);
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Boolean> deleteTenantVersion(final Long tenantId,
                                     final Long id) {
        final var request = new DeleteTenantVersionRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantVersionResponse::getDeleted);
    }
}
