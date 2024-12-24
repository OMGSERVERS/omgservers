package com.omgservers.service.operation.getIdByStage;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.service.module.alias.AliasModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIdByStageOperationImpl implements GetIdByStageOperation {

    final AliasModule aliasModule;

    @Override
    public Uni<Long> execute(final Long projectId, final String stage) {
        try {
            final var stageId = Long.valueOf(stage);
            return Uni.createFrom().item(stageId);
        } catch (NumberFormatException e) {
            return findProjectAlias(projectId, stage)
                    .map(AliasModel::getEntityId);
        }
    }

    Uni<AliasModel> findProjectAlias(final Long projectId,
                                     final String stageAlias) {
        final var request = new FindAliasRequest(projectId, stageAlias);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
