package com.omgservers.service.operation.getIdByProject;

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
class GetIdByProjectOperationImpl implements GetIdByProjectOperation {

    final AliasModule aliasModule;

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
        final var request = new FindAliasRequest(tenantId, projectAlias);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
