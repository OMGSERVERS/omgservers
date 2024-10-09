package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
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
class DeleteTenantVersionMethodImpl implements DeleteTenantVersionMethod {

    final TenantModule tenantModule;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteTenantVersionDeveloperResponse> execute(final DeleteTenantVersionDeveloperRequest request) {
        log.debug("Delete tenant version, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantVersionId = request.getId();

        return getTenantVersion(tenantId, tenantVersionId)
                .flatMap(tenantVersion -> {
                    final var versionProjectId = tenantVersion.getProjectId();

                    final var permissionQualifier =
                            TenantProjectPermissionQualifierEnum.VERSION_MANAGEMENT;
                    return checkTenantProjectPermissionOperation.execute(tenantId,
                                    versionProjectId,
                                    userId,
                                    permissionQualifier)
                            .flatMap(voidItem -> deleteTenantVersion(tenantId, tenantVersionId))
                            .map(DeleteTenantVersionDeveloperResponse::new);
                });
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Boolean> deleteTenantVersion(final Long tenantId,
                                     final Long id) {
        final var request = new DeleteTenantVersionRequest(tenantId, id);
        return tenantModule.getService().deleteTenantVersion(request)
                .map(DeleteTenantVersionResponse::getDeleted);
    }
}
