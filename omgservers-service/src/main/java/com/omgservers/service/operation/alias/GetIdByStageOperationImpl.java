package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIdByStageOperationImpl implements GetIdByStageOperation {

    final AliasShard aliasShard;

    @Override
    public Uni<Long> execute(final Long tenantId,
                             final Long projectId,
                             final String stage) {
        try {
            final var stageId = Long.valueOf(stage);
            return Uni.createFrom().item(stageId);
        } catch (NumberFormatException e) {
            return findProjectAlias(tenantId, projectId, stage)
                    .map(AliasModel::getEntityId);
        }
    }

    Uni<AliasModel> findProjectAlias(final Long tenantId,
                                     final Long projectId,
                                     final String stageAlias) {
        final var request = new FindAliasRequest(tenantId, projectId, stageAlias);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
