package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.service.exception.ServerSideForbiddenException;
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
class CreateVersionMethodImpl implements CreateVersionMethod {

    final TenantModule tenantModule;

    final TenantVersionModelFactory tenantVersionModelFactory;

    final SecurityIdentity securityIdentity;
    final ObjectMapper objectMapper;

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(final CreateVersionDeveloperRequest request) {
        log.debug("Create version, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getTenantProjectId();
        final var versionConfig = request.getVersionConfig();

        return checkVersionManagementPermission(tenantId, tenantProjectId, userId)
                .flatMap(voidItem -> createTenantVersion(tenantId, tenantProjectId, versionConfig))
                .map(TenantVersionModel::getId)
                .map(CreateVersionDeveloperResponse::new);
    }

    Uni<Void> checkVersionManagementPermission(final Long tenantId,
                                               final Long stageId,
                                               final Long userId) {
        final var permission = TenantProjectPermissionEnum.VERSION_MANAGEMENT;
        final var request = new VerifyTenantProjectPermissionExistsRequest(tenantId,
                stageId,
                userId,
                permission);
        return tenantModule.getTenantService().verifyTenantProjectPermissionExists(request)
                .map(VerifyTenantProjectPermissionExistsResponse::getExists)
                .invoke(exists -> {
                    if (!exists) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, " +
                                                "tenantId=%d, stageId=%d, userId=%d, permission=%s",
                                        tenantId, stageId, userId, permission));
                    }
                })
                .replaceWithVoid();
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
