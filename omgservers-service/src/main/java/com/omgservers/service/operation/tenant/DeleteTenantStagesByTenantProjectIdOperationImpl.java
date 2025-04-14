package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.schema.shard.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.shard.tenant.tenantStage.ViewTenantStagesResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteTenantStagesByTenantProjectIdOperationImpl implements DeleteTenantStagesByTenantProjectIdOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId, Long tenantProjectId) {
        return viewTenantStages(tenantId, tenantProjectId)
                .flatMap(tenantStages -> Multi.createFrom().iterable(tenantStages)
                        .onItem().transformToUniAndConcatenate(tenantStage ->
                                deleteTenantStage(tenantId, tenantStage.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant stage, " +
                                                            "tenantProject={}/{}, " +
                                                            "tenantStageId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantProjectId,
                                                    tenantStage.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantStageModel>> viewTenantStages(final Long tenantId, final Long tenantProjectId) {
        final var request = new ViewTenantStagesRequest(tenantId, tenantProjectId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantStagesResponse::getTenantStages);
    }

    Uni<Boolean> deleteTenantStage(final Long tenantId, final Long id) {
        final var request = new DeleteTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantStageResponse::getDeleted);
    }
}
