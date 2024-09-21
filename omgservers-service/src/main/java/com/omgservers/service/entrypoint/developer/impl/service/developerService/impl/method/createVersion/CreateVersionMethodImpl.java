package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
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
        final var stageId = request.getStageId();
        final var versionConfig = request.getVersionConfig();

        return checkVersionManagementPermission(tenantId, stageId, userId)
                .flatMap(voidItem -> createVersion(tenantId, stageId, versionConfig))
                .map(TenantVersionModel::getId)
                .map(CreateVersionDeveloperResponse::new);
    }

    Uni<Void> checkVersionManagementPermission(final Long tenantId,
                                               final Long stageId,
                                               final Long userId) {
        final var permission = TenantStagePermissionEnum.VERSION_MANAGEMENT;
        final var hasStagePermissionServiceRequest = new VerifyTenantStagePermissionExistsRequest(tenantId,
                stageId,
                userId,
                permission);
        return tenantModule.getTenantService().verifyTenantStagePermissionExists(hasStagePermissionServiceRequest)
                .map(VerifyTenantStagePermissionExistsResponse::getExists)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, " +
                                                "tenantId=%d, stageId=%d, userId=%d, permission=%s",
                                        tenantId, stageId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<TenantVersionModel> createVersion(final Long tenantId,
                                          final Long stageId,
                                          final TenantVersionConfigDto versionConfig) {
        // TODO: fix empty archive field
        final var version = tenantVersionModelFactory.create(tenantId, stageId, versionConfig, "");
        final var request = new SyncTenantVersionRequest(version);
        return tenantModule.getTenantService().syncTenantVersion(request)
                .replaceWith(version);
    }
}
