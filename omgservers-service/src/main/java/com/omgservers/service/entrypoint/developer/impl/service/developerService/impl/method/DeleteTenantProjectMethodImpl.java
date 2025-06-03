package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteProjectDeveloperResponse;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.DeleteTenantProjectResponse;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.authz.AuthorizeTenantRequestOperation;
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
class DeleteTenantProjectMethodImpl implements DeleteTenantProjectMethod {

    final TenantShard tenantShard;

    final AuthorizeTenantRequestOperation authorizeTenantRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;
    final GetIdByProjectOperation getIdByProjectOperation;

    @Override
    public Uni<DeleteProjectDeveloperResponse> execute(final DeleteProjectDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var userId = getSecurityAttributeOperation.getUserId();
        final var permission = TenantPermissionQualifierEnum.PROJECT_MANAGER;

        return authorizeTenantRequestOperation.execute(tenant, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> deleteTenantProject(tenantId, tenantProjectId)
                                    .invoke(deleted -> {
                                        if (deleted) {
                                            log.info("Delete project \"{}\" in tenant \"{}\"",
                                                    tenantProjectId, tenantId);
                                        }
                                    }));
                })
                .map(DeleteProjectDeveloperResponse::new);
    }

    Uni<Boolean> deleteTenantProject(final Long tenantId,
                                     final Long id) {
        final var request = new DeleteTenantProjectRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantProjectResponse::getDeleted);
    }
}
