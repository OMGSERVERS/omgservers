package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportResponse;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantProjectMethodImpl implements CreateTenantProjectMethod {

    final TenantModule tenantModule;

    final TenantProjectModelFactory tenantProjectModelFactory;
    final TenantStageModelFactory tenantStageModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantProjectSupportResponse> execute(final CreateTenantProjectSupportRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        return createTenantProject(tenantId)
                .flatMap(tenantProject -> {
                    final var tenantProjectId = tenantProject.getId();
                    return createTenantStage(tenantId, tenantProjectId)
                            .map(tenantStage -> {
                                final var tenantStageId = tenantStage.getId();
                                final var tenantStageSecret = tenantStage.getSecret();

                                log.info("The new project \"{}\" was created in tenant \"{}\" by the user {}",
                                        tenantProjectId, tenantId, userId);

                                return new CreateTenantProjectSupportResponse(tenantProjectId,
                                        tenantStageId,
                                        tenantStageSecret);
                            });
                });
    }

    Uni<TenantProjectModel> createTenantProject(final Long tenantId) {
        final var tenantProject = tenantProjectModelFactory.create(tenantId);
        final var syncProjectInternalRequest = new SyncTenantProjectRequest(tenantProject);
        return tenantModule.getService().syncTenantProject(syncProjectInternalRequest)
                .replaceWith(tenantProject);
    }

    Uni<TenantStageModel> createTenantStage(final Long tenantId,
                                            final Long tenantProjectId) {
        final var tenantStage = tenantStageModelFactory.create(tenantId, tenantProjectId);
        final var syncStageInternalRequest = new SyncTenantStageRequest(tenantStage);
        return tenantModule.getService().syncTenantStage(syncStageInternalRequest)
                .replaceWith(tenantStage);
    }
}
