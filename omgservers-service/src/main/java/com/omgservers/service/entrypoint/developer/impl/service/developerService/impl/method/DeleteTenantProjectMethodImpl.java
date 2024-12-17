package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperResponse;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantPermissionOperation;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteTenantProjectMethodImpl implements DeleteTenantProjectMethod {

    final TenantModule tenantModule;

    final CheckTenantPermissionOperation checkTenantPermissionOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteTenantProjectDeveloperResponse> execute(final DeleteTenantProjectDeveloperRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getId();

        final var permissionQualifier = TenantPermissionQualifierEnum.PROJECT_MANAGEMENT;
        return checkTenantPermissionOperation.execute(tenantId,
                        userId,
                        permissionQualifier)
                .flatMap(voidItem -> deleteTenantProject(tenantId, tenantProjectId))
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Project {} was deleted in tenant {} by user {}",
                                tenantProjectId, tenantId, userId);
                    }
                })
                .map(DeleteTenantProjectDeveloperResponse::new);
    }

    Uni<Boolean> deleteTenantProject(final Long tenantId,
                                     final Long id) {
        final var request = new DeleteTenantProjectRequest(tenantId, id);
        return tenantModule.getService().deleteTenantProject(request)
                .map(DeleteTenantProjectResponse::getDeleted);
    }
}
