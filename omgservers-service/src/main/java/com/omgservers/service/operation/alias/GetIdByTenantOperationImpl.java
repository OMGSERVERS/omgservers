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
class GetIdByTenantOperationImpl implements GetIdByTenantOperation {

    final AliasShard aliasShard;

    @Override
    public Uni<Long> execute(final String tenant) {
        try {
            final var tenantId = Long.valueOf(tenant);
            return Uni.createFrom().item(tenantId);
        } catch (NumberFormatException e) {
            return findTenantAlias(tenant)
                    .map(AliasModel::getEntityId);
        }
    }

    Uni<AliasModel> findTenantAlias(final String tenantAlias) {
        final var request = new FindAliasRequest(AliasQualifierEnum.TENANT,
                tenantAlias,
                0L,
                tenantAlias);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
