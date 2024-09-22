package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deleteVersion;

import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
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
        log.debug("Delete tenant version, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantVersionId = request.getId();

        return getTenantVersion(tenantId, tenantVersionId)
                .flatMap(tenantVersion -> {
                    final var projectId = tenantVersion.getProjectId();

                    return checkVersionManagementPermission(tenantId, projectId, userId)
                            .flatMap(voidItem -> deleteTenantVersion(tenantId, tenantVersionId))
                            .map(DeleteVersionDeveloperResponse::new);
                });
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Void> checkVersionManagementPermission(final Long tenantId,
                                               final Long tenantProjectId,
                                               final Long userId) {
        final var permission = TenantProjectPermissionEnum.VERSION_MANAGEMENT;
        final var request = new VerifyTenantProjectPermissionExistsRequest(tenantId,
                tenantProjectId,
                userId,
                permission);
        return tenantModule.getTenantService().verifyTenantProjectPermissionExists(request)
                .map(VerifyTenantProjectPermissionExistsResponse::getExists)
                .invoke(exists -> {
                    if (!exists) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, " +
                                                "tenantId=%d, tenantProjectId=%d, userId=%d, permission=%s",
                                        tenantId, tenantProjectId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<Boolean> deleteTenantVersion(final Long tenantId,
                                     final Long id) {
        final var request = new DeleteTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantVersion(request)
                .map(DeleteTenantVersionResponse::getDeleted);
    }
}
