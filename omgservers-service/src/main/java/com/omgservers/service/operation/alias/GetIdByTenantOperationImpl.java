package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.shard.alias.FindAliasRequest;
import com.omgservers.schema.shard.alias.FindAliasResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.configuration.GlobalShardConfiguration;
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
        final var request = new FindAliasRequest(GlobalShardConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_TENANTS_GROUP,
                tenantAlias);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
