package com.omgservers.service.handler.operation;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.module.tenant.TenantModule;
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

    final TenantModule tenantModule;

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
        return tenantModule.getTenantService().viewTenantStages(request)
                .map(ViewTenantStagesResponse::getTenantStages);
    }

    Uni<Boolean> deleteTenantStage(final Long tenantId, final Long id) {
        final var request = new DeleteTenantStageRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantStage(request)
                .map(DeleteTenantStageResponse::getDeleted);
    }
}
