package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportResponse;
import com.omgservers.schema.model.project.TenantProjectConfigDto;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantStage.TenantStageConfigDto;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.tenant.TenantShard;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantProjectMethodImpl implements CreateTenantProjectMethod {

    final TenantShard tenantShard;

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

                                        log.info("New project \"{}\" created in tenant \"{}\"",
                                                tenantProjectId, tenantId);

                                        return new CreateTenantProjectSupportResponse(tenantProjectId, tenantStageId);
                                    });
                        }));
    }

    Uni<TenantProjectModel> createTenantProject(final Long tenantId) {
        final var tenantProject = tenantProjectModelFactory.create(tenantId, TenantProjectConfigDto.create());
        final var syncProjectInternalRequest = new SyncTenantProjectRequest(tenantProject);
        return tenantShard.getService().execute(syncProjectInternalRequest)
                .replaceWith(tenantProject);
    }

    Uni<TenantStageModel> createTenantStage(final Long tenantId,
                                            final Long tenantProjectId) {
        final var tenantStage = tenantStageModelFactory.create(tenantId,
                tenantProjectId,
                TenantStageConfigDto.create());
        final var syncStageInternalRequest = new SyncTenantStageRequest(tenantStage);
        return tenantShard.getService().execute(syncStageInternalRequest)
                .replaceWith(tenantStage);
    }
}
