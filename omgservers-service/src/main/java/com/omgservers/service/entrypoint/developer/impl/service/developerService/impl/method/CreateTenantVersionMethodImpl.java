package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.shard.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
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
class CreateTenantVersionMethodImpl implements CreateTenantVersionMethod {

    final TenantShard tenantShard;

    final AuthorizeTenantProjectRequestOperation authorizeTenantProjectRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;

    @Override
    public Uni<CreateVersionDeveloperResponse> execute(final CreateVersionDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var userId = getSecurityAttributeOperation.getUserId();
        final var permission = TenantProjectPermissionQualifierEnum.VERSION_MANAGER;

        return authorizeTenantProjectRequestOperation.execute(tenant, project, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    final var tenantProjectId = authorization.tenantProjectId();
                    final var tenantVersionConfig = request.getConfig();
                    return createTenantVersion(tenantId,
                            tenantProjectId,
                            tenantVersionConfig)
                            .map(TenantVersionModel::getId)
                            .invoke(tenantVersionId -> log.info("Created new version \"{}\" in tenant \"{}\"",
                                    tenantVersionId, tenantId));
                })
                .map(CreateVersionDeveloperResponse::new);
    }

    Uni<TenantVersionModel> createTenantVersion(final Long tenantId,
                                                final Long tenantProjectId,
                                                final TenantVersionConfigDto tenantVersionConfigDto) {
        final var tenantVersion = tenantVersionModelFactory.create(tenantId,
                tenantProjectId,
                tenantVersionConfigDto);
        final var request = new SyncTenantVersionRequest(tenantVersion);
        return tenantShard.getService().execute(request)
                .replaceWith(tenantVersion);
    }
}
