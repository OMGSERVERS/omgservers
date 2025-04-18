package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.shard.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
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
class CreateTenantVersionMethodImpl implements CreateTenantVersionMethod {

    final TenantShard tenantShard;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;
    final SecurityIdentity securityIdentity;
    final ObjectMapper objectMapper;

    @Override
    public Uni<CreateVersionDeveloperResponse> execute(final CreateVersionDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> {
                                final var tenantVersionConfig = request.getConfig();
                                final var permissionQualifier =
                                        TenantProjectPermissionQualifierEnum.VERSION_MANAGER;
                                return checkTenantProjectPermissionOperation.execute(tenantId,
                                                tenantProjectId,
                                                userId,
                                                permissionQualifier)
                                        .flatMap(voidItem -> createTenantVersion(tenantId,
                                                tenantProjectId,
                                                tenantVersionConfig))
                                        .map(TenantVersionModel::getId)
                                        .invoke(tenantVersionId -> log.info(
                                                "The new version \"{}\" was created in tenant \"{}\"",
                                                tenantVersionId, tenantId));
                            });
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
