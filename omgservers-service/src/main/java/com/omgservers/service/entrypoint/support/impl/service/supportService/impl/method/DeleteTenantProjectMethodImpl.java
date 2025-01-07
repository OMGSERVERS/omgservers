package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectResponse;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getIdByProject.GetIdByProjectOperation;
import com.omgservers.service.operation.getIdByTenant.GetIdByTenantOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantProjectMethodImpl implements DeleteTenantProjectMethod {

    final TenantModule tenantModule;

    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteTenantProjectSupportResponse> execute(final DeleteTenantProjectSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> {
                                final var deleteTenantRequest = new DeleteTenantProjectRequest(tenantId,
                                        tenantProjectId);
                                return tenantModule.getService().deleteTenantProject(deleteTenantRequest)
                                        .map(DeleteTenantProjectResponse::getDeleted)
                                        .invoke(deleted -> {
                                            if (deleted) {
                                                log.info("Project \"{}\" was deleted in tenant \"{}\"",
                                                        tenantProjectId, tenantId);
                                            }
                                        })
                                        .map(DeleteTenantProjectSupportResponse::new);
                            });
                });
    }
}
