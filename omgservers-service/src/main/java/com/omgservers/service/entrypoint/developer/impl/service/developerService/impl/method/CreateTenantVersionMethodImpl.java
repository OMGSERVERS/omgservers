package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.ServiceSecurityAttributes;
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

    final TenantModule tenantModule;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;
    final SecurityIdentity securityIdentity;
    final ObjectMapper objectMapper;

    @Override
    public Uni<CreateTenantVersionDeveloperResponse> execute(final CreateTenantVersionDeveloperRequest request) {
        log.debug("Create tenant version, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getTenantProjectId();
        final var versionConfig = request.getTenantVersionConfig();

        final var permissionQualifier = TenantProjectPermissionQualifierEnum
                .VERSION_MANAGEMENT;
        return checkTenantProjectPermissionOperation.execute(tenantId, tenantProjectId, userId, permissionQualifier)
                .flatMap(voidItem -> createTenantVersion(tenantId, tenantProjectId, versionConfig))
                .map(TenantVersionModel::getId)
                .map(CreateTenantVersionDeveloperResponse::new);
    }

    Uni<TenantVersionModel> createTenantVersion(final Long tenantId,
                                                final Long tenantProjectId,
                                                final TenantVersionConfigDto versionConfig) {
        // TODO: fix empty archive field
        final var tenantVersion = tenantVersionModelFactory.create(tenantId, tenantProjectId, versionConfig, "");
        final var request = new SyncTenantVersionRequest(tenantVersion);
        return tenantModule.getTenantService().syncTenantVersion(request)
                .replaceWith(tenantVersion);
    }
}