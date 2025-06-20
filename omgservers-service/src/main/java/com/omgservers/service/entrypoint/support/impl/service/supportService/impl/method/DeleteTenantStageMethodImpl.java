package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportResponse;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByStageOperation;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantStageMethodImpl implements DeleteTenantStageMethod {

    final TenantShard tenantShard;

    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByStageOperation getIdByStageOperation;

    @Override
    public Uni<DeleteTenantStageSupportResponse> execute(final DeleteTenantStageSupportRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> {
                                final var stage = request.getStage();
                                return getIdByStageOperation.execute(tenantId, tenantProjectId, stage)
                                        .flatMap(tenantStageId -> {
                                            final var deleteTenantStageRequest = new DeleteTenantStageRequest(tenantId,
                                                    tenantStageId);
                                            return tenantShard.getService().execute(deleteTenantStageRequest)
                                                    .map(DeleteTenantStageResponse::getDeleted)
                                                    .invoke(deleted -> {
                                                        if (deleted) {
                                                            log.info("Stage \"{}\" was deleted in tenant \"{}\"",
                                                                    tenantStageId, tenantId);
                                                        }
                                                    })
                                                    .map(DeleteTenantStageSupportResponse::new);
                                        });
                            });
                });
    }
}
