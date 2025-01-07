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
class CreateTenantProjectMethodImpl implements CreateTenantProjectMethod {

    final TenantModule tenantModule;

    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantProjectModelFactory tenantProjectModelFactory;
    final TenantStageModelFactory tenantStageModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantProjectSupportResponse> execute(final CreateTenantProjectSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> createTenantProject(tenantId)
                        .flatMap(tenantProject -> {
                            final var tenantProjectId = tenantProject.getId();
                            return createTenantStage(tenantId, tenantProjectId)
                                    .map(tenantStage -> {
                                        final var tenantStageId = tenantStage.getId();

                                        log.info("The new project \"{}\" was created in tenant \"{}\" by the user {}",
                                                tenantProjectId, tenantId, userId);

                                        return new CreateTenantProjectSupportResponse(tenantProjectId, tenantStageId);
                                    });
                        }));
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
