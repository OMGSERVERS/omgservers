package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deleteVersion;

import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
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
class DeleteVersionMethodImpl implements DeleteVersionMethod {

    final TenantModule tenantModule;

    final TenantVersionModelFactory tenantVersionModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteVersionDeveloperResponse> deleteVersion(final DeleteVersionDeveloperRequest request) {
        log.debug("Delete version, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var versionId = request.getId();

        return getTenantVersion(tenantId, versionId)
                .flatMap(version -> {
                    final var stageId = version.getProjectId();

                    return checkVersionManagementPermission(tenantId, stageId, userId)
                            .flatMap(voidItem -> deleteVersion(tenantId, versionId))
                            .map(DeleteVersionDeveloperResponse::new);
                });
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Void> checkVersionManagementPermission(final Long tenantId,
                                               final Long stageId,
                                               final Long userId) {
        final var permission = TenantStagePermissionEnum.VERSION_MANAGEMENT;
        final var hasStagePermissionServiceRequest =
                new VerifyTenantStagePermissionExistsRequest(tenantId, stageId, userId, permission);
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

    Uni<Boolean> deleteVersion(final Long tenantId,
                               final Long id) {
        final var request = new DeleteTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteVersion(request)
                .map(DeleteTenantVersionResponse::getDeleted);
    }
}
