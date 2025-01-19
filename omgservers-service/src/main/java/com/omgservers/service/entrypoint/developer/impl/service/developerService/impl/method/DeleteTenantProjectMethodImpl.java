package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperResponse;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantPermissionOperation;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
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
class DeleteTenantProjectMethodImpl implements DeleteTenantProjectMethod {

    final TenantModule tenantModule;

    final CheckTenantPermissionOperation checkTenantPermissionOperation;
    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteTenantProjectDeveloperResponse> execute(final DeleteTenantProjectDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> {
                                final var permissionQualifier =
                                        TenantPermissionQualifierEnum.PROJECT_MANAGER;
                                return checkTenantPermissionOperation.execute(tenantId,
                                                userId,
                                                permissionQualifier)
                                        .flatMap(voidItem -> deleteTenantProject(tenantId, tenantProjectId))
                                        .invoke(deleted -> {
                                            if (deleted) {
                                                log.info("Project \"{}\" was deleted in tenant \"{}\"",
                                                        tenantProjectId, tenantId);
                                            }
                                        });
                            });
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
