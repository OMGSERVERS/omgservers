package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.shard.alias.FindAliasRequest;
import com.omgservers.schema.shard.alias.FindAliasResponse;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIdByProjectOperationImpl implements GetIdByProjectOperation {

    final AliasShard aliasShard;

    @Override
    public Uni<Long> execute(final Long tenantId, final String project) {
        try {
            final var projectId = Long.valueOf(project);
            return Uni.createFrom().item(projectId);
        } catch (NumberFormatException e) {
            return findProjectAlias(tenantId, project)
                    .map(AliasModel::getEntityId);
        }
    }

    Uni<AliasModel> findProjectAlias(final Long tenantId,
                                     final String projectAlias) {
        final var request = new FindAliasRequest(AliasQualifierEnum.PROJECT,
                tenantId.toString(),
                tenantId,
                projectAlias);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
