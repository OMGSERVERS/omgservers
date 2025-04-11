package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportResponse;
import com.omgservers.schema.model.tenantStage.TenantStageConfigDto;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
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
class CreateTenantStageMethodImpl implements CreateTenantStageMethod {

    final TenantShard tenantShard;

    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantStageModelFactory tenantStageModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantStageSupportResponse> execute(final CreateTenantStageSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> createTenantStage(tenantId, tenantProjectId)
                                    .map(tenantStage -> {
                                        final var tenantStageId = tenantStage.getId();

                                        log.info("New stage \"{}\" created in tenant \"{}\"",
                                                tenantStageId, tenantId);

                                        return new CreateTenantStageSupportResponse(tenantStageId);
                                    }));
                });
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
